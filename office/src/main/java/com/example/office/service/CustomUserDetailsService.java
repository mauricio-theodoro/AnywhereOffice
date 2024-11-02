package com.example.office.service;

import com.example.office.model.AppUser;
import com.example.office.model.Company;
import com.example.office.repository.AppUserRepository;
import com.example.office.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tenta encontrar a empresa pelo CNPJ
        Company company = companyRepository.findByCnpj(username);
        if (company != null) {
            // Definindo a role padrão para empresas como "ADMIN"
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // Define role para a empresa

            return new org.springframework.security.core.userdetails.User(
                    company.getCnpj(), // Nome de usuário
                    company.getPassword(), // Senha
                    authorities // Autoridades (roles)
            );
        }

        // Se não encontrar, tenta encontrar um usuário pelo email
        AppUser user = appUserRepository.findByEmail(username);
        if (user != null) {
            // Definindo a role do usuário
            List<GrantedAuthority> authorities = new ArrayList<>();

            // Verifica se o usuário tem a role de SUPERVISOR ou ADMIN
            if ("SUPERVISOR".equals(user.getRole())) {
                authorities.add(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
            } else if ("ADMIN".equals(user.getRole())) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                // Para outros papéis (caso existam), você pode optar por não adicionar nenhuma authority ou adicionar uma authority padrão
                authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT")); // ou qualquer outra role padrão que você tenha
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), // Nome de usuário
                    user.getPassword(), // Senha
                    authorities // Autoridades (roles)
            );
        }

        // Se não encontrar nenhum usuário ou empresa, lança exceção
        throw new UsernameNotFoundException("User or Company not found");
    }
}
