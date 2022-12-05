package com.netvalue.evehicleschargemeter.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connector {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connector_id_gen")
    @SequenceGenerator(name = "connector_id_gen", sequenceName = "connector_id_seq", allocationSize = 1)
    private Long id;
    private Integer connectorNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "charge_point_id")
    private ChargePoint chargePoint;

    public Connector(Integer connectorNumber, ChargePoint chargePoint) {
        this.connectorNumber = connectorNumber;
        this.chargePoint = chargePoint;
    }
}
