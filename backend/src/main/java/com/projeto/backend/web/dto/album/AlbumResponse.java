package com.projeto.backend.web.dto.album;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.projeto.backend.domain.album.Album;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados do álbum")
public class AlbumResponse {

    @Schema(description = "ID do álbum", example = "1")
    private Long id;

    @Schema(description = "ID do artista", example = "1")
    private Long artistaId;

    @Schema(description = "Nome do artista", example = "Linkin Park")
    private String artistaNome;

    @Schema(description = "Nome do álbum", example = "Hybrid Theory")
    private String nome;

    @Schema(description = "Ano de lançamento", example = "2000")
    private Integer anoLancamento;

    @Schema(description = "Gravadora", example = "Warner Bros. Records")
    private String gravadora;

    @Schema(description = "Gênero musical", example = "Nu Metal")
    private String genero;

    @Schema(description = "Total de faixas", example = "12")
    private Integer totalFaixas;

    @Schema(description = "Duração total em segundos", example = "2234")
    private Integer duracaoTotal;

    @Schema(description = "Descrição do álbum")
    private String descricao;

    @Schema(description = "Quantidade de capas", example = "2")
    private Integer totalCapas;

    @Schema(description = "Lista de URLs das capas")
    private List<AlbumCapaResponse> capas = new ArrayList<>();

    @Schema(description = "Data de criação")
    private LocalDateTime createdAt;

    @Schema(description = "Data de atualização")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Ativo")
    private Boolean ativo;

    // Construtores
    public AlbumResponse() {
    }

    /**
     * Cria um AlbumResponse a partir da entidade.
     */
    public static AlbumResponse fromEntity(Album album) {
        AlbumResponse response = new AlbumResponse();
        response.setId(album.getId());
        response.setArtistaId(album.getArtista().getId());
        response.setArtistaNome(album.getArtista().getNome());
        response.setNome(album.getNome());
        response.setAnoLancamento(album.getAnoLancamento());
        response.setGravadora(album.getGravadora());
        response.setGenero(album.getGenero());
        response.setTotalFaixas(album.getTotalFaixas());
        response.setDuracaoTotal(album.getDuracaoTotal());
        response.setDescricao(album.getDescricao());
        response.setTotalCapas(album.getTotalCapas());
        response.setCreatedAt(album.getCreatedAt());
        response.setUpdatedAt(album.getUpdatedAt());
        response.setAtivo(album.getAtivo());
        return response;
    }

    /**
     * Cria um AlbumResponse simplificado (sem carregar artista).
     */
    public static AlbumResponse fromEntitySimple(Album album, String artistaNome) {
        AlbumResponse response = new AlbumResponse();
        response.setId(album.getId());
        response.setArtistaId(album.getArtista().getId());
        response.setArtistaNome(artistaNome);
        response.setNome(album.getNome());
        response.setAnoLancamento(album.getAnoLancamento());
        response.setGravadora(album.getGravadora());
        response.setGenero(album.getGenero());
        response.setTotalFaixas(album.getTotalFaixas());
        response.setDuracaoTotal(album.getDuracaoTotal());
        response.setDescricao(album.getDescricao());
        response.setCreatedAt(album.getCreatedAt());
        response.setUpdatedAt(album.getUpdatedAt());
        return response;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArtistaId() {
        return artistaId;
    }

    public void setArtistaId(Long artistaId) {
        this.artistaId = artistaId;
    }

    public String getArtistaNome() {
        return artistaNome;
    }

    public void setArtistaNome(String artistaNome) {
        this.artistaNome = artistaNome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getGravadora() {
        return gravadora;
    }

    public void setGravadora(String gravadora) {
        this.gravadora = gravadora;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getTotalFaixas() {
        return totalFaixas;
    }

    public void setTotalFaixas(Integer totalFaixas) {
        this.totalFaixas = totalFaixas;
    }

    public Integer getDuracaoTotal() {
        return duracaoTotal;
    }

    public void setDuracaoTotal(Integer duracaoTotal) {
        this.duracaoTotal = duracaoTotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTotalCapas() {
        return totalCapas;
    }

    public void setTotalCapas(Integer totalCapas) {
        this.totalCapas = totalCapas;
    }

    public List<AlbumCapaResponse> getCapas() {
        return capas;
    }

    public void setCapas(List<AlbumCapaResponse> capas) {
        this.capas = capas;
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
