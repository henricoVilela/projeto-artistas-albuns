package com.projeto.backend.web.dto.artista;

import java.time.LocalDateTime;

import com.projeto.backend.domain.artista.Artista;
import com.projeto.backend.domain.artista.TipoArtista;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados do artista")
public class ArtistaResponse {

    @Schema(description = "ID do artista", example = "1")
    private Long id;

    @Schema(description = "Nome artístico", example = "Linkin Park")
    private String nome;

    @Schema(description = "Tipo do artista", example = "BANDA")
    private TipoArtista tipo;

    @Schema(description = "Descrição do tipo", example = "Banda")
    private String tipoDescricao;

    @Schema(description = "País de origem", example = "Estados Unidos")
    private String paisOrigem;

    @Schema(description = "Ano de formação", example = "1996")
    private Integer anoFormacao;

    @Schema(description = "Biografia")
    private String biografia;

    @Schema(description = "Ativo")
    private Boolean ativo;
    
    @Schema(description = "Quantidade de álbuns", example = "7")
    private Integer totalAlbuns;

    @Schema(description = "Data de criação")
    private LocalDateTime createdAt;

    @Schema(description = "Data de atualização")
    private LocalDateTime updatedAt;

    public ArtistaResponse() {
    }

    /**
     * Cria um ArtistaResponse a partir da entidade.
     */
    public static ArtistaResponse fromEntity(Artista artista) {
        ArtistaResponse response = new ArtistaResponse();
        response.setId(artista.getId());
        response.setNome(artista.getNome());
        response.setTipo(artista.getTipo());
        response.setTipoDescricao(artista.getTipo().getDescricao());
        response.setPaisOrigem(artista.getPaisOrigem());
        response.setAnoFormacao(artista.getAnoFormacao());
        response.setBiografia(artista.getBiografia());
        response.setTotalAlbuns(artista.getTotalAlbuns());
        response.setCreatedAt(artista.getCreatedAt());
        response.setUpdatedAt(artista.getUpdatedAt());
        response.setAtivo(artista.getAtivo());
        
        return response;
    }

    /**
     * Cria um ArtistaResponse com contagem de álbuns.
     */
    public static ArtistaResponse fromEntityWithAlbumCount(Artista artista, Long totalAlbuns) {
        ArtistaResponse response = fromEntity(artista);
        response.setTotalAlbuns(totalAlbuns != null ? totalAlbuns.intValue() : 0);
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoArtista getTipo() {
        return tipo;
    }

    public void setTipo(TipoArtista tipo) {
        this.tipo = tipo;
    }

    public String getTipoDescricao() {
        return tipoDescricao;
    }

    public void setTipoDescricao(String tipoDescricao) {
        this.tipoDescricao = tipoDescricao;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public Integer getAnoFormacao() {
        return anoFormacao;
    }

    public void setAnoFormacao(Integer anoFormacao) {
        this.anoFormacao = anoFormacao;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public Integer getTotalAlbuns() {
        return totalAlbuns;
    }

    public void setTotalAlbuns(Integer totalAlbuns) {
        this.totalAlbuns = totalAlbuns;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
