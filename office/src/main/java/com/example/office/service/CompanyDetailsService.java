package com.example.office.service;

import com.example.office.model.Company;
import com.example.office.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CompanyDetailsService implements UserDetailsService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Company company = companyRepository.findByEmail(email);
        if (company == null) {
            throw new UsernameNotFoundException("Company not found");
        }
        return new org.springframework.security.core.userdetails.User(company.getCnpj(), company.getPassword(), new ArrayList<>()); // Retorna os detalhes do usuário
    }
}
