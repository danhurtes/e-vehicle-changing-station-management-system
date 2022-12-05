package com.netvalue.evehicleschargemeter.web.controller;

import com.netvalue.evehicleschargemeter.annotation.EChargeMeterController;
import com.netvalue.evehicleschargemeter.web.response.VersionsResponse;
import com.netvalue.evehicleschargemeter.service.VersionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@EChargeMeterController
@RequiredArgsConstructor
public class VersionsController {
    private final VersionsService versionsService;

    @GetMapping("/appVersion")
    public ResponseEntity<VersionsResponse> getAppVersion() {
        return ResponseEntity.ok(new VersionsResponse(versionsService.getAppVersion()));
    }

    @GetMapping("/dbSchemaVersion")
    public ResponseEntity<VersionsResponse> getdbSchemaVersion() {
        return ResponseEntity.ok(new VersionsResponse(versionsService.getDbSchemaVersion()));
    }
}
