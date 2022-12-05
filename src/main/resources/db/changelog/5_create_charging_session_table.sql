--liquibase formatted sql
--changeset dborodkin:create_charging_session_table splitStatements:true endDelimiter/;

create sequence charging_session_id_seq nocache nocycle
/

create table CHARGING_SESSION
(
    ID number(19)
        constraint CHARGING_SESSION primary key,
    CUSTOMER_NAME varchar2(255),
    AVAILABLE_METER_VALUE number(19),
    METER_VALUE number(19) default 0,
    START_CHARGING_TIME TIMESTAMP default CURRENT_TIMESTAMP,
    END_CHARGING_TIME TIMESTAMP,
    ERROR_MESSAGE varchar2(255),
    CHARGE_POINT_ID number(19)
        constraint CHARGING_SESSION_CHARGE_POINT_ID_FK references CHARGE_POINT

)
/