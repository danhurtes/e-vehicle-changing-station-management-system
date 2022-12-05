package com.netvalue.evehicleschargemeter.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class VersionsService {
    @Value("${app.version}")
    private String appVersion;

    @Value("${database.version}")
    private String dbSchemaVersion;
}
