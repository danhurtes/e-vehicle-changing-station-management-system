--liquibase formatted sql
--changeset dborodkin:create_connector_table splitStatements:true endDelimiter/;

create sequence connector_id_seq nocache nocycle
/

create table CONNECTOR
(
    ID number(19) default VALUES NEXT VALUE FOR connector_id_seq
        constraint CONNECTOR primary key,
    CONNECTOR_NUMBER number(10) not null,
    CHARGE_POINT_ID number(19)
        constraint CONNECTOR_CHARGE_POINT_ID_FK references CHARGE_POINT
)
/