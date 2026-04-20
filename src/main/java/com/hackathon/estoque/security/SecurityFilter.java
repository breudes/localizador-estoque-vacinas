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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);

        if (token != null) {
            var subject = tokenService.validateToken(token);

            System.out.println("Subject extraído do Token: [" + subject + "]");

            // CORREÇÃO: Verifique se o subject não é nulo antes de buscar no repositório
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
        // Sempre continue a corrente, mesmo que não haja token ou o token seja inválido
        filterChain.doFilter(request, response);
    }

    /**
     * Este método recupera o token de autenticação do cabeçalho "Authorization"
     * da requisição HTTP. Se o cabeçalho estiver ausente ou vazio, retorna null.
     */
//    private String recoverToken(HttpServletRequest request) {
//        var authHeader = request.getHeader("Authorization");
//        if (authHeader == null || authHeader.isBlank()) {
//            System.out.println("No Authorization header found in request to " + request.getRequestURI());
//            return null;
//        }
//        return authHeader.replace("Bearer ", "");
//    }
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        // Verifica se o header existe e se começa com o prefixo correto
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        // substring(7) remove exatamente "Bearer " (6 letras + 1 espaço)
        return authHeader.substring(7).trim();
    }
}
