package com.example.office.dto;

import jakarta.validation.constraints.NotBlank;

public class CompanyRegisterDTO {
    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

    @NotBlank(message = "Nome da Empresa é obrigatório")
    private String name;

    private String address;
    private String country;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotBlank(message = "Confirme a Senha é obrigatória")
    private String confirmSenha;

    // Getters and Setters

    public @NotBlank(message = "CNPJ é obrigatório") String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotBlank(message = "CNPJ é obrigatório") String cnpj) {
        this.cnpj = cnpj;
    }

    public @NotBlank(message = "Nome da Empresa é obrigatório") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Nome da Empresa é obrigatório") String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public @NotBlank(message = "Senha é obrigatória") String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank(message = "Senha é obrigatória") String senha) {
        this.senha = senha;
    }

    public @NotBlank(message = "Confirme a Senha é obrigatória") String getConfirmSenha() {
        return confirmSenha;
    }

    public void setConfirmSenha(@NotBlank(message = "Confirme a Senha é obrigatória") String confirmSenha) {
        this.confirmSenha = confirmSenha;
    }
}
