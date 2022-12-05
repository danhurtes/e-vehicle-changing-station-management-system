--liquibase formatted sql
--changeset dborodkin:create_vehicle_table splitStatements:true endDelimiter/;

create sequence vehicle_id_seq nocache nocycle
/

create table VEHICLE
(
    ID number(19)
        constraint VEHICLE primary key,
    NAME varchar2(255) not null,
    REGISTRATION_PLATE varchar2(255) not null,
    RFID_TAG_ID number(19)
        constraint VEHICLE_RFID_TAG_ID_FK references RFID_TAG
)
/