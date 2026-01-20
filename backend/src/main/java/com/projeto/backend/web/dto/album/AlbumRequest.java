package com.projeto.backend.web.dto.album;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criação/atualização de álbum")
public class AlbumRequest {

    @Schema(
            description = "ID do artista",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "ID do artista é obrigatório")
    private Long artistaId;

    @Schema(
            description = "Nome do álbum",
            example = "Hybrid Theory",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 1, max = 300, message = "Nome deve ter entre 1 e 300 caracteres")
    private String nome;

    @Schema(
            description = "Ano de lançamento",
            example = "2000"
    )
    @Min(value = 1900, message = "Ano de lançamento deve ser maior que 1900")
    @Max(value = 2100, message = "Ano de lançamento inválido")
    private Integer anoLancamento;

    @Schema(
            description = "Nome da gravadora",
            example = "Warner Bros. Records"
    )
    @Size(max = 150, message = "Gravadora deve ter no máximo 150 caracteres")
    private String gravadora;

    @Schema(
            description = "Gênero musical",
            example = "Nu Metal"
    )
    @Size(max = 100, message = "Gênero deve ter no máximo 100 caracteres")
    private String genero;

    @Schema(
            description = "Número total de faixas",
            example = "12"
    )
    @Min(value = 1, message = "Total de faixas deve ser pelo menos 1")
    private Integer totalFaixas;

    @Schema(
            description = "Duração total em segundos",
            example = "2234"
    )
    @Min(value = 1, message = "Duração deve ser maior que 0")
    private Integer duracaoTotal;

    @Schema(
            description = "Descrição ou informações adicionais",
            example = "Álbum de estreia da banda que vendeu mais de 30 milhões de cópias mundialmente."
    )
    @Size(max = 5000, message = "Descrição deve ter no máximo 5000 caracteres")
    private String descricao;

    // Construtores
    public AlbumRequest() {
    }

    public AlbumRequest(Long artistaId, String nome) {
        this.artistaId = artistaId;
        this.nome = nome;
    }

    // Getters e Setters
    public Long getArtistaId() {
        return artistaId;
    }

    public void setArtistaId(Long artistaId) {
        this.artistaId = artistaId;
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
}

