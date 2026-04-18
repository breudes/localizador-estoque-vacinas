package com.hackathon.estoque.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    /**
     * Este método é chamado em cada requisição. Ele recupera o token de autenticação
     * do cabeçalho da requisição, valida o token e, se válido, autentica o usuário
     * associando-o ao contexto de segurança.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Recupera o token de autenticação do cabeçalho da requisição
        var token = this.recoverToken(request);

        if (token == null) {
            logger.debug("No Authorization token found on request to {}", request.getRequestURI());
        } else {
            logger.debug("Authorization token present (masked) for request to {}", request.getRequestURI());
        }

        // Valida o token e obtém o login (cpf) associado ao usuário
        var login = tokenService.validateToken(token);

        // Se o login não for nulo, o token é válido e o usuário existe
        if (login != null) {
            logger.debug("Token valid, subject (cpf): {}", login);
            // Procura o usuário no banco de dados pelo cpf retornado pelo token
            User user = userRepository.findByCpf(login)
                    .orElseThrow(() -> new RuntimeException("User Not Found"));

            // --- ADICIONE ESTAS LINHAS AQUI PARA DEBUG ---
            var autho = user.getAuthorities();
            System.out.println(">>> DEBUG SEGURANÇA <<<");
            System.out.println("Usuário logado: " + login);
            System.out.println("Authorities retornadas: " + autho);
            // ---

            // Usa as autoridades definidas no objeto User
            var authorities = user.getAuthorities();
            logger.debug("User authorities: {}", authorities);

            // Cria um objeto de autenticação com o usuário autenticado e suas permissões
            var userDetails = new CustomUserDetails(user);

            var authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Associa a autenticação ao contexto de segurança da aplicação
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println(">>> DEBUG SEGURANÇA <<<");
                System.out.println("Autenticação configurada para o usuário: " + login);
            }
        } else {
            System.out.println("Token invalid or not provided for request to " + request.getRequestURI());
        }

        // Passa a requisição para o próximo filtro da cadeia
        filterChain.doFilter(request, response);
    }

    /**
     * Este método recupera o token de autenticação do cabeçalho "Authorization"
     * da requisição HTTP. Se o cabeçalho estiver ausente ou vazio, retorna null.
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) {
            System.out.println("No Authorization header found in request to " + request.getRequestURI());
            return null;
        }
        // suportar 'Bearer ' case-insensitive e remover espaços extras
        if (authHeader.toLowerCase().startsWith("bearer ")) {
            System.out.println("Authorization header starts with 'Bearer ' (case-insensitive) for request to " + request.getRequestURI());
            return authHeader.substring(7).trim();
        }
        return authHeader.trim();
    }
}
