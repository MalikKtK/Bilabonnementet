-- Udarbejdet af Malik Kütük

INSERT INTO Locations(name)
VALUES ("HQ");
INSERT INTO Locations(name)
VALUES ("DS Forhandler");
INSERT INTO bilabonnement.Users(name, password, role, locationId)
VALUES ("Malik", "123", "ADMINISTRATOR", 1);
INSERT INTO bilabonnement.users(name, password, role, locationId)
VALUES ("Mark", "123", "DATA_REGISTRATION", 1);
INSERT INTO bilabonnement.users(name, password, role, locationId)
VALUES ("ebus", "123", "BUSINESS_DEVELOPER", 1);
INSERT INTO bilabonnement.users(name, password, role, locationId)
VALUES ("marky", "123", "DAMAGE_AND_RECTIFICATION", 1);


-- for cars
INSERT INTO `bilabonnement`.`cars` (`id`, `carNumber`, `status`, `make`, `model`, `carPrice`, `registrationFee`,
                                    `co2Emission`, `kilometersDriven`, `damages`, `colour`, `fuelType`, `locationId`)
VALUES (1, '2635', 'READY_TO_BE_RENTED', 'Citron', 'c5', '10000', '10000', '126', '97000', '', 'gul',
        'dielsel', '1');
INSERT INTO `bilabonnement`.`cars` (`id`, `carNumber`, `status`, `make`, `model`, `carPrice`, `registrationFee`,
                                    `co2Emission`, `kilometersDriven`, `damages`, `colour`, `fuelType`, `locationId`)
VALUES (2, '2660', 'READY_FOR_DELIVERY', 'Audi', 'm5', '20000', '20000', '126', '98000', '', 'blå',
        'benzin', '2');

INSERT INTO `bilabonnement`.`cars` (`id`, `carNumber`, `status`, `make`, `model`, `carPrice`, `registrationFee`,
                                    `co2Emission`, `kilometersDriven`, `damages`, `colour`, `fuelType`, `locationId`)
VALUES (3, '2670', 'RENTED', 'BMW', 'f3', '30000', '30000', '126', '99000', '', 'orange',
        'elbil', '1');

INSERT INTO `bilabonnement`.`cars` (`id`, `carNumber`, `status`, `make`, `model`, `carPrice`, `registrationFee`,
                                    `co2Emission`, `kilometersDriven`, `damages`, `colour`, `fuelType`, `locationId`)
VALUES (4, '2675', 'BACK_FROM_BEING_RENTED', 'Opel', 'f9', '40000', '40000', '126', '100000', '', 'lilla',
        'dielsel', '2');
INSERT INTO `bilabonnement`.`cars` (`id`, `carNumber`, `status`, `make`, `model`, `carPrice`, `registrationFee`,
                                    `co2Emission`, `kilometersDriven`, `damages`, `colour`, `fuelType`, `locationId`)
VALUES (5, '2601', 'READY_FOR_SALE', 'Fiat', 'c10', '50000', '50000', '126', '100000', '', 'hvid',
        'benzin', '1');

INSERT INTO `bilabonnement`.`rental_agreements` (`carId`, `startDate`, `endDate`, `price`, `type`)
VALUES ('1', '2022-10-02', '2022-12-02', '3500', 'UNLIMITED');
INSERT INTO `bilabonnement`.`rental_agreements` (`carId`, `startDate`, `endDate`, `price`, `type`)
VALUES ('2', '2022-05-01', '2022-12-01', '4000', 'LIMITED');
INSERT INTO `bilabonnement`.`rental_agreements` (`carId`, `startDate`, `endDate`, `price`, `type`)
VALUES ('3', '2021-01-01', '2022-02-01', '5000', 'UNLIMITED');
INSERT INTO `bilabonnement`.`rental_agreements` (`carId`, `startDate`, `endDate`, `price`, `type`)
VALUES ('4', '2018-02-01', '2022-01-01', '4000', 'LIMITED');

INSERT INTO `bilabonnement`.`damage_report` (`id`, `notes`, `technicianId`, `carId`)
VALUES ('1', 'skrammer for 100000', '3', '1');
INSERT INTO `bilabonnement`.`damage_report` (`id`, `notes`, `technicianId`, `carId`)
VALUES ('2', 'smadret rude', '3', '2');
INSERT INTO `bilabonnement`.`damage_report` (`id`, `notes`, `technicianId`, `carId`)
VALUES ('3', 'smadret gearkasse', '3', '3');
INSERT INTO `bilabonnement`.`damage_report` (`id`, `notes`, `technicianId`, `carId`)
VALUES ('4', 'smadret bremer', '3', '4');

INSERT INTO `bilabonnement`.`damageline` (`lineNumber`, `damageReportId`, `damageNotes`, `price`)
VALUES ('1', '1', 'skrammer', '12');
INSERT INTO `bilabonnement`.`damageline` (`lineNumber`, `damageReportId`, `damageNotes`, `price`)
VALUES ('1', '2', 'bilsrkammer', '123');
INSERT INTO `bilabonnement`.`damageline` (`lineNumber`, `damageReportId`, `damageNotes`, `price`)
VALUES ('1', '3', 'dørskrammer', '1234');
INSERT INTO `bilabonnement`.`damageline` (`lineNumber`, `damageReportId`, `damageNotes`, `price`)
VALUES ('1', '4', 'vinduesskrammer', '12345');
