package com.projeto.backend.domain.album;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumCapaRepository extends JpaRepository<AlbumCapa, Long> {
	/**
     * Conta capas de um álbum.
     *
     * @param albumId ID do álbum
     * @return Quantidade de capas
     */
    long countByAlbumId(Long albumId);
    
    /**
     * Busca capas de um álbum ordenadas por ordem.
     *
     * @param albumId ID do álbum
     * @return Lista de capas
     */
    List<AlbumCapa> findByAlbumIdOrderByOrdemAsc(Long albumId);
}
