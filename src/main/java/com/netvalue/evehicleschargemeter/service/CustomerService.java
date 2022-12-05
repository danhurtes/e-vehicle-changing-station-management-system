package com.netvalue.evehicleschargemeter.service;

import com.netvalue.evehicleschargemeter.dao.entity.Customer;
import com.netvalue.evehicleschargemeter.dao.repository.CustomerRepository;
import com.netvalue.evehicleschargemeter.exception.NoCustomerFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer findCustomerLeftMeterValueByRfidNumber(String rfidNumber) {
        return customerRepository.findByRfidTag_Number(rfidNumber).orElseThrow(() -> {
            throw new NoCustomerFoundException(rfidNumber);
        });
    }

    public void updateCustomerMeterValue(Long meterValue, String customerName) {
        customerRepository.updateMeterValue(meterValue, customerName);
    }
}
