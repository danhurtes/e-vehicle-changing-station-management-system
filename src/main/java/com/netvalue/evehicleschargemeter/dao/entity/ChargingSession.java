package com.netvalue.evehicleschargemeter.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "charging_session_gen")
    @SequenceGenerator(name = "charging_session_gen", sequenceName = "charging_session_id_seq", allocationSize = 1)
    private Long id;
    private String customerName;
    private Long availableMeterValue;
    private Long meterValue;
    private LocalDateTime startChargingTime;
    private LocalDateTime endChargingTime;
    private String errorMessage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_point_id", nullable = false)
    private ChargePoint chargePoint;
}
