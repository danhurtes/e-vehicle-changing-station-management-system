package com.netvalue.evehicleschargemeter.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Getter
@Setter
public class RfidTag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rfid_tag_id_gen")
    @SequenceGenerator(name = "rfid_tag_id_gen", sequenceName = "rfid_tag_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String number;
}
