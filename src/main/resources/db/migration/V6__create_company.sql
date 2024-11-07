-- CREATE COMPANY
INSERT INTO `flow_informations` (`cod_flow_informations`, `default_message`, `flow_cta`, `flow_message`, `nmr_phone`, `phone_number_id`, `flow_id`) VALUES ('1', 'Olá! Como posso ajudar você hoje a agendar seu próximo corte ou serviço de barbearia. É um prazer tê-lo conosco!', 'Agendar', 'Agendamento de maneira rápida e prática', '554799281556', '373516032513406', '1078519787098834');
INSERT INTO `address` (`cod_address`, `nm_cep`, `ds_complement`, `nm_locality`, `nm_public_place`, `cod_muni`, `latitude`, `longitude`) VALUES ('1', '89070330', 'Rua da esquina', 'Badenfurt', 'Av. Paulista', '1', '-26.9024343355583', '-49.15009953671555');
INSERT INTO `user` (`cod_user`, `active`, `nmr_cpf`, `created_at`, `email_user`, `nm_user`, `password`, `nmr_phone`) VALUES ('3', 1, '07646036048', '2024-09-09 21:09:15', 'joao.silva@gmail.com', 'João Silva', '$2a$10$vfFcU0xBR8.YRzMeIBoXv.i2AQ/lT8Z7D6p6SiIUI.gla1NonyOCW', '554799542257');
INSERT INTO `company_informations` (`cod_company_informations`, `nm_company`, `nmr_cnpj`, `nm_email`, `nmr_phone`, `password`, `start_time`, `end_time`, `average_time_work`) VALUES ('1', 'Barbearia Barber Lounge', '12345678000190', 'contato@barberlounge.com', '551187654321', '$2a$10$uc8v/vzgqpAhXHCLRrKxOOY.5jwliwj8EDvScIvECzHDkUoIpw7u6', '08:00:00', '18:00:00', 35);

INSERT INTO `company` (`cod_company`, `cod_address`, `cod_user`, `cod_flow_informations`, `cod_company_informations`, `active`, `created_at`) VALUES ('1', '1', '3', '1', '1', 1, '2024-09-09 22:10:05');

-- CREATE EMPLOYEE
INSERT INTO `user` (`cod_user`, `nm_user`, `email_user`, `password`, `nmr_cpf`, `nmr_phone`, `active`, `created_at`) VALUES ('4', 'Lucas Santos', 'lucas.santos@example.com', '$2a$10$1BzUhUgsqTSkVEoiLQybiexArXs9ntLbI68pYk0pRixob9wMj0.7q', '31648702058', '479845555193', 1, '2024-09-09 22:27:41');
INSERT INTO `user_roles` (`cod_user_role`, `cod_user`, `cod_role`) VALUES ('3', '4', '4');
INSERT INTO `employee` (`cod_empl`, `cod_user`, `contact_user`, `hired_in`, `active`, `created_at`) VALUES ('1', '4', '@lucas.santos', '2024-09-09 22:27:41', 1, '2024-09-09 22:27:41');

INSERT INTO `user` (`cod_user`, `nm_user`, `email_user`, `password`, `nmr_cpf`, `nmr_phone`, `active`, `created_at`)
VALUES ('5', 'Marcos Oliveira', 'marcos.oliveira@example.com', '$2a$10$1BzUhUgsqTSkVEoiLQybiexArXs9ntLbI68pYk0pRixob9wMj0.7q', '12345678901', '48996741234', 1, '2024-09-09 22:27:41');
INSERT INTO `user_roles` (`cod_user_role`, `cod_user`, `cod_role`)
VALUES ('4', '5', '4');
INSERT INTO `employee` (`cod_empl`, `cod_user`, `contact_user`, `hired_in`, `active`, `created_at`)
VALUES ('2', '5', '@marcos.oliveira', '2024-09-09 22:27:41', 1, '2024-09-09 22:27:41');

INSERT INTO `user` (`cod_user`, `nm_user`, `email_user`, `password`, `nmr_cpf`, `nmr_phone`, `active`, `created_at`)
VALUES ('6', 'Ana Pereira', 'ana.pereira@example.com', '$2a$10$1BzUhUgsqTSkVEoiLQybiexArXs9ntLbI68pYk0pRixob9wMj0.7q', '98765432100', '51987326455', 1, '2024-09-09 22:27:41');
INSERT INTO `user_roles` (`cod_user_role`, `cod_user`, `cod_role`)
VALUES ('5', '6', '4');
INSERT INTO `employee` (`cod_empl`, `cod_user`, `contact_user`, `hired_in`, `active`, `created_at`)
VALUES ('3', '6', '@ana.pereira', '2024-09-09 22:27:41', 1, '2024-09-09 22:27:41');

INSERT INTO `user` (`cod_user`, `nm_user`, `email_user`, `password`, `nmr_cpf`, `nmr_phone`, `active`, `created_at`)
VALUES ('7', 'Juliana Costa', 'juliana.costa@example.com', '$2a$10$1BzUhUgsqTSkVEoiLQybiexArXs9ntLbI68pYk0pRixob9wMj0.7q', '21436587091', '61985412345', 1, '2024-09-09 22:27:41');
INSERT INTO `user_roles` (`cod_user_role`, `cod_user`, `cod_role`)
VALUES ('6', '7', '4');
INSERT INTO `employee` (`cod_empl`, `cod_user`, `contact_user`, `hired_in`, `active`, `created_at`)
VALUES ('4', '7', '@juliana.costa', '2024-09-09 22:27:41', 1, '2024-09-09 22:27:41');

INSERT INTO `user` (`cod_user`, `nm_user`, `email_user`, `password`, `nmr_cpf`, `nmr_phone`, `active`, `created_at`)
VALUES ('8', 'Pedro Almeida', 'pedro.almeida@example.com', '$2a$10$1BzUhUgsqTSkVEoiLQybiexArXs9ntLbI68pYk0pRixob9wMj0.7q', '10293847560', '71987654321', 1, '2024-09-09 22:27:41');
INSERT INTO `user_roles` (`cod_user_role`, `cod_user`, `cod_role`)
VALUES ('7', '8', '4');
INSERT INTO `employee` (`cod_empl`, `cod_user`, `contact_user`, `hired_in`, `active`, `created_at`)
VALUES ('5', '8', '@pedro.almeida', '2024-09-09 22:27:41', 1, '2024-09-09 22:27:41');

-- EMPLOYEE + COMPANY
INSERT INTO `employee_company` (`cod_employee_company`, `cod_employee`, `cod_company`) VALUES ('1', '1', '1');
INSERT INTO `employee_company` (`cod_employee_company`, `cod_employee`, `cod_company`) VALUES ('2', '2', '1');
INSERT INTO `employee_company` (`cod_employee_company`, `cod_employee`, `cod_company`) VALUES ('3', '3', '1');
INSERT INTO `employee_company` (`cod_employee_company`, `cod_employee`, `cod_company`) VALUES ('4', '4', '1');
INSERT INTO `employee_company` (`cod_employee_company`, `cod_employee`, `cod_company`) VALUES ('5', '5', '1');

-- SERVICES
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('1', '1', 'Corte de cabelo', 'Serviço de corte de cabelo personalizado', '50', '30', 1, '2024-09-13 18:12:17');
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('2', '1', 'Barba e cabelo', 'Combo de barba e cabelo', '80', '60', 1, '2024-09-13 18:12:17');
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('3', '1', 'Aparar barba', 'Serviço de aparar e modelar barba', '30', '20', 1, '2024-09-13 18:12:17');
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('4', '1', 'Hidratação capilar', 'Tratamento de hidratação capilar', '40', '25', 1, '2024-09-13 18:12:17');
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('5', '1', 'Corte infantil', 'Corte de cabelo para crianças', '40', '30', 1, '2024-09-13 18:12:17');
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('6', '1', 'Alisamento', 'Serviço de alisamento capilar', '120', '90', 1, '2024-09-13 18:12:17');
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('7', '1', 'Pintura capilar', 'Coloração de cabelo com tintura', '100', '75', 1, '2024-09-13 18:12:17');
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('8', '1', 'Relaxamento', 'Serviço de relaxamento capilar', '90', '70', 1, '2024-09-13 18:12:17');
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('9', '1', 'Sobrancelha', 'Design de sobrancelhas', '20', '15', 1, '2024-09-13 18:12:17');
INSERT INTO `service` (`cod_service`, `cod_company`, `nm_service`, `ds_service`, `prc_service`, `average_time`, `active`, `created_at`) VALUES ('10', '1', 'Escova progressiva', 'Serviço de escova progressiva', '150', '120', 1, '2024-09-13 18:12:17');

-- EMPLOYEE-SERVICE
-- EMPLOYEE 1
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('1', '1');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('2', '1');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('3', '1');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('4', '1');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('5', '1');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('6', '1');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('7', '1');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('8', '1');

-- EMPLOYEE 2
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('1', '2');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('2', '2');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('3', '2');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('4', '2');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('5', '2');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('6', '2');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('9', '2');

-- EMPLOYEE 3
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('1', '3');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('2', '3');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('3', '3');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('7', '3');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('8', '3');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('9', '3');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('10', '3');

-- EMPLOYEE 4
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('1', '4');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('2', '4');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('4', '4');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('6', '4');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('7', '4');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('9', '4');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('10', '4');

-- EMPLOYEE 5
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('2', '5');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('3', '5');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('5', '5');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('6', '5');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('8', '5');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('9', '5');

-- CREATE DEFAULT EMPLOYEE
INSERT INTO `user` (`cod_user`, `nm_user`, `nmr_cpf`, `nmr_phone`, `active`, `created_at`) VALUES ('9', 'Não tenho preferência', '00000000000', '000000000000', 1, '2024-09-14 15:31:56');
INSERT INTO `employee` (`cod_empl`, `cod_user`, `contact_user`, `hired_in`, `active`, `created_at`) VALUES ('6', '9', '',  '2024-09-09 22:27:41', 1, '2024-09-09 22:27:41');
INSERT INTO `employee_company` (`cod_employee_company`, `cod_employee`, `cod_company`) VALUES ('6', '6', '1');

INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('1', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('2', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('3', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('4', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('5', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('6', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('7', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('8', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('9', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('10', '6');
INSERT INTO `employee_service` (`cod_service`, `cod_employee`) VALUES ('10', '6');
