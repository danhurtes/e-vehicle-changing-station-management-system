package com.netvalue.evehicleschargemeter.web.request;

import lombok.Data;

@Data
public class NewConnectorRequest {
    private Integer connectorNumber;
    private String cpUniqueSerialNumber;
}
