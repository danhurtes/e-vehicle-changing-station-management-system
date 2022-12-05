package com.netvalue.evehicleschargemeter.web.controller;

import com.netvalue.evehicleschargemeter.annotation.EChargeMeterController;
import com.netvalue.evehicleschargemeter.service.EChargeMeterService;
import com.netvalue.evehicleschargemeter.web.request.NewConnectorRequest;
import com.netvalue.evehicleschargemeter.web.response.ChargingSessionResponse;
import com.netvalue.evehicleschargemeter.web.response.EChargeMeterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@EChargeMeterController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final EChargeMeterService chargeMeterService;

    @GetMapping("/chargingSessions")
    public Page<ChargingSessionResponse> getChargingSessions(@PageableDefault Pageable pageable,
                                                             @RequestParam(required = false)
                                                             String dateFrom,
                                                             @RequestParam(required = false)
                                                             String dateTo) {
        return chargeMeterService.findAllChargingSessions(pageable, dateFrom, dateTo);
    }

    @PostMapping("/newConnector")
    public ResponseEntity<EChargeMeterResponse> addNewConnector(@RequestBody NewConnectorRequest newConnectorRequest) {
        return chargeMeterService.addNewConnector(newConnectorRequest);
    }
}
