package com.example.office.config;

import com.example.office.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy // Evita problemas de inicialização em cenários onde o serviço é carregado mais tarde
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Configura a cadeia de filtros de segurança.
     * Define as permissões de acesso para cada URL e configurações de login/logout.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // Recursos estáticos (CSS, JS, imagens) liberados para todos
                        .requestMatchers("/assets/**", "/css/**", "/js/**", "/images/**").permitAll()

                        // URLs públicas acessíveis sem autenticação
                        .requestMatchers("/", "/contact", "/index", "/blog", "/post01","/post02","/post03","/post04","/post05","/post06","/post07", "/create", "/store/**", "/companyRegister", "/login", "/logout").permitAll()

                        // Páginas que exigem autenticação ou papéis específicos
                        .requestMatchers("/register").hasAnyRole("SUPERVISOR", "ADMIN") // Apenas SUPERVISOR ou ADMIN podem acessar
                        .requestMatchers("/arquivos").authenticated() // Exige autenticação
                        .requestMatchers("/profile").authenticated() // Exige autenticação
                        .requestMatchers("/agenda").authenticated() // Exige autenticação
                        .requestMatchers("/create").authenticated() // Exige autenticação
                        .requestMatchers("/tasks").hasAnyRole("SUPERVISOR", "ADMIN") // Apenas SUPERVISOR ou ADMIN podem acessar

                        // Qualquer outra requisição exige autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Configuração da página de login
                        .loginPage("/login") // Página personalizada de login
                        .defaultSuccessUrl("/", true) // Redirecionar para a página inicial após login bem-sucedido
                        .permitAll() // Permite acesso à página de login para todos
                )
                .logout(logout -> logout
                        // Configuração do logout
                        .logoutSuccessUrl("/") // Redirecionar para a página inicial após logout
                        .permitAll() // Permite acesso ao logout para todos
                )
                .build();
    }

    /**
     * Configura o encoder de senha para ser usado em autenticação.
     * @return Instância de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o gerenciador de autenticação com o serviço de usuários personalizado
     * e o encoder de senhas.
     * @param http Instância de HttpSecurity para obter o objeto AuthenticationManagerBuilder.
     * @return Gerenciador de autenticação configurado.
     * @throws Exception Caso ocorra erro na configuração.
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Configura o serviço de detalhes do usuário e o codificador de senha
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        // Retorna o gerenciador de autenticação configurado
        return authenticationManagerBuilder.build();
    }
}
