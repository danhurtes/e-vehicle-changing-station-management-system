insert into RFID_TAG (id, name, number) values (1, 'charge-point1', 'h8674c0b-we3b-882c-a277-34546432281f');
insert into RFID_TAG (id, name, number) values (2, 'charge-point2', 'e8674c0b-062b-4a2c-a277-2042461281f5');

insert into VEHICLE (id, name, registration_plate, rfid_tag_id) values (1, 'Tesla', 'wer 132', 1);
insert into VEHICLE (id, name, registration_plate, rfid_tag_id) values (2, 'Ford', 'pen 331', 2);

insert into CHARGE_POINT (id, name, unique_serial_number) values (1, 'charge-point1', RANDOM_UUID())
insert into CHARGE_POINT (id, name, unique_serial_number) values (2, 'charge-point2', 'j22364c0b-762b-112c-a277-5321246128ad')

insert into CONNECTOR (id, connector_number, charge_point_id) values (NEXT VALUE FOR CONNECTOR_ID_SEQ, 23, 1);
insert into CONNECTOR (id, connector_number, charge_point_id) values (NEXT VALUE FOR CONNECTOR_ID_SEQ, 33, 2);

insert into CUSTOMER (id, name, meter_value_balance, rfid_tag_id) values (1, 'Elon Musk', 2, 1);
insert into CUSTOMER (id, name, meter_value_balance, rfid_tag_id) values (2, 'Henry Ford', 3, 2);
