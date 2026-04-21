package com.hackathon.estoque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    /**
     * Configura o filtro de segurança principal da aplicação.
     * Define as regras de acesso, gerenciamento de sessão e filtros de autenticação.
     *
     * @param http - Objeto HttpSecurity para configuração das permissões e segurança
     * @return SecurityFilterChain - Cadeia de filtros de segurança configurada
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // ENDPOINTS PÚBLICOS - criação de usuários
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin").permitAll()
                        .requestMatchers("/auth/update-password/**").authenticated()

                        // ADMIN
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")

                        // USUÁRIO (CUSTOMER)
                        .requestMatchers(HttpMethod.GET, "/users/me").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/address").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/address").hasAnyRole("USER", "ADMIN")

                        // ESTABELECIMENTO (HEALTH FACILITY)
                        .requestMatchers(HttpMethod.POST, "/api/health-facilities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/health-facilities/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/health-facilities/delete/{cnes}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/health-facilities/**").hasAnyRole("USER", "ADMIN")

                        // INVENTÁRIO DE VACINAS (INVENTORY)
                        .requestMatchers(HttpMethod.POST, "/api/health-facilities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/health-facilities/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/health-facilities/delete/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/health-facilities/**").hasAnyRole("USER", "ADMIN")

                        // VACINAS (VACCINE)
                        .requestMatchers(HttpMethod.POST, "/api/vaccines").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/vaccines/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/vaccines/delete/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/vaccines/**").hasAnyRole("USER", "ADMIN")

                        // QUALQUER OUTRO REQUER AUTENTICAÇÃO
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Define o encoder de senha, utilizando o BCrypt para criptografar as senhas dos usuários.
     *
     * @return PasswordEncoder - Instância do encoder BCrypt para senhas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o AuthenticationManager para gerenciar o processo de autenticação.
     *
     * @param authenticationConfiguration - Configuração de autenticação do Spring Security
     * @return AuthenticationManager - Instância do gerenciador de autenticação
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

