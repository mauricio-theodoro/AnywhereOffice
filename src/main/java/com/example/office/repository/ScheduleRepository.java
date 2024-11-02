package com.example.office.repository;

import com.example.office.model.Schedule;
import com.example.office.model.Company;
import com.example.office.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Filtra as tarefas de um usuário por uma data específica
    List<Schedule> findByUserAndDate(AppUser user, LocalDateTime date);

    // 1. Busca todas as tarefas de uma empresa específica
    List<Schedule> findByCompany(Company company);

    // 2. Busca todas as tarefas para um usuário específico (AppUser)
    List<Schedule> findByUser(AppUser user);


    List<Schedule> findByUserId(Long userId);

    List<Schedule> findByUserIdAndDate(Long userId, LocalDateTime date);

    // 3. Busca todas as tarefas em um intervalo de datas específico
    List<Schedule> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 4. Busca todas as tarefas de uma empresa em um intervalo de datas
    List<Schedule> findByCompanyAndDateBetween(Company company, LocalDateTime startDate, LocalDateTime endDate);

    // 5. Busca todas as tarefas de um usuário (AppUser) em um intervalo de datas
    List<Schedule> findByUserAndDateBetween(AppUser user, LocalDateTime startDate, LocalDateTime endDate);

    // 6. Busca todas as tarefas futuras a partir de uma data específica
    List<Schedule> findByDateAfter(LocalDateTime date);

    // 7. Custom Query: Conta as tarefas de uma empresa em um intervalo de datas
    @Query("SELECT COUNT(s) FROM Schedule s WHERE s.company = :company AND s.date BETWEEN :startDate AND :endDate")
    Long countSchedulesByCompanyAndDateRange(@Param("company") Company company,
                                             @Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    // 8. Busca todas as tarefas com uma palavra-chave na anotação (note), ignorando maiúsculas/minúsculas
    List<Schedule> findByNoteContainingIgnoreCase(String keyword);

    // 9. Custom Query: Lista todas as tarefas criadas antes de uma data específica
    @Query("SELECT s FROM Schedule s WHERE s.createdAt < :date")
    List<Schedule> findSchedulesCreatedBefore(@Param("date") LocalDateTime date);

    // 10. Lista todas as tarefas de um usuário (AppUser), ordenadas pela data
    List<Schedule> findByUserOrderByDateAsc(AppUser user);

    List<Schedule> findByUserIdAndDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
