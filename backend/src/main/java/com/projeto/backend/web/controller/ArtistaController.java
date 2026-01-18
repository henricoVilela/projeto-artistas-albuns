package com.projeto.backend.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.backend.domain.album.AlbumService;
import com.projeto.backend.domain.artista.ArtistaService;
import com.projeto.backend.web.dto.album.AlbumResponse;
import com.projeto.backend.web.dto.artista.ArtistaRequest;
import com.projeto.backend.web.dto.artista.ArtistaResponse;
import com.projeto.backend.web.openapi.ArtistaControllerOpenApi;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/artistas")
public class ArtistaController implements ArtistaControllerOpenApi { 
	
	private static final Logger logger = LoggerFactory.getLogger(ArtistaController.class);
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private AlbumService albumService;
	
    @GetMapping
    public ResponseEntity<Page<ArtistaResponse>> listar(
        @RequestParam(required = false) String nome,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "nome") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        logger.info("GET /api/v1/artistas - nome: {}, page: {}, size: {}", nome, page, size);

        Page<ArtistaResponse> artistas = artistaService.listar(nome, page, size, sortBy, sortDir);
        return ResponseEntity.ok(artistas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ArtistaResponse> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/artistas/{}", id);

        ArtistaResponse artista = artistaService.buscarPorId(id);
        return ResponseEntity.ok(artista);
    }
    
    @PostMapping
    public ResponseEntity<ArtistaResponse> criar(@Valid @RequestBody ArtistaRequest request) {
        logger.info("POST /api/v1/artistas - nome: {}", request.getNome());

        ArtistaResponse artista = artistaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(artista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistaResponse> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody ArtistaRequest request
    ) {
        logger.info("PUT /api/v1/artistas/{}", id);

        ArtistaResponse artista = artistaService.atualizar(id, request);
        return ResponseEntity.ok(artista);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/artistas/{}", id);

        artistaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Lista álbuns de um artista.
     */
    @GetMapping("/{id}/albuns")
    public ResponseEntity<List<AlbumResponse>> listarAlbuns(@PathVariable Long id) {
        logger.info("GET /api/v1/artistas/{}/albuns", id);

        List<AlbumResponse> albuns = albumService.listarTodosPorArtista(id);
        return ResponseEntity.ok(albuns);
    }
}
