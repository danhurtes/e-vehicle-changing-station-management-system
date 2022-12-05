package com.netvalue.evehicleschargemeter.service;

import com.netvalue.evehicleschargemeter.dao.entity.ChargePoint;
import com.netvalue.evehicleschargemeter.dao.entity.ChargingSession;
import com.netvalue.evehicleschargemeter.dao.entity.Customer;
import com.netvalue.evehicleschargemeter.dao.repository.ChargingSessionRepository;
import com.netvalue.evehicleschargemeter.dao.repository.specification.ChargingSessionSpecificationService;
import com.netvalue.evehicleschargemeter.exception.NoChargingSessionFoundException;
import com.netvalue.evehicleschargemeter.utils.CustomerUtils;
import com.netvalue.evehicleschargemeter.web.response.ChargingSessionIdResponse;
import com.netvalue.evehicleschargemeter.web.response.ChargingSessionResponse;
import com.netvalue.evehicleschargemeter.web.response.EChargeMeterErrorResponse;
import com.netvalue.evehicleschargemeter.web.response.EChargeMeterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChargingSessionService {
    private final static long EMPTY_BALANCE = 0L;
    private final ChargingSessionRepository chargingSessionRepository;
    private final CustomerService customerService;
    private final ChargingSessionSpecificationService specificationService;

    protected ChargingSession getChargingSessionById(Long id) {
        return chargingSessionRepository.findById(id).orElseThrow(() -> {
            throw new NoChargingSessionFoundException(id);
        });
    }

    protected CompletableFuture<ChargingSession> processChargingSession(ChargingSession newChargingSession) {
        var completableFuture = CompletableFuture.supplyAsync(() -> newChargingSession);
        return completableFuture.handle((cs, ex) -> {
            Optional<ChargingSession> csOpt = chargingSessionRepository.findById(cs.getId());
            if (csOpt.isPresent() && isChargingSessionEnded(csOpt.get())) {
                return CompletableFuture.completedFuture(cs);
            }
            return processChargingSession(cs);
        }).thenCompose(Function.identity());
    }

    private boolean isChargingSessionEnded(ChargingSession cs) {
        if (CustomerUtils.isAvailableBalanceSpent(cs.getAvailableMeterValue(), cs.getStartChargingTime())) {
            cs.setAvailableMeterValue(EMPTY_BALANCE);
            cs.setEndChargingTime(LocalDateTime.now());
            chargingSessionRepository.save(cs);
            customerService.updateCustomerMeterValue(EMPTY_BALANCE, cs.getCustomerName());
            log.info("Charging session for customer {} was completed because of empty balance", cs.getCustomerName());
            return true;
        }
        return cs.getEndChargingTime() != null;
    }

    protected ChargingSession createNewChargingSession(ChargePoint chargePoint, Customer customer) {
        log.info("New charging session was created");
        return chargingSessionRepository.save(ChargingSession.builder()
                .customerName(customer.getName())
                .availableMeterValue(customer.getMeterValueBalance())
                .meterValue(customer.getMeterValueBalance())
                .startChargingTime(LocalDateTime.now())
                .chargePoint(chargePoint)
                .build());
    }

    protected ResponseEntity<EChargeMeterResponse> processEndChargingSession(Long changingSessionId, ChargingSession chargingSession) {
        try {
            var endChargingSession = LocalDateTime.now();
            chargingSession.setEndChargingTime(endChargingSession);
            var meterValueSpent = CustomerUtils.computeMeterValue(chargingSession.getStartChargingTime(), endChargingSession);
            chargingSession.setMeterValue(meterValueSpent);
            var availableMeterValue = chargingSession.getAvailableMeterValue() - meterValueSpent;
            chargingSession.setAvailableMeterValue(availableMeterValue);

            chargingSessionRepository.save(chargingSession);
            customerService.updateCustomerMeterValue(availableMeterValue, chargingSession.getCustomerName());
            log.info("Charging session for customer {} successfully completed", chargingSession.getCustomerName());
            return ResponseEntity.ok(new ChargingSessionIdResponse("Charging session successfully completed", changingSessionId));
        } catch (Exception e) {
            log.error("Charging session with id - {} ended unsuccessfully", changingSessionId);
            chargingSession.setErrorMessage(e.getMessage());
            chargingSessionRepository.save(chargingSession);
            return ResponseEntity.badRequest()
                    .body(new EChargeMeterErrorResponse("Charging session ended unsuccessfully", e.getMessage()));
        }
    }

    public Page<ChargingSessionResponse> findAllChargingSessions(Pageable pageable, String dateFrom, String dateTo) {
        return chargingSessionRepository.findAll(specificationService.forChargingSessions(dateFrom, dateTo), pageable)
                .map(this::mapToChargingSessionResponse);
    }

    private ChargingSessionResponse mapToChargingSessionResponse(ChargingSession chargingSession) {
        return ChargingSessionResponse.builder()
                .chargingSessionId(chargingSession.getId())
                .customerName(chargingSession.getCustomerName())
                .startAvailableMeterValue(chargingSession.getAvailableMeterValue())
                .meterValueSpent(chargingSession.getMeterValue())
                .startChargingTime(chargingSession.getStartChargingTime())
                .endChargingTime(chargingSession.getEndChargingTime())
                .errorMessage(chargingSession.getErrorMessage())
                .build();
    }
}
