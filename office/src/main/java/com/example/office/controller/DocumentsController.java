package com.example.office.controller;

import com.example.office.entity.Documents;
import com.example.office.model.AppUser;
import com.example.office.repository.DocumentsRepository;
import com.example.office.service.AppUserService;
import com.example.office.service.DocumentsService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class DocumentsController {

    @Autowired
    private DocumentsService documentsService;

    @Autowired
    private DocumentsRepository repo;

    @Autowired
    private AppUserService appUserService; // Adicione este campo

    @GetMapping("/arquivos")
    public String arquivos(Model model) {
        List<Documents> listDocs = repo.findAll();
        model.addAttribute("listDocs", listDocs);

        return "arquivos"; // Nome do arquivo HTML sem a extensão
    }

    // Método para excluir um arquivo
    @GetMapping("/excluir")
    public String excluirDocumento(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            documentsService.deleteDocument(id);
            redirectAttributes.addFlashAttribute("Message", "Arquivo excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("Error", "Erro ao excluir o arquivo: " + e.getMessage());
        }
        return "redirect:/arquivos";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("documents") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) {
        Long userId = getUserId();
        if (userId == null) {
            redirectAttributes.addFlashAttribute("Error", "Erro: Usuário não autenticado.");
            return "redirect:/arquivos";
        }
        try {
            Documents savedDocument = documentsService.saveDocument(
                    multipartFile.getBytes(),
                    multipartFile.getOriginalFilename(),
                    multipartFile.getSize(),
                    userId
            );
            redirectAttributes.addFlashAttribute("Message", "Arquivo " + savedDocument.getName() + " carregado com sucesso!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("Error", "Erro ao carregar o arquivo: " + e.getMessage());
        }

        return "redirect:/arquivos";
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id") Long id, HttpServletResponse response) throws Exception {
        Optional<Documents> result = repo.findById(id);
        if (!result.isPresent()) {
            throw new Exception("Documento não foi encontrado com o ID:" + id);
        }

        Documents documents = result.get();

        response.setContentType("Application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + documents.getName();

        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(documents.getContent());
        outputStream.close();
    }

    private Long getUserId() {
        return appUserService.getUserId(); // Chama o método do AppUserService
    }
}
