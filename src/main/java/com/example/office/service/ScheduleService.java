package com.example.office.service;

import com.example.office.model.Schedule;
import com.example.office.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // Método para criar ou atualizar um agendamento
    public Schedule save(Schedule schedule) {
        // Aqui você pode adicionar lógica adicional, como validações, se necessário
        return scheduleRepository.save(schedule);
    }

    // Método para listar todos os agendamentos
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Método para obter agendamento por ID
    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }

    // Método para deletar um agendamento pelo ID
    public void deleteById(Long id) {
        // Verificação se o agendamento existe antes de tentar deletar
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Agendamento não encontrado com o ID: " + id);
        }
    }

    // Método para obter agendamentos de um usuário específico
    public List<Schedule> findByUserId(Long userId) {
        return scheduleRepository.findByUserId(userId);
    }

    // Método para filtrar agendamentos por data e usuário
    public List<Schedule> findByUserIdAndDate(Long userId, LocalDateTime date) {
        return scheduleRepository.findByUserIdAndDate(userId, date);
    }

    // Método para buscar agendamentos em um intervalo de datas
    public List<Schedule> findByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    // Método para buscar agendamentos próximos (dentro de uma hora, por exemplo)
    public List<Schedule> findUpcomingSchedules(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourLater = now.plusHours(1);
        return scheduleRepository.findByUserIdAndDateBetween(userId, now, oneHourLater);
    }

}
