package com.projeto.backend.domain.album;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
	
	/**
     * Busca álbuns ativos de um artista específico (lista).
     *
     * @param artistaId ID do artista
     * @return Lista de álbuns
     */
    List<Album> findByArtistaIdAndAtivoTrueOrderByAnoLancamentoDesc(Long artistaId);
}