package com.projeto.backend.web.openapi;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notificações", description = "Endpoints para gerenciamento de notificações WebSocket")
@SecurityRequirement(name = "bearerAuth")
public interface NotificationControllerOpenApi {
	
	@Operation(
            summary = "Status do WebSocket",
            description = "Retorna informações sobre como conectar ao WebSocket"
    )
    @ApiResponse(responseCode = "200", description = "Informações do WebSocket")
    public ResponseEntity<Map<String, Object>> getWebSocketInfo() ;
	
	@Operation(
            summary = "Enviar notificação de teste",
            description = "Envia uma notificação de sistema para todos os clientes conectados"
    )
    @ApiResponse(responseCode = "200", description = "Notificação enviada")
    public ResponseEntity<Map<String, String>> sendTestNotification(
            @RequestBody(required = false) Map<String, String> request
    );

}
