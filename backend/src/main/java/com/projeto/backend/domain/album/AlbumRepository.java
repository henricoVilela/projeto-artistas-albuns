package com.projeto.backend.domain.album;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album>{
	
	/**
     * Busca álbuns ativos de um artista específico (lista).
     *
     * @param artistaId ID do artista
     * @return Lista de álbuns
     */
    List<Album> findByArtistaIdAndAtivoTrueOrderByAnoLancamentoDesc(Long artistaId);
    
    /**
     * Busca álbum ativo por ID.
     *
     * @param id ID do álbum
     * @return Optional com o álbum
     */
    Optional<Album> findByIdAndAtivoTrue(Long id);
    
    /**
     * Lista gêneros distintos dos álbuns ativos.
     *
     * @return Lista de gêneros
     */
    @Query("SELECT DISTINCT al.genero FROM Album al WHERE al.ativo = true AND al.genero IS NOT NULL ORDER BY al.genero")
    List<String> findDistinctGeneros();
}