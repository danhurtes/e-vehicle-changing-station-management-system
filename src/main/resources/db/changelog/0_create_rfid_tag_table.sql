--liquibase formatted sql
--changeset dborodkin:create_vehicle_table splitStatements:true endDelimiter/;

create sequence rfid_tag_id_seq nocache nocycle
/

create table RFID_TAG
(
    ID number(19)
        constraint RFID_TAG primary key,
    NAME varchar2(255) not null,
    NUMBER UUID not null
)
/