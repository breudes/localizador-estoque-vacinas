package com.hackathon.estoque.security;

import com.hackathon.estoque.model.User;
import com.hackathon.estoque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Este método carrega os detalhes do usuário com base no CPF fornecido.
     * Ele é usado pelo Spring Security para autenticação.
     *
     * @param username - O CPF fornecido para autenticação
     * @return UserDetails - Objeto contendo as informações do usuário para o
     *         processo de autenticação
     * @throws UsernameNotFoundException - Exceção lançada se o usuário não for
     *                                   encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Procura o usuário no banco de dados pelo cpf fornecido
        User user = this.userRepository.findByCpf(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new CustomUserDetails(user);
    }
}
