package com.projeto.backend.domain.album;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.backend.domain.artista.ArtistaService;
import com.projeto.backend.web.dto.album.AlbumResponse;

/**
 * Serviço para operações de negócio relacionadas a álbuns.
 */
@Service
public class AlbumService {

    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    @Autowired
    private AlbumRepository albumRepository;
    
    @Autowired
    private ArtistaService artistaService;
    
    /**
     * Lista todos os álbuns de um artista (sem paginação).
     *
     * @param artistaId ID do artista
     * @return Lista de álbuns
     */
    @Transactional(readOnly = true)
    public List<AlbumResponse> listarTodosPorArtista(Long artistaId) {
        logger.info("Listando todos os álbuns do artista ID: {}", artistaId);

        artistaService.buscarEntidadePorId(artistaId);

        List<Album> albuns = albumRepository.findByArtistaIdAndAtivoTrueOrderByAnoLancamentoDesc(artistaId);

        return albuns.stream()
                .map(AlbumResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
