INSERT INTO User (id,  first_name, last_name, mobile_number, national_code, password, username, CREATION_DATE) VALUES
  (1, 'Elham', 'Ghafarzadeh', '09121234567', '0312345678', '{scrypt}$e0801$gDHgl1QxdziQMyLqGQwsgp93aKRavAMxsOYKxKTXy1YqBey2y71P445xwV6Wi2CyAJjaSmfXz3yn987ITDxQCA==$nwZ2/k2TLowY7hcybKv22TVqG2d6+EMFTz0u+cp8TTo=', 'e.ghafarzadeh', '2019-01-01 00:00:01');
INSERT INTO card (id, pan, user_id) values (1, '1234123412341234', 1);
INSERT INTO card (id, pan, user_id) values (2, '6037123412341234', 1);
INSERT INTO TRANSACTION (id, destination, TRANSACTION_DATE, REFERENCE_NUMBER, source, amount, STATUS, PAYMENT_CLIENT_NAME, USER_ID) values (1, '6037123412341230', '2019-01-01 00:00:01', '1234', '6037123412341234', 1000, 'FAILED', 'PAYMENT1', 1);
INSERT INTO TRANSACTION (id, destination, TRANSACTION_DATE, REFERENCE_NUMBER, source, amount, STATUS, PAYMENT_CLIENT_NAME, USER_ID) values (2, '6037123412341231', '2019-01-01 00:00:01', '1235', '1234123412341234', 2000, 'SUCCESSFUL', 'PAYMENT2', 1);
alter sequence transaction_seq RESTART WITH 100
alter sequence card_seq RESTART WITH 100
alter sequence user_seq RESTART WITH 100
