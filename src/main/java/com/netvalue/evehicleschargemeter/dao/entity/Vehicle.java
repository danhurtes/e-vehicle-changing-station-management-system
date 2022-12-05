package com.netvalue.evehicleschargemeter.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_id_gen")
    @SequenceGenerator(name = "vehicle_id_gen", sequenceName = "vehicle_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String registrationPlate;
    @OneToOne
    @JoinColumn(name = "rfid_tag_id", referencedColumnName = "id")
    private RfidTag rfidTag;
}
