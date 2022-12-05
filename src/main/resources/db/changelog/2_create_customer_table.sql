--liquibase formatted sql
--changeset dborodkin:create_customer_table splitStatements:true endDelimiter/;

create sequence customer_id_seq nocache nocycle
/

create table CUSTOMER
(
    ID number(19)
        constraint CUSTOMER primary key,
    NAME varchar2(255) not null,
    METER_VALUE_BALANCE number(19),
    RFID_TAG_ID number(19)
        constraint CUSTOMER_RFID_TAG_ID_FK references RFID_TAG
)
/