package com.example.office.controller;

import com.example.office.dto.CompanyRegisterDTO;
import com.example.office.model.Company;
import com.example.office.repository.CompanyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepo;

    // Método que trata o acesso à página de registro
    @GetMapping("/companyRegister")
    public String register(Model model) {
        CompanyRegisterDTO registerDTO = new CompanyRegisterDTO();
        model.addAttribute("company", registerDTO); // Adiciona o objeto DTO ao modelo
        model.addAttribute("success", false);
        return "companyRegister"; // Retorna o nome da página HTML
    }

    @PostMapping("/companyRegister")
    public String register(Model model, @Valid @ModelAttribute CompanyRegisterDTO registerDTO, BindingResult result) {
        if (!registerDTO.getSenha().equals(registerDTO.getConfirmSenha())) {
            result.addError(new FieldError("companyRegisterDTO", "confirmSenha", "Senhas não conferem"));
        }

        Company existingCompany = companyRepo.findByCnpj(registerDTO.getCnpj());
        if (existingCompany != null) {
            result.addError(new FieldError("companyRegisterDTO", "cnpj", "CNPJ já está sendo usado"));
        }

        if (result.hasErrors()) {
            return "companyRegister"; // Retorna à página se houver erros
        }

        try {
            BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

            Company newCompany = new Company();
            newCompany.setCnpj(registerDTO.getCnpj());
            newCompany.setName(registerDTO.getName());
            newCompany.setAddress(registerDTO.getAddress());
            newCompany.setCountry(registerDTO.getCountry());
            newCompany.setPassword(bCryptEncoder.encode(registerDTO.getSenha())); // Criptografa a senha
            newCompany.setCreatedAt(LocalDateTime.now());

            companyRepo.save(newCompany);

            model.addAttribute("companyRegisterDTO", new CompanyRegisterDTO());
            model.addAttribute("success", true);

        } catch (Exception ex) {
            result.addError(new FieldError("companyRegisterDTO", "name", ex.getMessage()));
        }

        return "companyRegister";
    }
}
