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
    ('Carmen', 'Diaz', 3, '23455948169', '1987-12-10 00:00:00', 2, 1, NULL , TRUE, NOW(), 1, NOW(), 1);

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
    (20, 5, 2, 2, 600.00, 400.00, NOW(), 1, NOW(), 1);

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
    (20, 10, NOW(), 1, NOW(), 1);

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
                    END
                      
                    DELIMITER ;
