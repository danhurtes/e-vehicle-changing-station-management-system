package com.netvalue.evehicleschargemeter.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Entity
@Getter
@Setter
public class ChargePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "charge_point_id_gen")
    @SequenceGenerator(name = "charge_point_id_gen", sequenceName = "charge_point_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String uniqueSerialNumber;
    @OneToMany(mappedBy = "chargePoint", cascade = CascadeType.ALL)
    private List<Connector> connectors;
    @OneToMany(mappedBy = "chargePoint")
    private List<ChargingSession> chargingSessions;
}
