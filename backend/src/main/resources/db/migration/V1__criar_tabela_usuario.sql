-- Tabela responsável por armazenar os dados de autenticação dos usuários do sistema. Utiliza hash bcrypt para senhas.

CREATE TABLE usuario (
    id              BIGSERIAL       PRIMARY KEY,
    username        VARCHAR(50)     NOT NULL,
    email           VARCHAR(150)    NOT NULL,
    password_hash   VARCHAR(255)    NOT NULL,
    nome_completo   VARCHAR(200)    NULL,
    ativo           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT uk_usuario_username UNIQUE (username),
    CONSTRAINT uk_usuario_email UNIQUE (email)
);

-- Índices para otimização de consultas
CREATE INDEX idx_usuario_username ON usuario(username);
CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_usuario_ativo ON usuario(ativo);

-- Comentários nas colunas
COMMENT ON TABLE usuario IS 'Tabela de usuários do sistema para autenticação JWT';
COMMENT ON COLUMN usuario.username IS 'Nome de usuário único para login';
COMMENT ON COLUMN usuario.password_hash IS 'Hash da senha (bcrypt)';
