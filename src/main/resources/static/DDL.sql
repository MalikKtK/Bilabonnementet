CREATE SCHEMA bilabonnement ;
-- Udarbejdet af Malik Kütük
USE bilabonnement ;

CREATE TABLE bilabonnement.Locations (
                                            id INT NOT NULL AUTO_INCREMENT,
                                            name VARCHAR(255) NOT NULL,
                                            PRIMARY KEY (id));


CREATE TABLE bilabonnement.Users (
                                        id INT NOT NULL AUTO_INCREMENT,
                                        name VARCHAR(255) NOT NULL,
                                        password VARCHAR(64) NOT NULL,
                                        role VARCHAR(25) NOT NULL,
                                        locationId INT NOT NULL,
                                        PRIMARY KEY (id),
                                            FOREIGN KEY (locationId)
                                                REFERENCES bilabonnement.Locations (id));


CREATE TABLE bilabonnement.cars (
                                        id INT NOT  NULL AUTO_INCREMENT,
                                        carNumber VARCHAR(17) NULL,
                                        status VARCHAR(255) NULL,
                                        make VARCHAR(255) NULL,
                                        model VARCHAR(255) NULL,
                                        carPrice INT NULL,
                                        registrationFee INT NULL,
                                        co2Emission VARCHAR(255) NULL,
                                        kilometersDriven INT NULL,
                                        damages VARCHAR(2400) NULL,
                                        colour VARCHAR(255) NULL,
                                        fuelType VARCHAR(255) NULL,
                                        locationId INT NULL,
                                        PRIMARY KEY (id),
                                            FOREIGN KEY (locationId)
                                                REFERENCES bilabonnement.locations (id));


CREATE TABLE bilabonnement.rental_agreements (
                                                     id INT NOT NULL AUTO_INCREMENT,
                                                     carId INT NOT NULL,
                                                     startDate DATE NOT NULL,
                                                     endDate DATE NOT NULL,
                                                     price INT NULL,
                                                     type VARCHAR(45) NULL,
                                                     PRIMARY KEY (id),
                                                         FOREIGN KEY (carId)
                                                             REFERENCES bilabonnement.cars (id));

CREATE TABLE bilabonnement.damage_report (
                                 id INT NOT NULL AUTO_INCREMENT,
                                 notes text,
                                 technicianId INT NOT NULL,
                                 carId INT NOT NULL,
                                 PRIMARY KEY (id),
                                 FOREIGN KEY (carId)
                                     REFERENCES bilabonnement.cars (id),
                                 FOREIGN KEY (technicianId)
                                     REFERENCES bilabonnement.users (id));

CREATE TABLE bilabonnement.damageline (
                                              damageReportId INT NOT NULL,
                                              lineNumber INT NOT NULL,
                                              damageNotes VARCHAR(255) NULL,
                                              price INT NULL,
                                              PRIMARY KEY (lineNumber, damageReportId),
                                                  FOREIGN KEY (damageReportId)
                                                      REFERENCES bilabonnement.damage_report (id));
