package com.example.office.controller;

import com.example.office.model.Schedule;
import com.example.office.model.AppUser;
import com.example.office.service.ScheduleService;
import com.example.office.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private AppUserService appUserService;

    // Listar todos os agendamentos
    @GetMapping
    public String listSchedules(Model model, Principal principal) {
        AppUser currentUser = appUserService.findByEmail(principal.getName());
        List<Schedule> schedules = scheduleService.findByUserId(currentUser.getId());
        model.addAttribute("schedules", schedules);
        return "agenda"; // Renderiza a página agenda.html
    }

    // Exibir o formulário de criação de agendamento
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        return "schedules/create"; // Página com o formulário de criação
    }

    // Criar um novo agendamento
    @PostMapping
    public String createSchedule(@Validated @ModelAttribute Schedule schedule,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        AppUser currentUser = appUserService.findByEmail(principal.getName());
        schedule.setUser(currentUser);
        schedule.setCreatedAt(LocalDateTime.now());

        scheduleService.save(schedule);

        redirectAttributes.addFlashAttribute("message", "Agendamento criado com sucesso!");
        return "redirect:/schedules"; // Redireciona para a lista de agendamentos
    }

    // Exibir o formulário de edição de um agendamento específico
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Schedule> scheduleOpt = scheduleService.findById(id);
        if (scheduleOpt.isPresent()) {
            model.addAttribute("schedule", scheduleOpt.get());
            return "schedules/edit"; // Página com o formulário de edição
        } else {
            return "redirect:/schedules"; // Redireciona se o agendamento não for encontrado
        }
    }

    // Atualizar um agendamento
    @PostMapping("/update/{id}")
    public String updateSchedule(@PathVariable Long id,
                                 @Validated @ModelAttribute Schedule updatedSchedule,
                                 RedirectAttributes redirectAttributes) {
        Optional<Schedule> scheduleOpt = scheduleService.findById(id);
        if (scheduleOpt.isPresent()) {
            Schedule existingSchedule = scheduleOpt.get();
            existingSchedule.setTitle(updatedSchedule.getTitle());
            existingSchedule.setNote(updatedSchedule.getNote());
            existingSchedule.setDate(updatedSchedule.getDate());
            existingSchedule.setReminderTime(updatedSchedule.getReminderTime());

            scheduleService.save(existingSchedule);
            redirectAttributes.addFlashAttribute("message", "Agendamento atualizado com sucesso!");
        }
        return "redirect:/schedules"; // Redireciona para a lista de agendamentos
    }

    // Deletar um agendamento
    @PostMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        scheduleService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Agendamento excluído com sucesso!");
        return "redirect:/schedules"; // Redireciona para a lista de agendamentos
    }

    // Filtrar agendamentos por data (exemplo de filtro adicional)
    @GetMapping("/filter")
    public String filterSchedules(@RequestParam("date") LocalDateTime date, Model model, Principal principal) {
        AppUser currentUser = appUserService.findByEmail(principal.getName());
        List<Schedule> filteredSchedules = scheduleService.findByUserIdAndDate(currentUser.getId(), date);
        model.addAttribute("schedules", filteredSchedules);
        return "agenda"; // Mesma página de agenda, mas agora com os resultados filtrados
    }
}
