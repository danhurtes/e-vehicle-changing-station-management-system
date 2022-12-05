package com.netvalue.evehicleschargemeter.dao.repository;

import com.netvalue.evehicleschargemeter.dao.entity.Customer;
import com.netvalue.evehicleschargemeter.dao.entity.RfidTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByRfidTag_Number(String number);

    @Transactional
    @Modifying
    @Query(value = "update Customer c set c.meterValueBalance = :meterValue where c.name = :customerName")
    void updateMeterValue(@Param("meterValue") Long meterValue, @Param("customerName") String customerName);
}
