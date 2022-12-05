package com.netvalue.evehicleschargemeter.dao.repository;

import com.netvalue.evehicleschargemeter.dao.entity.ChargingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long>, JpaSpecificationExecutor<ChargingSession> {
}
