INSERT INTO espaco(nome, capacidade, recursos_disponiveis, localizacao, disponibilidade, tipo_espaco)
VALUES
    ('Sala 1', 20, 'Computador, Projetor, Mesas', '1o Andar', 'DISPONIVEL', 'SALA_DE_AULA'),
    ('Auditorio', 50, 'Palco, Cortina, Projetor', '2o Andar', 'RESERVADO', 'AUDITORIO'),
    ('Lab 1', 10, 'Quadro, Jalecos, Oculos', '3o Andar', 'DISPONIVEL', 'LABORATORIO')

INSERT INTO usuario(nome, email, senha, data_registro, usuario_tipo)
VALUES ('Pedro Vidigal', 'prof@prof.com', '$2a$10$mECCwfIdgmI8FqoIPwlwg.cetGukOzd59cgs10kw7DNWnE9EQPwn6', '2024-01-01', 'PROFESSOR'),
    ('Clovis Dantas', 'admin@admin.com', '$2a$10$mECCwfIdgmI8FqoIPwlwg.cetGukOzd59cgs10kw7DNWnE9EQPwn6', '2024-01-01', 'ADMINISTRADOR'),
    ('Guilherme Gentili', 'gestor@gestor.com', '$2a$10$mECCwfIdgmI8FqoIPwlwg.cetGukOzd59cgs10kw7DNWnE9EQPwn6', '2024-01-01', 'GESTOR')
