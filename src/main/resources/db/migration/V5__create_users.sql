-- password = technosdev
INSERT INTO `user` (`cod_user`, `active`, `nmr_cpf`, `created_at`, `email_user`, `nm_user`, `password`, `nmr_phone`) VALUES ('1', 1, '13159060900', '2024-09-08 14:26:19', 'lucas@technosdev.com', 'Lucas Gabriel', '$2a$10$Oev9bZqgoWIb1gVe8eKURujH1b45nfH59G5C2aUowKttLMX5/st5K', '554799544286');
INSERT INTO `user` (`cod_user`, `active`, `nmr_cpf`, `created_at`, `email_user`, `nm_user`, `password`, `nmr_phone`) VALUES ('2', 1, '14762607029', '2024-09-08 14:26:19', 'pedro@technosdev.com', 'Pedro Teloeken', '$2a$10$Oev9bZqgoWIb1gVe8eKURujH1b45nfH59G5C2aUowKttLMX5/st5K', '554784555508');

INSERT INTO `user_roles` (`cod_role`, `cod_user`) VALUES ('1', '1');
INSERT INTO `user_roles` (`cod_role`, `cod_user`) VALUES ('1', '2');
