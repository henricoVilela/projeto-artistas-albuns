package com.projeto.backend.web.dto.album;

import java.time.LocalDateTime;

import com.projeto.backend.domain.album.AlbumCapa;
import com.projeto.backend.domain.album.TipoCapa;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados da capa do álbum")
public class AlbumCapaResponse {

    @Schema(description = "ID da capa", example = "1")
    private Long id;

    @Schema(description = "ID do álbum", example = "1")
    private Long albumId;

    @Schema(description = "Nome original do arquivo", example = "capa-frente.jpg")
    private String nomeArquivo;

    @Schema(description = "Tipo de conteúdo", example = "image/jpeg")
    private String contentType;

    @Schema(description = "Tamanho em bytes", example = "245678")
    private Long tamanhoBytes;

    @Schema(description = "Tipo da capa", example = "FRENTE")
    private TipoCapa tipoCapa;

    @Schema(description = "Descrição do tipo", example = "Capa Frontal")
    private String tipoCapaDescricao;

    @Schema(description = "Ordem de exibição", example = "0")
    private Integer ordem;

    @Schema(description = "URL pré-assinada para acesso à imagem (expira em 30 minutos)")
    private String url;

    @Schema(description = "Data de upload")
    private LocalDateTime createdAt;

    // Construtores
    public AlbumCapaResponse() {
    }

    /**
     * Cria um AlbumCapaResponse a partir da entidade.
     */
    public static AlbumCapaResponse fromEntity(AlbumCapa capa) {
        AlbumCapaResponse response = new AlbumCapaResponse();
        response.setId(capa.getId());
        response.setAlbumId(capa.getAlbum().getId());
        response.setNomeArquivo(capa.getNomeArquivo());
        response.setContentType(capa.getContentType());
        response.setTamanhoBytes(capa.getTamanhoBytes());
        response.setTipoCapa(capa.getTipoCapa());
        response.setTipoCapaDescricao(capa.getTipoCapa().getDescricao());
        response.setOrdem(capa.getOrdem());
        response.setCreatedAt(capa.getCreatedAt());
        return response;
    }

    /**
     * Cria um AlbumCapaResponse com URL pré-assinada.
     */
    public static AlbumCapaResponse fromEntityWithUrl(AlbumCapa capa, String presignedUrl) {
        AlbumCapaResponse response = fromEntity(capa);
        response.setUrl(presignedUrl);
        return response;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getTamanhoBytes() {
        return tamanhoBytes;
    }

    public void setTamanhoBytes(Long tamanhoBytes) {
        this.tamanhoBytes = tamanhoBytes;
    }

    public TipoCapa getTipoCapa() {
        return tipoCapa;
    }

    public void setTipoCapa(TipoCapa tipoCapa) {
        this.tipoCapa = tipoCapa;
    }

    public String getTipoCapaDescricao() {
        return tipoCapaDescricao;
    }

    public void setTipoCapaDescricao(String tipoCapaDescricao) {
        this.tipoCapaDescricao = tipoCapaDescricao;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
