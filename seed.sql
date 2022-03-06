INSERT INTO donnamis.addresses (building_number, street, postcode, commune, country)
VALUES ('23a', 'rue des paquerettes', '1420', 'Braine l''Alleud', 'Belgique');

INSERT INTO donnamis.addresses (building_number, street, postcode, commune, country)
VALUES ('78', 'chaussée de Waterloo', '1410', 'Waterloo', 'Belgique');

INSERT INTO donnamis.addresses (building_number, street, postcode, commune, country)
VALUES ('1000', 'avenue Louise', '1000', 'Bruxelles', 'Belgique');

INSERT INTO donnamis.addresses (building_number, street, postcode, commune, country)
VALUES ('3', 'avenue Rombault', '1420', 'Braine l''Alleud', 'Belgique');

INSERT INTO donnamis.members
(username, lastname, firstname, status, role, password, id_address)
VALUES ('MarcDeBruxelles', 'Jacky', 'Marc', 'pending', 'member','$2a$10$eMsxJ.LI75F.CIQXRXkrL.8k/Ss/ogrBCGo/8Ez8lJF99LdqmeKdm',1);

INSERT INTO donnamis.members
(username, lastname, firstname, status, role, password, id_address)
VALUES ('AlbertDeBucarest', 'Dubous', 'Albert', 'valid', 'member','$2a$10$tTjy7rCWdNsyQyIc8zoGrumHZvvGSOBwwf97J7PWMqoU5SVtR/0Fa',2);

INSERT INTO donnamis.members
(username, lastname, firstname, status, role, password, id_address)
VALUES ('GaspardDePorto', 'Pepe', 'Gaspard', 'valid', 'administrator','$2a$10$jTlPWXCbch1wjY34rQbjY.aoRxYr/Hq1MBeE2htTRQQ6q3lk5GpTm',3);

INSERT INTO donnamis.members
(username, lastname, firstname, status, role, password, id_address, refusal_reason)
VALUES ('JoshuaDeMadrid', 'Capri', 'Joshua', 'denied', 'member','$2a$10$JxDzVtxFgNkecl.AxXgZUu75dU2iedqVk7Yzj2SDZkAsRvlqey4I2',4, 'Je ne te connais pas.');