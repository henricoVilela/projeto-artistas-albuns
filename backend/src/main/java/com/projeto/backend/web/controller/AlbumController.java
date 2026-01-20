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
import com.projeto.backend.web.dto.album.AlbumRequest;
import com.projeto.backend.web.dto.album.AlbumResponse;
import com.projeto.backend.web.openapi.AlbumControllerOpenApi;

import jakarta.validation.Valid;

/**
 * Controller para endpoints de álbuns.
 */
@RestController
@RequestMapping("/api/v1/albuns")
public class AlbumController implements AlbumControllerOpenApi {
	
	private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

	@Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<Page<AlbumResponse>> listar(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String artista,
        @RequestParam(required = false) String genero,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "nome") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        logger.info("GET /api/v1/albuns - nome: {}, artista: {}, genero: {}, page: {}, size: {}",
                nome, artista, genero, page, size);

        Page<AlbumResponse> albuns = albumService.listar(nome, artista, genero, page, size, sortBy, sortDir);
        return ResponseEntity.ok(albuns);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/albuns/{}", id);

        AlbumResponse album = albumService.buscarPorId(id);
        return ResponseEntity.ok(album);
    }

    @GetMapping("/generos")
    public ResponseEntity<List<String>> listarGeneros() {
        logger.info("GET /api/v1/albuns/generos");

        List<String> generos = albumService.listarGeneros();
        return ResponseEntity.ok(generos);
    }

    @PostMapping
    public ResponseEntity<AlbumResponse> criar(@Valid @RequestBody AlbumRequest request) {
        logger.info("POST /api/v1/albuns - nome: {}, artistaId: {}", request.getNome(), request.getArtistaId());

        AlbumResponse album = albumService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(album);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponse> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody AlbumRequest request
    ) {
        logger.info("PUT /api/v1/albuns/{}", id);

        AlbumResponse album = albumService.atualizar(id, request);
        return ResponseEntity.ok(album);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/albuns/{}", id);

        albumService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
