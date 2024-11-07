CREATE TABLE address
(
    cod_address     BIGINT AUTO_INCREMENT NOT NULL,
    cod_muni        BIGINT       NOT NULL,
    nm_public_place VARCHAR(50)  NOT NULL,
    nm_locality     VARCHAR(50)  NOT NULL,
    nm_cep          VARCHAR(8)   NOT NULL,
    ds_complement   VARCHAR(100) NOT NULL,
    latitude        DOUBLE NOT NULL,
    longitude        DOUBLE NOT NULL,
    CONSTRAINT pk_address PRIMARY KEY (cod_address)
);

CREATE TABLE city
(
    cod_city  BIGINT AUTO_INCREMENT NOT NULL,
    cod_state BIGINT NULL,
    nm_city   VARCHAR(50) NOT NULL,
    CONSTRAINT pk_city PRIMARY KEY (cod_city)
);

CREATE TABLE company
(
    cod_company              BIGINT AUTO_INCREMENT NOT NULL,
    cod_address              BIGINT NULL,
    cod_user                 BIGINT NULL,
    cod_flow_informations    BIGINT NULL,
    cod_company_informations BIGINT NULL,
    active                   BIT(1) NOT NULL,
    created_at               datetime NULL,
    CONSTRAINT pk_company PRIMARY KEY (cod_company)
);

CREATE TABLE company_informations
(
    cod_company_informations BIGINT AUTO_INCREMENT NOT NULL,
    nm_company               VARCHAR(100) NOT NULL,
    nmr_cnpj                 VARCHAR(30)  NOT NULL,
    nm_email                 VARCHAR(50)  NOT NULL,
    nmr_phone                VARCHAR(12)  NOT NULL,
    password                 VARCHAR(255) NOT NULL,
    start_time               TIME NOT NULL,
    end_time                 TIME NOT NULL,
    average_time_work        INT(11) NOT NULL,
    CONSTRAINT pk_company_informations PRIMARY KEY (cod_company_informations)
);

CREATE TABLE employee
(
    cod_empl        BIGINT AUTO_INCREMENT NOT NULL,
    cod_user        BIGINT NULL,
    contact_user    VARCHAR(20) NOT NULL,
    hired_in        datetime NULL,
    active          BIT(1) NOT NULL,
    created_at      datetime NULL,
    CONSTRAINT pk_employee PRIMARY KEY (cod_empl)
);

CREATE TABLE employee_company
(
    cod_employee_company BIGINT AUTO_INCREMENT NOT NULL,
    cod_employee         BIGINT NULL,
    cod_company          BIGINT NULL,
    CONSTRAINT pk_employee_company PRIMARY KEY (cod_employee_company)
);

CREATE TABLE employee_service
(
    cod_employee_service BIGINT AUTO_INCREMENT NOT NULL,
    cod_service          BIGINT NULL,
    cod_employee         BIGINT NULL,
    CONSTRAINT pk_employee_service PRIMARY KEY (cod_employee_service)
);

CREATE TABLE flow_informations
(
    cod_flow_informations BIGINT AUTO_INCREMENT NOT NULL,
    nmr_phone             VARCHAR(12) NULL,
    default_message       VARCHAR(150) NULL,
    flow_message          VARCHAR(45) NULL,
    flow_cta              VARCHAR(10) NULL,
    phone_number_id       VARCHAR(45) NULL,
    flow_id               VARCHAR(45) NULL,
    CONSTRAINT pk_flow_informations PRIMARY KEY (cod_flow_informations)
);

CREATE TABLE `role`
(
    cod_role BIGINT AUTO_INCREMENT NOT NULL,
    nm_role  VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (cod_role)
);

CREATE TABLE scheduling
(
    cod_scheduling        BIGINT AUTO_INCREMENT NOT NULL,
    cod_client            BIGINT NULL,
    cod_scheduling_status BIGINT NULL,
    cod_company           BIGINT NULL,
    date                  datetime NULL,
    CONSTRAINT pk_scheduling PRIMARY KEY (cod_scheduling)
);

CREATE TABLE scheduling_status
(
    cod_scheduling_status BIGINT AUTO_INCREMENT NOT NULL,
    nm_status             VARCHAR(50) NOT NULL,
    active                BIT(1)      NOT NULL,
    created_at            datetime NULL,
    CONSTRAINT pk_scheduling_status PRIMARY KEY (cod_scheduling_status)
);

CREATE TABLE service
(
    cod_service  BIGINT AUTO_INCREMENT NOT NULL,
    cod_company  BIGINT NULL,
    nm_service   VARCHAR(100) NOT NULL,
    ds_service   VARCHAR(200) NOT NULL,
    prc_service DOUBLE NOT NULL,
    average_time INT          NOT NULL,
    active       BIT(1)       NOT NULL,
    created_at   datetime NULL,
    CONSTRAINT pk_service PRIMARY KEY (cod_service)
);

CREATE TABLE service_order
(
    cod_service_order BIGINT AUTO_INCREMENT NOT NULL,
    cod_scheduling    BIGINT NULL,
    cod_service       BIGINT NULL,
    cod_employee      BIGINT NULL,
    finished_at       datetime NULL,
    created_at        datetime NULL,
    CONSTRAINT pk_service_order PRIMARY KEY (cod_service_order)
);

CREATE TABLE state
(
    cod_state BIGINT AUTO_INCREMENT NOT NULL,
    nm_state  VARCHAR(50) NOT NULL,
    sg_uf     VARCHAR(2)  NOT NULL,
    CONSTRAINT pk_state PRIMARY KEY (cod_state)
);

CREATE TABLE user
(
    cod_user   BIGINT AUTO_INCREMENT NOT NULL,
    nm_user    VARCHAR(80) NOT NULL,
    email_user VARCHAR(100) NULL,
    password   VARCHAR(255) NULL,
    nmr_cpf    VARCHAR(20) NULL,
    nmr_phone  VARCHAR(12)  NOT NULL,
    active     BIT(1)       NOT NULL,
    created_at datetime NULL,
    CONSTRAINT pk_user PRIMARY KEY (cod_user)
);

CREATE TABLE user_roles
(
    cod_user_role BIGINT AUTO_INCREMENT NOT NULL,
    cod_user      BIGINT NULL,
    cod_role      BIGINT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (cod_user_role)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_nmr_cpf UNIQUE (nmr_cpf);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_COD_MUNI FOREIGN KEY (cod_muni) REFERENCES city (cod_city);

ALTER TABLE city
    ADD CONSTRAINT FK_CITY_ON_COD_STATE FOREIGN KEY (cod_state) REFERENCES state (cod_state);

ALTER TABLE company
    ADD CONSTRAINT FK_COMPANY_ON_COD_ADDRESS FOREIGN KEY (cod_address) REFERENCES address (cod_address);

ALTER TABLE company
    ADD CONSTRAINT FK_COMPANY_ON_COD_COMPANY_INFORMATIONS FOREIGN KEY (cod_company_informations) REFERENCES company_informations (cod_company_informations);

ALTER TABLE company
    ADD CONSTRAINT FK_COMPANY_ON_COD_FLOW_INFORMATIONS FOREIGN KEY (cod_flow_informations) REFERENCES flow_informations (cod_flow_informations);

ALTER TABLE company
    ADD CONSTRAINT FK_COMPANY_ON_COD_USER FOREIGN KEY (cod_user) REFERENCES user (cod_user);

ALTER TABLE employee_company
    ADD CONSTRAINT FK_EMPLOYEE_COMPANY_ON_COD_COMPANY FOREIGN KEY (cod_company) REFERENCES company (cod_company);

ALTER TABLE employee_company
    ADD CONSTRAINT FK_EMPLOYEE_COMPANY_ON_COD_EMPLOYEE FOREIGN KEY (cod_employee) REFERENCES employee (cod_empl);

ALTER TABLE employee
    ADD CONSTRAINT FK_EMPLOYEE_ON_COD_USER FOREIGN KEY (cod_user) REFERENCES user (cod_user);

ALTER TABLE employee_service
    ADD CONSTRAINT FK_EMPLOYEE_SERVICE_ON_COD_EMPL FOREIGN KEY (cod_employee) REFERENCES employee (cod_empl);

ALTER TABLE employee_service
    ADD CONSTRAINT FK_EMPLOYEE_SERVICE_ON_COD_SERVICE FOREIGN KEY (cod_service) REFERENCES service (cod_service);

ALTER TABLE scheduling
    ADD CONSTRAINT FK_SCHEDULING_ON_COD_CLIENT FOREIGN KEY (cod_client) REFERENCES user (cod_user);

ALTER TABLE scheduling
    ADD CONSTRAINT FK_SCHEDULING_ON_COD_COMPANY FOREIGN KEY (cod_company) REFERENCES company (cod_company);

ALTER TABLE scheduling
    ADD CONSTRAINT FK_SCHEDULING_ON_COD_SCHEDULING_STATUS FOREIGN KEY (cod_scheduling_status) REFERENCES scheduling_status (cod_scheduling_status);

ALTER TABLE service
    ADD CONSTRAINT FK_SERVICE_ON_COD_COMPANY FOREIGN KEY (cod_company) REFERENCES company (cod_company);

ALTER TABLE service_order
    ADD CONSTRAINT FK_SERVICE_ORDER_ON_COD_EMPLOYEE FOREIGN KEY (cod_employee) REFERENCES employee (cod_empl);

ALTER TABLE service_order
    ADD CONSTRAINT FK_SERVICE_ORDER_ON_COD_SCHEDULING FOREIGN KEY (cod_scheduling) REFERENCES scheduling (cod_scheduling);

ALTER TABLE service_order
    ADD CONSTRAINT FK_SERVICE_ORDER_ON_COD_SERVICE FOREIGN KEY (cod_service) REFERENCES service (cod_service);

ALTER TABLE user_roles
    ADD CONSTRAINT FK_USER_ROLES_ON_COD_ROLE FOREIGN KEY (cod_role) REFERENCES `role` (cod_role);

ALTER TABLE user_roles
    ADD CONSTRAINT FK_USER_ROLES_ON_COD_USER FOREIGN KEY (cod_user) REFERENCES user (cod_user);