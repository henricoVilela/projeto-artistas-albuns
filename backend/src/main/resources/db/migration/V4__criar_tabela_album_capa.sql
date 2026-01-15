CREATE TABLE album_capa (
    id              BIGSERIAL       PRIMARY KEY,
    album_id        BIGINT          NOT NULL,
    object_key      VARCHAR(500)    NOT NULL,
    nome_arquivo    VARCHAR(255)    NOT NULL,
    content_type    VARCHAR(100)    NOT NULL,
    tamanho_bytes   BIGINT          NOT NULL,
    tipo_capa       VARCHAR(50)     NOT NULL DEFAULT 'FRENTE',
    ordem           INTEGER         NOT NULL DEFAULT 0,
    hash_md5        VARCHAR(32)     NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_album_capa_album FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE,
    
    CONSTRAINT uk_album_capa_object_key UNIQUE (object_key),
    CONSTRAINT ck_album_capa_tipo CHECK (tipo_capa IN ('FRENTE', 'VERSO', 'ENCARTE', 'DISCO', 'PROMOCIONAL', 'OUTRO')),
    CONSTRAINT ck_album_capa_tamanho CHECK (tamanho_bytes > 0)
);

CREATE INDEX idx_album_capa_album_id ON album_capa(album_id);
CREATE INDEX idx_album_capa_tipo ON album_capa(tipo_capa);
CREATE INDEX idx_album_capa_ordem ON album_capa(album_id, ordem);

COMMENT ON TABLE album_capa IS 'Tabela de capas dos álbuns armazenadas no MinIO';
COMMENT ON COLUMN album_capa.object_key IS 'Chave do objeto no MinIO (path completo)';
COMMENT ON COLUMN album_capa.nome_arquivo IS 'Nome original do arquivo enviado';
COMMENT ON COLUMN album_capa.content_type IS 'MIME type do arquivo (image/jpeg, image/png, etc)';
COMMENT ON COLUMN album_capa.tipo_capa IS 'Tipo da capa: FRENTE, VERSO, ENCARTE, DISCO, PROMOCIONAL ou OUTRO';
COMMENT ON COLUMN album_capa.ordem IS 'Ordem de exibição da capa';
COMMENT ON COLUMN album_capa.hash_md5 IS 'Hash MD5 do arquivo para verificação de integridade';

