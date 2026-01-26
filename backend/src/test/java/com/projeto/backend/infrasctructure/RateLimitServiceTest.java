package com.projeto.backend.infrasctructure;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.projeto.backend.infrastructure.ratelimit.RateLimitService;

/**
 * Testes unitários para RateLimitService.
 * 
 * Cobertura:
 * - Consumo de tokens
 * - Limite de requisições
 * - Reset de buckets
 * - Informações de rate limit
 */
@DisplayName("RateLimitService")
class RateLimitServiceTest {

    private RateLimitService rateLimitService;

    @BeforeEach
    void setUp() {
        rateLimitService = new RateLimitService();
    }

    @Nested
    @DisplayName("Consumo de Tokens")
    class ConsumoTokens {

        @Test
        @DisplayName("Deve permitir primeira requisição")
        void devePermitirPrimeiraRequisicao() {
            // Act
            boolean permitido = rateLimitService.tryConsume("user:test1");

            // Assert
            assertThat(permitido).isTrue();
        }

        @Test
        @DisplayName("Deve permitir múltiplas requisições dentro do limite")
        void devePermitirMultiplasRequisicoesNorte() {
            // Act & Assert
            for (int i = 0; i < 10; i++) {
                boolean permitido = rateLimitService.tryConsume("user:test2");
                assertThat(permitido)
                        .as("Requisição %d deveria ser permitida", i + 1)
                        .isTrue();
            }
        }

        @Test
        @DisplayName("Deve bloquear após exceder limite")
        void deveBloquearAposExcederLimite() {
            // Arrange - consumir todos os 10 tokens
            for (int i = 0; i < 10; i++) {
                rateLimitService.tryConsume("user:test3");
            }

            // Act - 11ª requisição
            boolean permitido = rateLimitService.tryConsume("user:test3");

            // Assert
            assertThat(permitido).isFalse();
        }

        @Test
        @DisplayName("Buckets devem ser independentes por usuário")
        void bucketsDevemSerIndependentesPorUsuario() {
            // Arrange - esgotar tokens do user1
            for (int i = 0; i < 10; i++) {
                rateLimitService.tryConsume("user:user1");
            }

            // Act - user2 ainda deve ter tokens
            boolean permitidoUser2 = rateLimitService.tryConsume("user:user2");

            // Assert
            assertThat(permitidoUser2).isTrue();
        }
    }

    @Nested
    @DisplayName("Informações de Rate Limit")
    class InformacoesRateLimit {

        @Test
        @DisplayName("Deve retornar informações corretas para novo bucket")
        void deveRetornarInformacoesCorretasParaNovoBucket() {
            // Act
            RateLimitService.RateLimitInfo info = rateLimitService.getRateLimitInfo("user:novo");

            // Assert
            assertThat(info.limit()).isEqualTo(10);
            assertThat(info.remaining()).isEqualTo(10);
            assertThat(info.resetInSeconds()).isEqualTo(60);
        }

        @Test
        @DisplayName("Deve atualizar remaining após consumo")
        void deveAtualizarRemainingAposConsumo() {
            // Arrange
            String identifier = "user:test4";
            rateLimitService.tryConsume(identifier);
            rateLimitService.tryConsume(identifier);
            rateLimitService.tryConsume(identifier);

            // Act
            RateLimitService.RateLimitInfo info = rateLimitService.getRateLimitInfo(identifier);

            // Assert
            assertThat(info.remaining()).isEqualTo(7); // 10 - 3 = 7
        }

        @Test
        @DisplayName("Remaining deve ser zero quando limite excedido")
        void remainingDeveSerZeroQuandoLimiteExcedido() {
            // Arrange - consumir todos os tokens
            String identifier = "user:test5";
            for (int i = 0; i < 10; i++) {
                rateLimitService.tryConsume(identifier);
            }

            // Act
            RateLimitService.RateLimitInfo info = rateLimitService.getRateLimitInfo(identifier);

            // Assert
            assertThat(info.remaining()).isZero();
        }
    }

    @Nested
    @DisplayName("Gerenciamento de Buckets")
    class GerenciamentoBuckets {

        @Test
        @DisplayName("Deve remover bucket específico")
        void deveRemoverBucketEspecifico() {
            // Arrange
            String identifier = "user:test6";
            for (int i = 0; i < 10; i++) {
                rateLimitService.tryConsume(identifier);
            }
            assertThat(rateLimitService.tryConsume(identifier)).isFalse();

            // Act - remover bucket
            rateLimitService.removeBucket(identifier);

            // Assert - novo bucket com todos os tokens
            boolean permitido = rateLimitService.tryConsume(identifier);
            assertThat(permitido).isTrue();
            
            RateLimitService.RateLimitInfo info = rateLimitService.getRateLimitInfo(identifier);
            assertThat(info.remaining()).isEqualTo(9); // Apenas 1 consumido
        }

        @Test
        @DisplayName("Deve limpar todos os buckets")
        void deveLimparTodosOsBuckets() {
            // Arrange
            for (int i = 0; i < 10; i++) {
                rateLimitService.tryConsume("user:a");
                rateLimitService.tryConsume("user:b");
            }
            assertThat(rateLimitService.tryConsume("user:a")).isFalse();
            assertThat(rateLimitService.tryConsume("user:b")).isFalse();

            // Act
            rateLimitService.clearAllBuckets();

            // Assert - ambos devem ter novos buckets
            assertThat(rateLimitService.tryConsume("user:a")).isTrue();
            assertThat(rateLimitService.tryConsume("user:b")).isTrue();
        }
    }

    @Nested
    @DisplayName("Diferentes Identificadores")
    class DiferentesIdentificadores {

        @Test
        @DisplayName("Deve aceitar identificador por usuário")
        void deveAceitarIdentificadorPorUsuario() {
            // Act
            boolean permitido = rateLimitService.tryConsume("user:admin");

            // Assert
            assertThat(permitido).isTrue();
        }

        @Test
        @DisplayName("Deve aceitar identificador por IP")
        void deveAceitarIdentificadorPorIP() {
            // Act
            boolean permitido = rateLimitService.tryConsume("ip:192.168.1.1");

            // Assert
            assertThat(permitido).isTrue();
        }

        @Test
        @DisplayName("Deve tratar IPs diferentes separadamente")
        void deveTratarIPsDiferentesSeparadamente() {
            // Arrange - esgotar limite de um IP
            for (int i = 0; i < 10; i++) {
                rateLimitService.tryConsume("ip:10.0.0.1");
            }

            // Act
            boolean permitidoIP1 = rateLimitService.tryConsume("ip:10.0.0.1");
            boolean permitidoIP2 = rateLimitService.tryConsume("ip:10.0.0.2");

            // Assert
            assertThat(permitidoIP1).isFalse();
            assertThat(permitidoIP2).isTrue();
        }
    }
}
