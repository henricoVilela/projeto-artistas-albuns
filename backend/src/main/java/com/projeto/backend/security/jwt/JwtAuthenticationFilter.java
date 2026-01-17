package com.projeto.backend.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de autenticação JWT.
 * 
 * Intercepta todas as requisições e valida o token JWT presente
 * no header Authorization. Se válido, configura o contexto de
 * segurança do Spring Security.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
	    @NonNull HttpServletRequest request,
	    @NonNull HttpServletResponse response,
	    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        
        // Verifica se o header Authorization está presente e começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extrai o token do header
            final String jwt = authHeader.substring(BEARER_PREFIX.length());
            final String username = jwtService.extractUsername(jwt);

            // Se o username foi extraído e não há autenticação no contexto
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Carrega os detalhes do usuário
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                
                if (jwtService.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    
                    // Define os detalhes da requisição
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Define a autenticação no contexto do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    
                    logger.debug("Usuário autenticado: {}", username);
                }
            }
        } catch (Exception e) {
            logger.error("Erro ao processar autenticação JWT: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}

