package com.projeto.backend.web.dto.artista;


import com.projeto.backend.domain.artista.TipoArtista;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criação/atualização de artista")
public class ArtistaRequest {

    @Schema(
            description = "Nome artístico",
            example = "Linkin Park",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 1, max = 200, message = "Nome deve ter entre 1 e 200 caracteres")
    private String nome;

    @Schema(
            description = "Tipo do artista",
            example = "BANDA",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Tipo é obrigatório")
    private TipoArtista tipo;

    @Schema(
            description = "País de origem",
            example = "Estados Unidos"
    )
    @Size(max = 100, message = "País de origem deve ter no máximo 100 caracteres")
    private String paisOrigem;

    @Schema(
            description = "Ano de formação ou início da carreira",
            example = "1996"
    )
    @Min(value = 1900, message = "Ano de formação deve ser maior que 1900")
    @Max(value = 2100, message = "Ano de formação inválido")
    private Integer anoFormacao;

    @Schema(
            description = "Biografia ou descrição do artista",
            example = "Linkin Park é uma banda de rock americana formada em Agoura Hills, Califórnia, em 1996."
    )
    @Size(max = 5000, message = "Biografia deve ter no máximo 5000 caracteres")
    private String biografia;

    // Construtores
    public ArtistaRequest() {
    }

    public ArtistaRequest(String nome, TipoArtista tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    // Getters e Setters
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
}
