package com.projeto.backend.web.openapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.projeto.backend.web.dto.album.AlbumRequest;
import com.projeto.backend.web.dto.album.AlbumResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Álbuns", description = "Endpoints para gerenciamento de álbuns")
@SecurityRequirement(name = "bearerAuth")
public interface AlbumControllerOpenApi {
	
	/**
     * Lista álbuns com paginação e filtros.
     */
    @Operation(
            summary = "Listar álbuns",
            description = "Retorna uma lista paginada de álbuns com filtros por nome, artista e gênero"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de álbuns retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    public ResponseEntity<Page<AlbumResponse>> listar(
            @Parameter(description = "Filtrar por nome do álbum", required = false) String nome,
            @Parameter(description = "Filtrar por nome do artista", required = false) String artista,
            @Parameter(description = "Filtrar por gênero musical", required = false) String genero,
            @Parameter(description = "Número da página (começa em 0)") int page,
            @Parameter(description = "Quantidade de itens por página") int size,
            @Parameter(description = "Campo para ordenação (nome, anoLancamento, genero)") String sortBy,
            @Parameter(description = "Direção da ordenação (asc ou desc)") String sortDir
    );
    
    @Operation(
            summary = "Buscar álbum por ID",
            description = "Retorna os dados completos de um álbum"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Álbum encontrado",
                    content = @Content(schema = @Schema(implementation = AlbumResponse.class))),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> buscarPorId(@Parameter(description = "ID do álbum") Long id);
    
    @Operation(
            summary = "Listar gêneros",
            description = "Retorna a lista de gêneros musicais distintos dos álbuns cadastrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de gêneros retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    public ResponseEntity<List<String>> listarGeneros();
    
    @Operation(
            summary = "Criar álbum",
            description = "Cria um novo álbum associado a um artista"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Álbum criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AlbumResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Artista não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AlbumResponse> criar(@RequestBody AlbumRequest request);
    
    @Operation(
            summary = "Atualizar álbum",
            description = "Atualiza os dados de um álbum existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Álbum atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AlbumResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Álbum ou artista não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    public ResponseEntity<AlbumResponse> atualizar(
            @Parameter(description = "ID do álbum")
            @PathVariable Long id,
            @Valid @RequestBody AlbumRequest request
    );
    
    @Operation(
            summary = "Remover álbum",
            description = "Remove (inativa) um álbum do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Álbum removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    public ResponseEntity<Void> deletar(@Parameter(description = "ID do álbum") Long id);
	
}
