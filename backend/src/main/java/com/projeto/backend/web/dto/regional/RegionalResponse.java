package com.projeto.backend.web.dto.regional;

import java.time.LocalDateTime;

import com.projeto.backend.domain.regional.Regional;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados da regional")
public class RegionalResponse {

    @Schema(description = "ID da regional", example = "1")
    private Integer id;

    @Schema(description = "Nome da regional", example = "Regional Centro")
    private String nome;

    @Schema(description = "ID externo (da API)", example = "101")
    private Long externalId;

    @Schema(description = "Indica se está ativa", example = "true")
    private Boolean ativo;

    @Schema(description = "Data de criação")
    private LocalDateTime createdAt;

    @Schema(description = "Data de atualização")
    private LocalDateTime updatedAt;

    public RegionalResponse() {
    }

    public static RegionalResponse fromEntity(Regional regional) {
        RegionalResponse response = new RegionalResponse();
        response.setId(regional.getId());
        response.setNome(regional.getNome());
        response.setExternalId(regional.getExternalId());
        response.setAtivo(regional.getAtivo());
        response.setCreatedAt(regional.getCreatedAt());
        response.setUpdatedAt(regional.getUpdatedAt());
        return response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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
}
