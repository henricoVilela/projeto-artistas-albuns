package com.projeto.backend.web.openapi;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.projeto.backend.web.dto.regional.RegionalResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Regionais", description = "Endpoints para gerenciamento de regionais e sincronização com API externa")
@SecurityRequirement(name = "bearerAuth")
public interface RegionalControllerOpenApi {
	
	@Operation(
            summary = "Listar regionais",
            description = "Retorna uma lista paginada de regionais com filtro por nome"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de regionais retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    public ResponseEntity<Page<RegionalResponse>> listar(
            @Parameter(description = "Filtrar por nome", required = false) String nome,
            @Parameter(description = "Número da página (começa em 0)") int page,
            @Parameter(description = "Quantidade de itens por página") int size,
            @Parameter(description = "Campo para ordenação") String sortBy,
            @Parameter(description = "Direção da ordenação (asc ou desc)") String sortDir
    );
	
	@Operation(
            summary = "Sincronizar regionais",
            description = "Executa sincronização manual com a API externa de regionais"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização concluída",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "Sincronização já em andamento", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    public ResponseEntity<Map<String, Object>> sincronizar() ;

}
