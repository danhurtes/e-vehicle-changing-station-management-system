package com.netvalue.evehicleschargemeter.service;

import com.netvalue.evehicleschargemeter.dao.entity.ChargePoint;
import com.netvalue.evehicleschargemeter.dao.entity.ChargingSession;
import com.netvalue.evehicleschargemeter.dao.entity.Connector;
import com.netvalue.evehicleschargemeter.dao.entity.Customer;
import com.netvalue.evehicleschargemeter.dao.entity.RfidTag;
import com.netvalue.evehicleschargemeter.exception.EmptyMeterValueBalanceException;
import com.netvalue.evehicleschargemeter.utils.CustomerUtils;
import com.netvalue.evehicleschargemeter.web.request.NewConnectorRequest;
import com.netvalue.evehicleschargemeter.web.response.ChargingSessionIdResponse;
import com.netvalue.evehicleschargemeter.web.response.ChargingSessionResponse;
import com.netvalue.evehicleschargemeter.web.response.EChargeMeterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EChargeMeterService {
    private final VehicleService vehicleService;
    private final ChargingSessionService chargingSessionService;
    private final ConnectorService connectorService;
    private final CustomerService customerService;
    private final ChargePointService chargePointService;

    public RfidTag getRfidTag(String registrationPlate) {
        return vehicleService.findRfidTagByRegistrationPlate(registrationPlate);
    }

    public ChargePoint getChargingPoint(RfidTag rfidTag, Integer connectorNumber) {
        return connectorService.findChargingPointByConnectorNumberAndRfidTagName(rfidTag.getName(), connectorNumber);
    }

    public Customer getCustomerLeftMeterValue(String rfidNumber) {
        return customerService.findCustomerLeftMeterValueByRfidNumber(rfidNumber);
    }

    public long addNewChargingSession(ChargePoint chargePoint, Customer customer) {
        long availableSeconds = CustomerUtils.getAvailableSeconds(customer.getMeterValueBalance());

        if (availableSeconds > 0) {
            ChargingSession newChargingSession = chargingSessionService.createNewChargingSession(chargePoint, customer);
            chargingSessionService.processChargingSession(newChargingSession);
            return newChargingSession.getId();
        }
        throw new EmptyMeterValueBalanceException();
    }

    public ResponseEntity<EChargeMeterResponse> endChargingSession(Long changingSessionId) {
        ChargingSession chargingSession = chargingSessionService.getChargingSessionById(changingSessionId);
        if (chargingSession.getEndChargingTime() != null) {
            return ResponseEntity.badRequest()
                    .body(new ChargingSessionIdResponse("Charging session already completed", changingSessionId));
        }
        return chargingSessionService.processEndChargingSession(changingSessionId, chargingSession);
    }

    public Page<ChargingSessionResponse> findAllChargingSessions(Pageable pageable, String dateFrom, String dateTo) {
        return chargingSessionService.findAllChargingSessions(pageable, dateFrom, dateTo);
    }

    public ResponseEntity<EChargeMeterResponse> addNewConnector(NewConnectorRequest newConnectorRequest) {
        var chargePoint = chargePointService.findByUniqueSerialNumber(newConnectorRequest.getCpUniqueSerialNumber());
        connectorService.save(new Connector(newConnectorRequest.getConnectorNumber(), chargePoint));

        log.info("A new connector with number - {} was successfully added", newConnectorRequest.getConnectorNumber());
        return ResponseEntity.ok(new EChargeMeterResponse("A new connector was successfully added"));
    }
}
