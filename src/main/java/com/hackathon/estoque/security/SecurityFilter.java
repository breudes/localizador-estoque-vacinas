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

@Component
public class SecurityFilter extends OncePerRequestFilter {

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

        // Valida o token e obtém o login (cpf) associado ao usuário
        var login = tokenService.validateToken(token);

        // Se o login não for nulo, o token é válido e o usuário existe
        if (login != null) {
            // Procura o usuário no banco de dados pelo cpf retornado pelo token
            User user = userRepository.findByCpf(login)
                    .orElseThrow(() -> new RuntimeException("User Not Found"));

            // Usa as autoridades definidas no objeto User
            var authorities = user.getAuthorities();

            // Cria um objeto de autenticação com o usuário autenticado e suas permissões
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

            // Associa a autenticação ao contexto de segurança da aplicação
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Passa a requisição para o próximo filtro da cadeia
        filterChain.doFilter(request, response);
    }

    /**
     * Este método recupera o token de autenticação do cabeçalho "Authorization"
     * da requisição HTTP. Se o cabeçalho estiver ausente ou vazio, retorna null.
     */
    private String recoverToken(HttpServletRequest request) {
        // Obtém o valor do cabeçalho "Authorization"
        var authHeader = request.getHeader("Authorization");

        // Retorna null se o cabeçalho não estiver presente
        if (authHeader == null)
            return null;

        // Remove o prefixo "Bearer " do token e retorna o token limpo
        return authHeader.replace("Bearer ", "");
    }
}

