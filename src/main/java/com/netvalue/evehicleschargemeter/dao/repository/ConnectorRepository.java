package com.netvalue.evehicleschargemeter.dao.repository;

import com.netvalue.evehicleschargemeter.dao.entity.Connector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConnectorRepository extends JpaRepository<Connector, Long> {
    Optional<Connector> findByConnectorNumberAndChargePoint_Name(Integer connectorNumber, String name);
}
