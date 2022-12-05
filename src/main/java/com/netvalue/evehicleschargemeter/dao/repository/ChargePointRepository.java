package com.netvalue.evehicleschargemeter.dao.repository;

import com.netvalue.evehicleschargemeter.dao.entity.ChargePoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChargePointRepository extends JpaRepository<ChargePoint, Long> {
    Optional<ChargePoint> findByUniqueSerialNumber(String uniqueSerialNumber);
}
