package com.netvalue.evehicleschargemeter.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "rfid-tag.validated")
@Component
@Data
public class RfidTagValidatedList {
    Map<String, List<String>> chargePoints;
}
