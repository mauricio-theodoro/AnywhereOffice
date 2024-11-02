package com.example.office.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt; // Atributo para armazenar a data de criação

    private String title;
    private String note;
    private LocalDateTime date;
    private LocalDateTime reminderTime;

    // Relacionamento com AppUser (representa o usuário associado à tarefa)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    // Relacionamento com Company (representa a empresa associada à tarefa)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // Construtor padrão
    public Schedule() {
        this.createdAt = LocalDateTime.now(); // Inicializa createdAt com a data e hora atuais
    }

    // Construtor com argumentos
    public Schedule(String title, String note, LocalDateTime date, LocalDateTime reminderTime, AppUser user, Company company) {
        this.createdAt = LocalDateTime.now(); // Inicializa createdAt com a data e hora atuais
        this.title = title;
        this.note = note;
        this.date = date;
        this.reminderTime = reminderTime;
        this.user = user;
        this.company = company;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", date=" + date +
                ", reminderTime=" + reminderTime +
                ", user=" + user +
                ", company=" + company +
                '}';
    }
}
