package com.projeto.backend.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

/**
 * Serviço responsável por operações com JWT (JSON Web Token).
 */
@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret:projeto-artistas-albuns-jwt-secret-key}")
    private String jwtSecret;

    @Value("${jwt.expiration:300000}") // 5 minutos em milissegundos
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration:86400000}") // 24 horas em milissegundos
    private long refreshExpiration;

    /**
     * Extrai o username do token JWT.
     *
     * @param token Token JWT
     * @return Username extraído do token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrai a data de expiração do token.
     *
     * @param token Token JWT
     * @return Data de expiração
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrai um claim específico do token.
     *
     * @param token Token JWT
     * @param claimsResolver Função para extrair o claim desejado
     * @return Valor do claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrai todos os claims do token.
     *
     * @param token Token JWT
     * @return Claims do token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Verifica se o token está expirado.
     *
     * @param token Token JWT
     * @return true se expirado
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Gera um token de acesso para o usuário.
     * Expira em 5 minutos conforme requisito.
     *
     * @param userDetails Detalhes do usuário
     * @return Token JWT de acesso
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        return createToken(claims, userDetails.getUsername(), jwtExpiration);
    }

    /**
     * Gera um token de acesso com claims adicionais.
     *
     * @param extraClaims Claims extras
     * @param userDetails Detalhes do usuário
     * @return Token JWT de acesso
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        extraClaims.put("type", "access");
        return createToken(extraClaims, userDetails.getUsername(), jwtExpiration);
    }

    /**
     * Gera um token de refresh para o usuário.
     * Expira em 24 horas para permitir renovação do token de acesso.
     *
     * @param userDetails Detalhes do usuário
     * @return Token JWT de refresh
     */
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return createToken(claims, userDetails.getUsername(), refreshExpiration);
    }

    /**
     * Cria um token JWT.
     *
     * @param claims Claims do token
     * @param subject Subject (username)
     * @param expiration Tempo de expiração em milissegundos
     * @return Token JWT
     */
    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Valida o token JWT.
     *
     * @param token Token JWT
     * @param userDetails Detalhes do usuário
     * @return true se válido
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            logger.warn("Token JWT expirado: {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Erro ao validar token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verifica se é um token de refresh válido.
     *
     * @param token Token JWT
     * @return true se for um token de refresh válido
     */
    public Boolean isRefreshToken(String token) {
        try {
            String type = extractClaim(token, claims -> claims.get("type", String.class));
            return "refresh".equals(type);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém a chave de assinatura a partir do secret.
     *
     * @return SecretKey para assinatura
     */
    private SecretKey getSigningKey() {
        // Garante que a chave tenha pelo menos 256 bits (32 caracteres)
        String paddedSecret = jwtSecret;
        while (paddedSecret.length() < 32) {
            paddedSecret += jwtSecret;
        }
        paddedSecret = paddedSecret.substring(0, Math.max(32, jwtSecret.length()));
        
        byte[] keyBytes = paddedSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Retorna o tempo de expiração do token de acesso em milissegundos.
     *
     * @return Tempo de expiração
     */
    public long getJwtExpiration() {
        return jwtExpiration;
    }

    /**
     * Retorna o tempo de expiração do token de refresh em milissegundos.
     *
     * @return Tempo de expiração do refresh
     */
    public long getRefreshExpiration() {
        return refreshExpiration;
    }
}
