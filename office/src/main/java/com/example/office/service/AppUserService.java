package com.example.office.service;

import com.example.office.model.AppUser;
import com.example.office.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository repo;

    @Override
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException{
        AppUser appUser = repo.findByEmail(email);

        if(appUser !=null){
            var springUser = User.withUsername(appUser.getEmail())
                    .password(appUser.getPassword())
                    .roles(appUser.getRole())
                    .build();

            return springUser;
        }
        return null;
    }

    public Long getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            AppUser appUser = repo.findByEmail(email);  // Busca o usuário pelo email
            return appUser != null ? appUser.getId() : null;  // Retorna o ID do usuário
        }

        throw new RuntimeException("Usuário não autenticado");
    }

    public AppUser findByEmail(String email) {
        return repo.findByEmail(email);
    }
}
