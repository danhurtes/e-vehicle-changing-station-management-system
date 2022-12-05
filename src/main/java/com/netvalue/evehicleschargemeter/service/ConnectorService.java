package com.netvalue.evehicleschargemeter.service;

import com.netvalue.evehicleschargemeter.dao.entity.ChargePoint;
import com.netvalue.evehicleschargemeter.dao.entity.Connector;
import com.netvalue.evehicleschargemeter.dao.repository.ConnectorRepository;
import com.netvalue.evehicleschargemeter.exception.NoConnectorFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectorService {
    private final ConnectorRepository connectorRepository;

    public ChargePoint findChargingPointByConnectorNumberAndRfidTagName(String rfidName, Integer connectorNumber) {
        return connectorRepository.findByConnectorNumberAndChargePoint_Name(connectorNumber, rfidName).orElseThrow(() -> {
            throw new NoConnectorFoundException(connectorNumber);
        }).getChargePoint();
    }

    public void save(Connector connector) {
        connectorRepository.save(connector);
    }
}
