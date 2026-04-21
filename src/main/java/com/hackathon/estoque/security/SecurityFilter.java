package com.hackathon.estoque.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.hackathon.estoque.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
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
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);

        if (token != null) {
            var subject = tokenService.validateToken(token);

            // Definindo uma variável de ambiente para o Spring, que pode ser acessada
            // em outros pontos da aplicação.
            request.setAttribute("subjectFromToken", subject);

            System.out.println("Subject extraído do Token: [" + subject + "]");

            if (subject != null) {
                var userOptional = userRepository.findByCpf(subject);

                if (userOptional.isPresent()) {
                    userRepository.findByCpf(subject).ifPresent(user -> {
                        // O primeiro parâmetro aqui é o que o @AuthenticationPrincipal vai capturar
                        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Este método recupera o token de autenticação do cabeçalho "Authorization"
     * da requisição HTTP. Se o cabeçalho estiver ausente ou vazio, retorna null.
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authHeader.substring(7).trim();

        if (token.startsWith("\"") && token.endsWith("\"")) {
            token = token.substring(1, token.length() - 1);
        }

        return token;
    }
}
