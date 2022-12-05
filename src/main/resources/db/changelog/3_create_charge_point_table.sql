--liquibase formatted sql
--changeset dborodkin:create_charge_point_table splitStatements:true endDelimiter/;

create sequence charge_point_id_seq nocache nocycle
/

create table CHARGE_POINT
(
    ID number(19)
        constraint CHARGE_POINT primary key,
    NAME varchar2(255) not null,
    UNIQUE_SERIAL_NUMBER UUID not null
)
/