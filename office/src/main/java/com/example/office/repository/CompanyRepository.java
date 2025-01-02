package com.example.office.repository;

import com.example.office.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByEmail(String email);
}