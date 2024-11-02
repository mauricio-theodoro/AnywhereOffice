package com.example.office.service;

import com.example.office.model.Company;
import com.example.office.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//Essa classe gerenciará as operações de empresa, incluindo o registro de novas empresas e o vínculo de usuários.
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company registerCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company findCompanyByCnpj(String cnpj) {
        return companyRepository.findByCnpj(cnpj);
    }
}
