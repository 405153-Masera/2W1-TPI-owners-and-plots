-- Tabla DniTypes
CREATE TABLE dni_types (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           description VARCHAR(255),
                           created_datetime DATETIME,
                           created_user INT,
                           last_updated_datetime DATETIME,
                           last_updated_user INT
);

-- Tabla Tax_Status
CREATE TABLE tax_status (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            description VARCHAR(255),
                            created_datetime DATETIME,
                            created_user INT,
                            last_updated_datetime DATETIME,
                            last_updated_user INT
);

-- Tabla OwnersTypes
CREATE TABLE owners_types (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              description VARCHAR(255),
                              created_datetime DATETIME,
                              created_user INT,
                              last_updated_datetime DATETIME,
                              last_updated_user INT
);

-- Tabla Owners
CREATE TABLE owners (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255),
                        lastname VARCHAR(255),
                        dni_type_id INT,
                        dni VARCHAR(255),
                        date_birth DATETIME,
                        tax_status_id INT,
                        owner_type_id INT,
                        business_name VARCHAR(255),
                        active BOOLEAN,
                        created_datetime DATETIME,
                        created_user INT,
                        last_updated_datetime DATETIME,
                        last_updated_user INT,
                        FOREIGN KEY (tax_status_id) REFERENCES tax_status(id),
                        FOREIGN KEY (owner_type_id) REFERENCES owners_types(id),
                        FOREIGN KEY (dni_type_id) REFERENCES dni_types(id)
);

-- Tabla PlotStates
CREATE TABLE plot_states (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(255),
                             created_datetime DATETIME,
                             created_user INT,
                             last_updated_datetime DATETIME,
                             last_updated_user INT
);

-- Tabla PlotTypes
CREATE TABLE plot_types (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255),
                            created_datetime DATETIME,
                            created_user INT,
                            last_updated_datetime DATETIME,
                            last_updated_user INT
);

-- Tabla Plots
CREATE TABLE plots (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       plot_number INT,
                       block_number INT,
                       plot_state_id INT,
                       plot_type_id INT,
                       total_area_in_m2 DECIMAL(10, 2),
                       built_area_in_m2 DECIMAL(10, 2),
                       created_datetime DATETIME,
                       created_user INT,
                       last_updated_datetime DATETIME,
                       last_updated_user INT,
                       FOREIGN KEY (plot_state_id) REFERENCES plot_states(id),
                       FOREIGN KEY (plot_type_id) REFERENCES plot_types(id)
);

-- Tabla PlotOwners
CREATE TABLE plot_owners (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             plot_id INT,
                             owner_id INT,
                             created_datetime DATETIME,
                             created_user INT,
                             last_updated_datetime DATETIME,
                             last_updated_user INT,
                             FOREIGN KEY (plot_id) REFERENCES plots(id),
                             FOREIGN KEY (owner_id) REFERENCES owners(id)
);


-- Tabla Files
CREATE TABLE files (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       file_uuid VARCHAR(255),
                       name VARCHAR(255),
                       created_datetime DATETIME,
                       created_user INT,
                       last_updated_datetime DATETIME,
                       last_updated_user INT
);

-- Tabla Files_Plots
CREATE TABLE files_plots (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             file_id INT,
                             plot_id INT,
                             created_datetime DATETIME,
                             created_user INT,
                             last_updated_datetime DATETIME,
                             last_updated_user INT,
                             FOREIGN KEY (file_id) REFERENCES files(id),
                             FOREIGN KEY (plot_id) REFERENCES plots(id)
);

-- Tabla Files_Owners
CREATE TABLE files_owners (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              file_id INT,
                              owner_id INT,
                              created_datetime DATETIME,
                              created_user INT,
                              last_updated_datetime DATETIME,
                              last_updated_user INT,
                              FOREIGN KEY (file_id) REFERENCES files(id),
                              FOREIGN KEY (owner_id) REFERENCES owners(id)
);


-- Tabla de auditoría para Owners
CREATE TABLE owners_audit (
                              version_id INT PRIMARY KEY AUTO_INCREMENT,
                              id INT,
                              version INT,
                              name VARCHAR(255),
                              lastname VARCHAR(255),
                              dni VARCHAR(255),
                              dni_type_id INT,
                              date_birth DATE,
                              tax_status_id INT,
                              owner_type_id INT,
                              business_name VARCHAR(255),
                              active BOOLEAN,
                              created_datetime DATETIME,
                              created_user INT,
                              last_updated_datetime DATETIME,
                              last_updated_user INT
);

-- Tabla de auditoría para PlotTypes
CREATE TABLE plot_types_audit (
                                  version_id INT PRIMARY KEY AUTO_INCREMENT,
                                  id INT,
                                  version INT,
                                  name VARCHAR(100),
                                  created_datetime DATETIME,
                                  created_user INT,
                                  last_updated_datetime DATETIME,
                                  last_updated_user INT
);

-- Tabla de auditoría para PlotStates
CREATE TABLE plot_states_audit (
                                   version_id INT PRIMARY KEY AUTO_INCREMENT,
                                   id INT,
                                   version INT,
                                   name VARCHAR(100),
                                   created_datetime DATETIME,
                                   created_user INT,
                                   last_updated_datetime DATETIME,
                                   last_updated_user INT
);

-- Tabla de auditoría para Plots
CREATE TABLE plots_audit (
                             version_id INT PRIMARY KEY AUTO_INCREMENT,
                             id INT,
                             version INT,
                             plot_number INT,
                             block_number INT,
                             plot_state_id INT,
                             plot_type_id INT,
                             total_area_in_m2 DECIMAL(10, 2),
                             built_area_in_m2 DECIMAL(10, 2),
                             created_datetime DATETIME,
                             created_user INT,
                             last_updated_datetime DATETIME,
                             last_updated_user INT
);

-- Tabla de auditoría para PlotOwners
CREATE TABLE plot_owners_audit (
                                   version_id INT PRIMARY KEY AUTO_INCREMENT,
                                   id INT,
                                   version INT,
                                   plot_id INT,
                                   owner_id INT,
                                   created_datetime DATETIME,
                                   created_user INT,
                                   last_updated_datetime DATETIME,
                                   last_updated_user INT
);

-- Tabla de auditoría para Files
CREATE TABLE files_audit (
                             version_id INT PRIMARY KEY AUTO_INCREMENT,
                             id INT,
                             version INT,
                             file_uuid VARCHAR(255),
                             name VARCHAR(255),
                             created_datetime DATETIME,
                             created_user INT,
                             last_updated_datetime DATETIME,
                             last_updated_user INT
);

-- Tabla de auditoría para Files_Plots
CREATE TABLE files_plots_audit (
                                   version_id INT PRIMARY KEY AUTO_INCREMENT,
                                   id INT,
                                   version INT,
                                   file_id INT,
                                   plot_id INT,
                                   created_datetime DATETIME,
                                   created_user INT,
                                   last_updated_datetime DATETIME,
                                   last_updated_user INT
);

-- Tabla de auditoría para Files_Owners
CREATE TABLE files_owners_audit (
                                    version_id INT PRIMARY KEY AUTO_INCREMENT,
                                    id INT,
                                    version INT,
                                    file_id INT,
                                    owner_id INT,
                                    created_datetime DATETIME,
                                    created_user INT,
                                    last_updated_datetime DATETIME,
                                    last_updated_user INT
);

CREATE TABLE dni_types_audit (
                                 version_id INT PRIMARY KEY AUTO_INCREMENT,
                                 id INT,
                                 version INT,
                                 description VARCHAR(255),
                                 created_datetime DATETIME,
                                 created_user INT,
                                 last_updated_datetime DATETIME,
                                 last_updated_user INT
);


-- Tabla de auditoría para TaxStatus
CREATE TABLE tax_status_audit (
                                  version_id INT PRIMARY KEY AUTO_INCREMENT,
                                  id INT,
                                  version INT,
                                  description VARCHAR(255),
                                  created_datetime DATETIME,
                                  created_user INT,
                                  last_updated_datetime DATETIME,
                                  last_updated_user INT
);

-- Tabla de auditoría para TaxStatus
CREATE TABLE owners_types_audit (
                                    version_id INT PRIMARY KEY AUTO_INCREMENT,
                                    id INT,
                                    version INT,
                                    description VARCHAR(255),
                                    created_datetime DATETIME,
                                    created_user INT,
                                    last_updated_datetime DATETIME,
                                    last_updated_user INT
);

INSERT INTO tax_status (description, created_datetime, last_updated_datetime, created_user, last_updated_user)
VALUES
    ('IVA Responsable inscripto', NOW(), NOW(), 1, 1),
    ('IVA Responsable no inscripto', NOW(), NOW(), 1, 1),
    ('IVA no Responsable', NOW(), NOW(), 1, 1),
    ('IVA Sujeto Exento', NOW(), NOW(), 1, 1),
    ('Monotributista', NOW(), NOW(), 1, 1);

INSERT INTO owners_types (description, created_datetime, last_updated_datetime, created_user, last_updated_user)
VALUES
    ('Persona Fisica' , NOW(), NOW(), 1, 1),
    ('Persona Juridica', NOW(), NOW(), 1, 1),
    ('Otro', NOW(), NOW(), 1, 1);

INSERT INTO plot_states (name, created_datetime, last_updated_datetime, created_user, last_updated_user)
VALUES
    ('Disponible', NOW(), NOW(), 1, 1),
    ('Habitado', NOW(), NOW(), 1, 1),
    ('En construccion', NOW(), NOW(), 1, 1);

INSERT INTO plot_types (name, created_datetime, last_updated_datetime, created_user, last_updated_user)
VALUES
    ('Comercial', NOW(), NOW(), 1, 1),
    ('Residencial', NOW(), NOW(), 1, 1),
    ('Baldio', NOW(), NOW(), 1, 1);

-- Insertar tipos de DNI
INSERT INTO dni_types (description, created_datetime, last_updated_datetime, created_user, last_updated_user)
VALUES
    ('DNI', NOW(), NOW(), 1, 1),
    ('Pasaporte', NOW(), NOW(), 1, 1),
    ('CUIT/CUIL', NOW(), NOW(), 1, 1);

INSERT INTO owners (name, lastname, dni_type_id, dni, date_birth, tax_status_id, owner_type_id, business_name, active, created_datetime, created_user, last_updated_datetime, last_updated_user)
VALUES
    ('Carlos', 'Perez', 1, '41234567', '1985-04-12 00:00:00', 1, 1, NULL, TRUE, NOW(), 1, NOW(), 1),
    ('Maria', 'Gonzalez', 1, '39876543', '1990-08-25 00:00:00', 2, 1, NULL, TRUE, NOW(), 1, NOW(), 1),
    ('Juan', 'Lopez', 1, '41238945', '1978-03-15 00:00:00', 5, 1, NULL, TRUE, NOW(), 1, NOW(), 1),
    ('Ana', 'Martinez', 1, '44556677', '1995-07-19 00:00:00', 2, 1, NULL, TRUE, NOW(), 1, NOW(), 1),
    ('Pedro', 'Ramirez', 1, '40785621', '1982-01-05 00:00:00', 3, 1, NULL, TRUE, NOW(), 1, NOW(), 1),
    ('Sofia', 'Hernandez', 1, '43890123', '1993-11-11 00:00:00', 4, 1, NULL, TRUE, NOW(), 1, NOW(), 1),
    ('Luis', 'Garcia', 1, '42987654', '1988-09-30 00:00:00', 2, 1, NULL, TRUE, NOW(), 1, NOW(), 1),
    ('Laura', 'Rojas', 1, '40654321', '2000-02-20 00:00:00', 3, 1, NULL, TRUE, NOW(), 1, NOW(), 1),
    ('Jose', 'Torres', 1, '43210987', '1975-05-22 00:00:00', 4, 1, NULL, TRUE, NOW(), 1, NOW(), 1),
    ('Carmen', 'Diaz', 3, '23455948169', '1987-12-10 00:00:00', 2, 1, NULL , TRUE, NOW(), 1, NOW(), 1),
    ('Gabriel','De Maussion', 1, '43432654','2000-02-02 00:00:00' , 1, 1 ,NULL,TRUE,NOW(),1,NOW(),1 );

INSERT INTO plots (plot_number, block_number, plot_state_id, plot_type_id, total_area_in_m2, built_area_in_m2, created_datetime, created_user, last_updated_datetime, last_updated_user)
VALUES
    (1, 1, 2, 1, 1500.00, 1000.00, NOW(), 1, NOW(), 1),
    (2, 1, 2, 2, 2500.00, 1500.00, NOW(), 1, NOW(), 1),
    (3, 1, 2, 3, 1800.00, 0.00, NOW(), 1, NOW(), 1),
    (4, 1, 3, 3, 500.00, 0.00, NOW(), 1, NOW(), 1),
    (5, 2, 2, 2, 1200.00, 800.00, NOW(), 1, NOW(), 1),
    (6, 2, 1, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (7, 2, 2, 1, 2100.00, 1500.00, NOW(), 1, NOW(), 1),
    (8, 3, 1, 2, 900.00, 500.00, NOW(), 1, NOW(), 1),
    (9, 3, 3, 3, 2600.00, 0.00, NOW(), 1, NOW(), 1),
    (10, 3, 2, 1, 1300.00, 1100.00, NOW(), 1, NOW(), 1),
    (11, 3, 2, 2, 2900.00, 1800.00, NOW(), 1, NOW(), 1),
    (12, 4, 1, 3, 1100.00, 0.00, NOW(), 1, NOW(), 1),
    (13, 4, 3, 3, 1500.00, 0.00, NOW(), 1, NOW(), 1),
    (14, 4, 2, 2, 1700.00, 1200.00, NOW(), 1, NOW(), 1),
    (15, 4, 1, 1, 2400.00, 1300.00, NOW(), 1, NOW(), 1),
    (16, 5, 2, 3, 2000.00, 0.00, NOW(), 1, NOW(), 1),
    (17, 5, 2, 1, 1900.00, 1500.00, NOW(), 1, NOW(), 1),
    (18, 5, 3, 3, 2800.00, 0.00, NOW(), 1, NOW(), 1),
    (19, 5, 2, 1, 2300.00, 1700.00, NOW(), 1, NOW(), 1),
    (20, 5, 2, 2, 600.00, 400.00, NOW(), 1, NOW(), 1),
    (21, 5, 2, 2, 800.00, 500.00, NOW(), 1, NOW(), 1),
    (22, 1, 1, 1, 1500.00, 1000.00, NOW(), 1, NOW(), 1),
    (23, 1, 2, 2, 2500.00, 1500.00, NOW(), 1, NOW(), 1),
    (24, 1, 3, 3, 1800.00, 0.00, NOW(), 1, NOW(), 1),
    (25, 1, 1, 3, 500.00, 0.00, NOW(), 1, NOW(), 1),
    (26, 2, 2, 2, 1200.00, 800.00, NOW(), 1, NOW(), 1),
    (27, 2, 1, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (28, 2, 2, 1, 2100.00, 1500.00, NOW(), 1, NOW(), 1),
    (29, 3, 1, 2, 900.00, 500.00, NOW(), 1, NOW(), 1),
    (30, 3, 3, 3, 2600.00, 0.00, NOW(), 1, NOW(), 1),
    (31, 3, 2, 1, 1300.00, 1100.00, NOW(), 1, NOW(), 1),
    (32, 3, 2, 2, 2900.00, 1800.00, NOW(), 1, NOW(), 1),
    (33, 4, 1, 3, 1100.00, 0.00, NOW(), 1, NOW(), 1),
    (34, 4, 3, 3, 1500.00, 0.00, NOW(), 1, NOW(), 1),
    (35, 4, 2, 2, 1700.00, 1200.00, NOW(), 1, NOW(), 1),
    (36, 4, 1, 1, 2400.00, 1300.00, NOW(), 1, NOW(), 1),
    (37, 5, 2, 3, 2000.00, 0.00, NOW(), 1, NOW(), 1),
    (38, 5, 2, 1, 1900.00, 1500.00, NOW(), 1, NOW(), 1),
    (39, 5, 3, 3, 2800.00, 0.00, NOW(), 1, NOW(), 1),
    (40, 5, 2, 1, 2300.00, 1700.00, NOW(), 1, NOW(), 1),
    (41, 5, 2, 2, 600.00, 400.00, NOW(), 1, NOW(), 1),
    (42, 6, 2, 3, 800.00, 300.00, NOW(), 1, NOW(), 1),
    (43, 6, 1, 1, 1500.00, 700.00, NOW(), 1, NOW(), 1),
    (44, 6, 2, 2, 2400.00, 1200.00, NOW(), 1, NOW(), 1),
    (45, 6, 3, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (46, 7, 1, 2, 1100.00, 800.00, NOW(), 1, NOW(), 1),
    (47, 7, 2, 3, 500.00, 0.00, NOW(), 1, NOW(), 1),
    (48, 7, 2, 1, 2300.00, 1700.00, NOW(), 1, NOW(), 1),
    (49, 7, 3, 2, 800.00, 600.00, NOW(), 1, NOW(), 1),
    (50, 7, 1, 3, 2700.00, 0.00, NOW(), 1, NOW(), 1),
    (51, 8, 2, 1, 1900.00, 1500.00, NOW(), 1, NOW(), 1),
    (52, 8, 2, 2, 600.00, 400.00, NOW(), 1, NOW(), 1),
    (53, 8, 2, 2, 800.00, 500.00, NOW(), 1, NOW(), 1),
    (54, 8, 1, 1, 1500.00, 1000.00, NOW(), 1, NOW(), 1),
    (55, 8, 1, 2, 2500.00, 1500.00, NOW(), 1, NOW(), 1),
    (56, 8, 3, 3, 1800.00, 0.00, NOW(), 1, NOW(), 1),
    (57, 8, 1, 3, 500.00, 0.00, NOW(), 1, NOW(), 1),
    (58, 9, 2, 2, 1200.00, 800.00, NOW(), 1, NOW(), 1),
    (59, 9, 1, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (60, 9, 2, 1, 2100.00, 1500.00, NOW(), 1, NOW(), 1),
    (61, 10, 1, 2, 900.00, 500.00, NOW(), 1, NOW(), 1),
    (62, 10, 3, 3, 2600.00, 0.00, NOW(), 1, NOW(), 1),
    (63, 10, 2, 1, 1300.00, 1100.00, NOW(), 1, NOW(), 1),
    (64, 10, 2, 2, 2900.00, 1800.00, NOW(), 1, NOW(), 1),
    (65, 11, 1, 3, 1100.00, 0.00, NOW(), 1, NOW(), 1),
    (66, 11, 3, 3, 1500.00, 0.00, NOW(), 1, NOW(), 1),
    (67, 11, 2, 2, 1700.00, 1200.00, NOW(), 1, NOW(), 1),
    (68, 11, 1, 1, 2400.00, 1300.00, NOW(), 1, NOW(), 1),
    (69, 12, 2, 3, 2000.00, 0.00, NOW(), 1, NOW(), 1),
    (70, 12, 2, 1, 1900.00, 1500.00, NOW(), 1, NOW(), 1),
    (71, 12, 3, 3, 2800.00, 0.00, NOW(), 1, NOW(), 1),
    (72, 12, 2, 1, 2300.00, 1700.00, NOW(), 1, NOW(), 1),
    (73, 12, 2, 2, 600.00, 400.00, NOW(), 1, NOW(), 1),
    (74, 12, 2, 2, 800.00, 500.00, NOW(), 1, NOW(), 1),
    (75, 12, 1, 1, 1500.00, 1000.00, NOW(), 1, NOW(), 1),
    (76, 12, 2, 2, 2500.00, 1500.00, NOW(), 1, NOW(), 1),
    (77, 12, 3, 3, 1800.00, 0.00, NOW(), 1, NOW(), 1),
    (78, 13, 1, 3, 500.00, 0.00, NOW(), 1, NOW(), 1),
    (79, 13, 2, 2, 1200.00, 800.00, NOW(), 1, NOW(), 1),
    (80, 13, 1, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (81, 13, 2, 1, 2100.00, 1500.00, NOW(), 1, NOW(), 1),
    (82, 14, 1, 2, 900.00, 500.00, NOW(), 1, NOW(), 1),
    (83, 14, 3, 3, 2600.00, 0.00, NOW(), 1, NOW(), 1),
    (84, 14, 2, 1, 1300.00, 1100.00, NOW(), 1, NOW(), 1),
    (85, 14, 2, 2, 2900.00, 1800.00, NOW(), 1, NOW(), 1),
    (86, 15, 1, 3, 1100.00, 0.00, NOW(), 1, NOW(), 1),
    (87, 15, 3, 3, 1500.00, 0.00, NOW(), 1, NOW(), 1),
    (88, 15, 2, 2, 1700.00, 1200.00, NOW(), 1, NOW(), 1),
    (89, 15, 1, 1, 2400.00, 1300.00, NOW(), 1, NOW(), 1),
    (90, 15, 2, 3, 2000.00, 0.00, NOW(), 1, NOW(), 1),
    (91, 15, 2, 1, 1900.00, 1500.00, NOW(), 1, NOW(), 1),
    (92, 15, 3, 3, 2800.00, 0.00, NOW(), 1, NOW(), 1),
    (93, 16, 2, 1, 2300.00, 1700.00, NOW(), 1, NOW(), 1),
    (94, 16, 2, 2, 600.00, 400.00, NOW(), 1, NOW(), 1),
    (95, 16, 2, 2, 800.00, 500.00, NOW(), 1, NOW(), 1),
    (96, 16, 1, 1, 1500.00, 1000.00, NOW(), 1, NOW(), 1),
    (97, 16, 2, 2, 2500.00, 1500.00, NOW(), 1, NOW(), 1),
    (98, 16, 3, 3, 1800.00, 0.00, NOW(), 1, NOW(), 1),
    (99, 16, 1, 3, 500.00, 0.00, NOW(), 1, NOW(), 1),
    (100, 17, 2, 2, 1200.00, 800.00, NOW(), 1, NOW(), 1),
    (101, 11, 1, 2, 1500.00, 900.00, NOW(), 1, NOW(), 1),
    (102, 11, 2, 3, 2500.00, 1500.00, NOW(), 1, NOW(), 1),
    (103, 11, 3, 1, 1800.00, 0.00, NOW(), 1, NOW(), 1),
    (104, 11, 1, 3, 500.00, 0.00, NOW(), 1, NOW(), 1),
    (105, 12, 2, 2, 1200.00, 800.00, NOW(), 1, NOW(), 1),
    (106, 12, 1, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (107, 12, 2, 1, 2100.00, 1500.00, NOW(), 1, NOW(), 1),
    (108, 13, 1, 2, 900.00, 500.00, NOW(), 1, NOW(), 1),
    (109, 13, 3, 3, 2600.00, 0.00, NOW(), 1, NOW(), 1),
    (110, 13, 2, 1, 1300.00, 1100.00, NOW(), 1, NOW(), 1),
    (111, 13, 2, 2, 2900.00, 1800.00, NOW(), 1, NOW(), 1),
    (112, 14, 1, 3, 1100.00, 0.00, NOW(), 1, NOW(), 1),
    (113, 14, 3, 3, 1500.00, 0.00, NOW(), 1, NOW(), 1),
    (114, 14, 2, 2, 1700.00, 1200.00, NOW(), 1, NOW(), 1),
    (115, 14, 1, 1, 2400.00, 1300.00, NOW(), 1, NOW(), 1),
    (116, 15, 2, 3, 2000.00, 0.00, NOW(), 1, NOW(), 1),
    (117, 15, 2, 1, 1900.00, 1500.00, NOW(), 1, NOW(), 1),
    (118, 15, 3, 3, 2800.00, 0.00, NOW(), 1, NOW(), 1),
    (119, 15, 2, 1, 2300.00, 1700.00, NOW(), 1, NOW(), 1),
    (120, 15, 2, 2, 600.00, 400.00, NOW(), 1, NOW(), 1),
    (121, 15, 2, 2, 800.00, 500.00, NOW(), 1, NOW(), 1),
    (122, 16, 1, 1, 1500.00, 1000.00, NOW(), 1, NOW(), 1),
    (123, 16, 2, 2, 2500.00, 1500.00, NOW(), 1, NOW(), 1),
    (124, 16, 3, 3, 1800.00, 0.00, NOW(), 1, NOW(), 1),
    (125, 16, 1, 3, 500.00, 0.00, NOW(), 1, NOW(), 1),
    (126, 17, 2, 2, 1200.00, 800.00, NOW(), 1, NOW(), 1),
    (127, 17, 1, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (128, 17, 2, 1, 2100.00, 1500.00, NOW(), 1, NOW(), 1),
    (129, 18, 1, 2, 900.00, 500.00, NOW(), 1, NOW(), 1),
    (130, 18, 3, 3, 2600.00, 0.00, NOW(), 1, NOW(), 1),
    (131, 18, 2, 1, 1300.00, 1100.00, NOW(), 1, NOW(), 1),
    (132, 18, 2, 2, 2900.00, 1800.00, NOW(), 1, NOW(), 1),
    (133, 19, 1, 3, 1100.00, 0.00, NOW(), 1, NOW(), 1),
    (134, 19, 3, 3, 1500.00, 0.00, NOW(), 1, NOW(), 1),
    (135, 19, 2, 2, 1700.00, 1200.00, NOW(), 1, NOW(), 1),
    (136, 19, 1, 1, 2400.00, 1300.00, NOW(), 1, NOW(), 1),
    (137, 20, 2, 3, 2000.00, 0.00, NOW(), 1, NOW(), 1),
    (138, 20, 2, 1, 1900.00, 1500.00, NOW(), 1, NOW(), 1),
    (139, 20, 3, 3, 2800.00, 0.00, NOW(), 1, NOW(), 1),
    (140, 20, 2, 1, 2300.00, 1700.00, NOW(), 1, NOW(), 1),
    (141, 20, 2, 2, 600.00, 400.00, NOW(), 1, NOW(), 1),
    (142, 21, 2, 3, 800.00, 300.00, NOW(), 1, NOW(), 1),
    (143, 21, 1, 1, 1500.00, 700.00, NOW(), 1, NOW(), 1),
    (144, 21, 2, 2, 2400.00, 1200.00, NOW(), 1, NOW(), 1),
    (145, 21, 3, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (146, 22, 1, 2, 1100.00, 800.00, NOW(), 1, NOW(), 1),
    (147, 22, 2, 3, 500.00, 0.00, NOW(), 1, NOW(), 1),
    (148, 22, 2, 1, 2300.00, 1700.00, NOW(), 1, NOW(), 1),
    (149, 22, 3, 2, 800.00, 600.00, NOW(), 1, NOW(), 1),
    (150, 22, 1, 3, 2700.00, 0.00, NOW(), 1, NOW(), 1),
    (151, 23, 2, 1, 1400.00, 1000.00, NOW(), 1, NOW(), 1),
    (152, 23, 3, 3, 2500.00, 0.00, NOW(), 1, NOW(), 1),
    (153, 23, 1, 2, 1200.00, 800.00, NOW(), 1, NOW(), 1),
    (154, 23, 2, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (155, 24, 2, 1, 2200.00, 1500.00, NOW(), 1, NOW(), 1),
    (156, 24, 3, 2, 800.00, 500.00, NOW(), 1, NOW(), 1),
    (157, 24, 1, 3, 1700.00, 0.00, NOW(), 1, NOW(), 1),
    (158, 24, 2, 2, 2900.00, 1800.00, NOW(), 1, NOW(), 1),
    (159, 25, 1, 2, 900.00, 600.00, NOW(), 1, NOW(), 1),
    (160, 25, 3, 3, 2500.00, 0.00, NOW(), 1, NOW(), 1),
    (161, 25, 2, 1, 1900.00, 1300.00, NOW(), 1, NOW(), 1),
    (162, 25, 2, 2, 1600.00, 1100.00, NOW(), 1, NOW(), 1),
    (163, 26, 1, 3, 1300.00, 0.00, NOW(), 1, NOW(), 1),
    (164, 26, 3, 2, 800.00, 500.00, NOW(), 1, NOW(), 1),
    (165, 26, 2, 1, 2500.00, 1500.00, NOW(), 1, NOW(), 1),
    (166, 26, 2, 3, 1700.00, 0.00, NOW(), 1, NOW(), 1),
    (167, 27, 1, 2, 1400.00, 1100.00, NOW(), 1, NOW(), 1),
    (168, 27, 3, 3, 2000.00, 0.00, NOW(), 1, NOW(), 1),
    (169, 27, 2, 1, 1800.00, 1200.00, NOW(), 1, NOW(), 1),
    (170, 27, 2, 2, 2600.00, 1600.00, NOW(), 1, NOW(), 1),
    (171, 28, 1, 3, 1100.00, 0.00, NOW(), 1, NOW(), 1),
    (172, 28, 2, 2, 2100.00, 1400.00, NOW(), 1, NOW(), 1),
    (173, 28, 3, 1, 900.00, 700.00, NOW(), 1, NOW(), 1),
    (174, 28, 2, 3, 2400.00, 0.00, NOW(), 1, NOW(), 1),
    (175, 29, 1, 2, 1500.00, 1000.00, NOW(), 1, NOW(), 1),
    (176, 29, 3, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (177, 29, 2, 1, 1900.00, 1500.00, NOW(), 1, NOW(), 1),
    (178, 29, 2, 2, 1700.00, 1300.00, NOW(), 1, NOW(), 1),
    (179, 30, 1, 3, 1200.00, 0.00, NOW(), 1, NOW(), 1),
    (180, 30, 2, 1, 2200.00, 1500.00, NOW(), 1, NOW(), 1),
    (181, 30, 3, 2, 1000.00, 600.00, NOW(), 1, NOW(), 1),
    (182, 30, 2, 3, 1800.00, 0.00, NOW(), 1, NOW(), 1),
    (183, 31, 1, 2, 1300.00, 800.00, NOW(), 1, NOW(), 1),
    (184, 31, 3, 1, 2600.00, 2000.00, NOW(), 1, NOW(), 1),
    (185, 31, 2, 2, 1600.00, 1000.00, NOW(), 1, NOW(), 1),
    (186, 31, 2, 3, 2100.00, 0.00, NOW(), 1, NOW(), 1),
    (187, 32, 1, 2, 1100.00, 700.00, NOW(), 1, NOW(), 1),
    (188, 32, 3, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (189, 32, 2, 1, 1900.00, 1400.00, NOW(), 1, NOW(), 1),
    (190, 32, 2, 2, 800.00, 500.00, NOW(), 1, NOW(), 1),
    (191, 33, 1, 3, 1500.00, 0.00, NOW(), 1, NOW(), 1),
    (192, 33, 2, 2, 2500.00, 1600.00, NOW(), 1, NOW(), 1),
    (193, 33, 3, 1, 1800.00, 1200.00, NOW(), 1, NOW(), 1),
    (194, 33, 2, 3, 1700.00, 0.00, NOW(), 1, NOW(), 1),
    (195, 34, 1, 2, 1200.00, 900.00, NOW(), 1, NOW(), 1),
    (196, 34, 3, 3, 2400.00, 0.00, NOW(), 1, NOW(), 1),
    (197, 34, 2, 1, 1600.00, 1300.00, NOW(), 1, NOW(), 1),
    (198, 34, 2, 2, 1900.00, 1400.00, NOW(), 1, NOW(), 1),
    (199, 34, 1, 3, 1300.00, 0.00, NOW(), 1, NOW(), 1),
    (200, 34, 2, 1, 1500.00, 1000.00, NOW(), 1, NOW(), 1),
    (201, 35, 2, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1),
    (202, 35, 3, 1, 1800.00, 1400.00, NOW(), 1, NOW(), 1),
    (203, 35, 1, 2, 1500.00, 1100.00, NOW(), 1, NOW(), 1),
    (204, 35, 2, 1, 2400.00, 1800.00, NOW(), 1, NOW(), 1),
    (205, 36, 2, 2, 1700.00, 1300.00, NOW(), 1, NOW(), 1),
    (206, 36, 3, 3, 2000.00, 0.00, NOW(), 1, NOW(), 1),
    (207, 36, 1, 3, 1200.00, 0.00, NOW(), 1, NOW(), 1),
    (208, 36, 2, 1, 1900.00, 1500.00, NOW(), 1, NOW(), 1),
    (209, 37, 3, 2, 800.00, 500.00, NOW(), 1, NOW(), 1),
    (210, 37, 2, 3, 1300.00, 0.00, NOW(), 1, NOW(), 1),
    (211, 37, 1, 2, 2200.00, 1600.00, NOW(), 1, NOW(), 1),
    (212, 37, 2, 1, 1400.00, 1000.00, NOW(), 1, NOW(), 1),
    (213, 38, 3, 3, 2700.00, 0.00, NOW(), 1, NOW(), 1),
    (214, 38, 1, 2, 1100.00, 700.00, NOW(), 1, NOW(), 1),
    (215, 38, 2, 1, 2500.00, 1800.00, NOW(), 1, NOW(), 1),
    (216, 38, 2, 2, 1900.00, 1400.00, NOW(), 1, NOW(), 1),
    (217, 39, 1, 3, 1500.00, 0.00, NOW(), 1, NOW(), 1),
    (218, 39, 2, 2, 2100.00, 1600.00, NOW(), 1, NOW(), 1),
    (219, 39, 3, 1, 1800.00, 1200.00, NOW(), 1, NOW(), 1),
    (220, 39, 2, 3, 1700.00, 0.00, NOW(), 1, NOW(), 1),
    (221, 40, 1, 2, 1200.00, 800.00, NOW(), 1, NOW(), 1),
    (222, 40, 3, 3, 2400.00, 0.00, NOW(), 1, NOW(), 1),
    (223, 40, 2, 1, 1600.00, 1300.00, NOW(), 1, NOW(), 1),
    (224, 40, 2, 2, 1900.00, 1400.00, NOW(), 1, NOW(), 1),
    (225, 41, 1, 3, 1300.00, 0.00, NOW(), 1, NOW(), 1),
    (226, 41, 2, 2, 2500.00, 1800.00, NOW(), 1, NOW(), 1),
    (227, 41, 3, 1, 1400.00, 1000.00, NOW(), 1, NOW(), 1),
    (228, 41, 2, 3, 1900.00, 0.00, NOW(), 1, NOW(), 1),
    (229, 42, 1, 2, 1100.00, 900.00, NOW(), 1, NOW(), 1),
    (230, 42, 3, 3, 3000.00, 0.00, NOW(), 1, NOW(), 1);


INSERT INTO plot_owners (plot_id, owner_id, created_datetime, created_user, last_updated_datetime, last_updated_user)
VALUES
    (1, 1, NOW(), 1, NOW(), 1),
    (2, 1, NOW(), 1, NOW(), 1),
    (3, 2, NOW(), 1, NOW(), 1),
    (5, 3, NOW(), 1, NOW(), 1),
    (7, 4, NOW(), 1, NOW(), 1),
    (10, 5, NOW(), 1, NOW(), 1),
    (11, 6, NOW(), 1, NOW(), 1),
    (14, 7, NOW(), 1, NOW(), 1),
    (16, 8, NOW(), 1, NOW(), 1),
    (17, 9, NOW(), 1, NOW(), 1),
    (19, 10, NOW(), 1, NOW(), 1),
    (20, 10, NOW(), 1, NOW(), 1),
    (21, 11, NOW(), 1, NOW(), 1);

DELIMITER $$

CREATE TRIGGER trg_taxstatus_insert
    AFTER INSERT ON tax_status
    FOR EACH ROW
BEGIN
    INSERT INTO tax_status_audit
    (id, version, description, created_datetime, created_user, last_updated_datetime, last_updated_user)
    VALUES
        (NEW.id, 1, NEW.description, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
    END$$

    DELIMITER ;

DELIMITER $$

    CREATE TRIGGER trg_dnitypes_insert
        AFTER INSERT ON dni_types
        FOR EACH ROW
    BEGIN
        INSERT INTO dni_types_audit
        (id, version, description, created_datetime, created_user, last_updated_datetime, last_updated_user)
        VALUES
            (NEW.id, 1, NEW.description, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
        END$$

        CREATE TRIGGER trg_dni_type_update
            AFTER UPDATE ON dni_types
            FOR EACH ROW
        BEGIN
            DECLARE latest_version INT;
            SELECT MAX(version) INTO latest_version FROM dni_types_audit WHERE id = NEW.id;
            SET latest_version = IFNULL(latest_version, 0) + 1;

            INSERT INTO dni_types_audit (id, version, description, created_datetime, last_updated_datetime, created_user, last_updated_user)
            VALUES (NEW.id, latest_version, NEW.description, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
        END $$

DELIMITER ;

        DELIMITER $$

        CREATE TRIGGER trg_taxstatus_update
            AFTER UPDATE ON tax_status
            FOR EACH ROW
        BEGIN
            DECLARE version_number INT;

            SELECT IFNULL(MAX(version), 0) + 1 INTO version_number FROM TaxStatus_audit WHERE id = OLD.id;

            INSERT INTO TaxStatus_audit
            (id, version, description, created_datetime, created_user, last_updated_datetime, last_updated_user)
            VALUES
                (OLD.id, version_number, OLD.description, OLD.created_datetime, OLD.created_user, OLD.last_updated_datetime, OLD.last_updated_user);
            END$$

            DELIMITER ;

DELIMITER $$

            CREATE TRIGGER trg_owners_insert
                AFTER INSERT ON owners
                FOR EACH ROW
            BEGIN
                INSERT INTO owners_audit
                (id, version, name, lastname, dni, dni_type_id, date_birth, tax_status_id, owner_type_id, business_name, active, created_datetime, created_user, last_updated_datetime, last_updated_user)
                VALUES
                    (NEW.id, 1, NEW.name, NEW.lastname, NEW.dni,NEW.dni_type_id, NEW.date_birth, NEW.tax_status_id, NEW.owner_type_id, NEW.business_name, NEW.active, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
                END$$

                DELIMITER ;

DELIMITER $$

                CREATE TRIGGER trg_owners_update
                    AFTER UPDATE ON owners
                    FOR EACH ROW
                BEGIN
                    DECLARE version_number INT;

                    SELECT IFNULL(MAX(version), 0) + 1 INTO version_number FROM owners_audit WHERE id = OLD.id;

                    INSERT INTO owners_audit
                    (id, version, name, lastname, dni, dni_type_id, date_birth, tax_status_id, owner_type_id, business_name, active, created_datetime, created_user, last_updated_datetime, last_updated_user)
                    VALUES
                        (OLD.id, version_number, OLD.name, OLD.lastname, OLD.dni, OLD.dni_type_id, OLD.date_birth, OLD.tax_status_id, OLD.owner_type_id, OLD.business_name, OLD.active, OLD.created_datetime, OLD.created_user, OLD.last_updated_datetime, OLD.last_updated_user);
                    END$$
