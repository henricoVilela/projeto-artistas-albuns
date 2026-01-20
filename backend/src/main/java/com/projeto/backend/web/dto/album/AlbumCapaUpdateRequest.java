package com.projeto.backend.web.dto.album;

import com.projeto.backend.domain.album.TipoCapa;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para atualização de capa")
public class AlbumCapaUpdateRequest {

    @Schema(description = "Tipo da capa", example = "FRENTE")
    private TipoCapa tipoCapa;

    @Schema(description = "Ordem de exibição", example = "0")
    private Integer ordem;

    public AlbumCapaUpdateRequest() {
    }

    public AlbumCapaUpdateRequest(TipoCapa tipoCapa, Integer ordem) {
        this.tipoCapa = tipoCapa;
        this.ordem = ordem;
    }

    public TipoCapa getTipoCapa() {
        return tipoCapa;
    }

    public void setTipoCapa(TipoCapa tipoCapa) {
        this.tipoCapa = tipoCapa;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
}