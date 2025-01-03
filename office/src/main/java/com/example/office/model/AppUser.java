package com.example.office.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    private String address;

    @Column(nullable = false)
    private String password;

    private String role;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDate birthday;

    // Relacionamento com a entidade Company (Muitos usuários podem pertencer a uma empresa)
    @ManyToOne
    @JoinColumn(name = "company_id")  // Este é o nome da coluna na tabela 'users' que vai armazenar a referência da empresa
    private Company company;  // A referência à empresa associada ao usuário

    // Construtor padrão
    public AppUser() {
        this.createdAt = LocalDateTime.now(); // Define a data de criação como agora por padrão
    }

    // Construtor completo (opcional)
    public AppUser(String firstName, String lastName, String email, String phone, String address, String password, String role, LocalDate birthday, Company company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now(); // Define a data de criação como agora
        this.birthday = birthday;
        this.company = company;  // Inicializa a empresa associada
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    // Novo método para acessar a empresa associada ao usuário
    public Company getCompany() {
        return company;
    }

    // Novo método para associar uma empresa ao usuário
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", birthday=" + birthday +
                ", company=" + company +  // Exibe a empresa associada
                '}';
    }
}
