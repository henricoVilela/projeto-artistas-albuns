CREATE TABLE album (
    id              BIGSERIAL       PRIMARY KEY,
    artista_id      BIGINT          NOT NULL,
    nome            VARCHAR(300)    NOT NULL,
    nome_busca      VARCHAR(300)    NOT NULL,
    ano_lancamento  INTEGER         NULL,
    gravadora       VARCHAR(150)    NULL,
    genero          VARCHAR(100)    NULL,
    total_faixas    INTEGER         NULL,
    duracao_total   INTEGER         NULL,
    descricao       TEXT            NULL,
    ativo           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_album_artista FOREIGN KEY (artista_id) REFERENCES artista(id) ON DELETE CASCADE,
    CONSTRAINT ck_album_ano_lancamento CHECK (ano_lancamento IS NULL OR ano_lancamento >= 1900),
    CONSTRAINT ck_album_total_faixas CHECK (total_faixas IS NULL OR total_faixas > 0),
    CONSTRAINT ck_album_duracao CHECK (duracao_total IS NULL OR duracao_total > 0)
);

CREATE INDEX idx_album_artista_id ON album(artista_id);
CREATE INDEX idx_album_nome ON album(nome);
CREATE INDEX idx_album_nome_busca ON album(nome_busca);
CREATE INDEX idx_album_ano_lancamento ON album(ano_lancamento);
CREATE INDEX idx_album_genero ON album(genero);

-- Índice para busca case-insensitive
CREATE INDEX idx_album_nome_lower ON album(LOWER(nome));

COMMENT ON TABLE album IS 'Tabela de álbuns dos artistas';
COMMENT ON COLUMN album.total_faixas IS 'Número total de faixas no álbum';
COMMENT ON COLUMN album.duracao_total IS 'Duração total do álbum em segundos';
