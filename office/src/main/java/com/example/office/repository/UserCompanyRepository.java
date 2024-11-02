package com.example.office.repository;

import com.example.office.model.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCompanyRepository extends JpaRepository<UserCompany, Long> {
}
