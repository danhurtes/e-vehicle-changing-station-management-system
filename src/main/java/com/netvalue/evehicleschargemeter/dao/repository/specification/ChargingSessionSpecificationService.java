package com.netvalue.evehicleschargemeter.dao.repository.specification;

import com.netvalue.evehicleschargemeter.dao.entity.ChargingSession;
import com.netvalue.evehicleschargemeter.utils.CustomerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ChargingSessionSpecificationService {
    public Specification<ChargingSession> forChargingSessions(String dateFrom, String dateTo) {
        return ((root, query, criteriaBuilder) -> {
            Specification<ChargingSession> csSpecification = Specification.where(null);
            if (StringUtils.isNotBlank(dateFrom)) {
                csSpecification = csSpecification
                        .and(dateGreaterOrEquals(CustomerUtils.getDateTimeFromString(dateFrom).toLocalDate().atStartOfDay()));
            }
            if (StringUtils.isNotBlank(dateTo)) {
                csSpecification = csSpecification
                        .and(dateLessOrEquals(CustomerUtils.getDateTimeFromString(dateTo).toLocalDate().atTime(LocalTime.MAX)));
            }
            return csSpecification.toPredicate(root, query, criteriaBuilder);
        });
    }

    private Specification<ChargingSession> dateGreaterOrEquals(LocalDateTime dateFrom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startChargingTime"), dateFrom);
    }

    private Specification<ChargingSession> dateLessOrEquals(LocalDateTime dateTo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("endChargingTime"), dateTo);
    }
}
