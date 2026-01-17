package com.projeto.backend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.backend.domain.usuario.Usuario;
import com.projeto.backend.domain.usuario.UsuarioRepository;
import com.projeto.backend.security.jwt.JwtService;
import com.projeto.backend.web.dto.auth.AuthRequest;
import com.projeto.backend.web.dto.auth.AuthResponse;
import com.projeto.backend.web.dto.auth.RegisterRequest;

/**
 * Serviço de autenticação.
 */
@Service
public class AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	/**
     * Realiza o login do usuário.
     *
     * @param request Dados de login (username e password)
     * @return Resposta com tokens JWT
     * @throws BadCredentialsException Se as credenciais forem inválidas
     */
    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest request) {
   
        logger.info("Tentativa de login para usuário: {}", request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
        	.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String accessToken = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);

        logger.info("Login realizado com sucesso para usuário: {}", request.getUsername());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresIn(jwtService.getJwtExpiration() / 1000) // em segundos
            .username(usuario.getUsername())
            .email(usuario.getEmail())
            .build();
    }
    
    /**
     * Registra um novo usuário.
     *
     * @param request Dados de registro
     * @return Resposta com tokens JWT
     * @throws IllegalArgumentException Se username ou email já existirem
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        logger.info("Tentativa de registro para usuário: {}", request.getUsername());

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username já está em uso");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("E-mail já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setNomeCompleto(request.getNomeCompleto());
        usuario.setAtivo(true);

        usuario = usuarioRepository.save(usuario);

        String accessToken = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);

        logger.info("Registro realizado com sucesso para usuário: {}", request.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getJwtExpiration() / 1000)
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .build();
    }
}
