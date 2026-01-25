package com.projeto.backend.web.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projeto.backend.infrastructure.websocket.NotificationService;
import com.projeto.backend.web.openapi.NotificationControllerOpenApi;

@Controller
@RequestMapping("/api/v1/notifications")
public class NotificationController implements NotificationControllerOpenApi {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
	private NotificationService notificationService;
	
	/**
     * Endpoint WebSocket para receber mensagens de ping.
     * Clientes podem enviar para /app/ping e receber resposta em /topic/pong.
     */
    @MessageMapping("/ping")
    @SendTo("/topic/pong")
    public Map<String, Object> handlePing(Map<String, Object> message) {
        logger.debug("Ping recebido: {}", message);
        return Map.of(
                "type", "PONG",
                "message", "Conexão WebSocket ativa",
                "timestamp", System.currentTimeMillis()
        );
    }

    @GetMapping("/websocket-info")
    public ResponseEntity<Map<String, Object>> getWebSocketInfo() {
        return ResponseEntity.ok(Map.of(
                "endpoint", "/ws",
                "sockJsEndpoint", "/ws",
                "topics", Map.of(
                        "artistas", "/topic/artistas",
                        "albuns", "/topic/albuns",
                        "sync", "/topic/sync",
                        "system", "/topic/system"
                ),
                "sendTo", "/app/ping",
                "instructions", "Conecte-se ao endpoint /ws usando STOMP sobre WebSocket ou SockJS"
        ));
    }
    
    @PostMapping("/test")
    public ResponseEntity<Map<String, String>> sendTestNotification(
        @RequestBody(required = false) Map<String, String> request
    ) {
        String message = request != null && request.containsKey("message") 
                ? request.get("message") 
                : "Notificação de teste";

        notificationService.notifySystem(message);

        return ResponseEntity.ok(Map.of(
                "status", "sent",
                "message", message
        ));
    }

}
