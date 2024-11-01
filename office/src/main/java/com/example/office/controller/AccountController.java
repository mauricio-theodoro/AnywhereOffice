package com.example.office.controller;

import com.example.office.dto.RegisterDTO;
import com.example.office.model.AppUser;
import com.example.office.repository.AppUserRepository;
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

import javax.naming.Binding;
import java.io.Serial;
import java.util.Date;

@Controller
public class AccountController {

    @Autowired
    private AppUserRepository repo;


    @GetMapping("/register")
    public String register (Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute(registerDTO);
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register")
    public String register (
            Model model,
            @Valid @ModelAttribute RegisterDTO registerDTO,
            BindingResult result
    ){
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            result.addError(
                    new FieldError("registerDTO", "confirmPassword"
                            , "Senha não confere")
            );
        }

        AppUser appUser = repo.findByEmail(registerDTO.getEmail());
        if (appUser != null) {
            result.addError(
                    new FieldError("registerDTO", "email"
                    , "Email já está sendo usado")
            );
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {

            //Criando uma nova conta
            var bCryptEcoder = new BCryptPasswordEncoder();

            AppUser newUser = new AppUser();
            newUser.setFirstName(registerDTO.getFirstName());
            newUser.setLastName(registerDTO.getLastName());
            newUser.setEmail(registerDTO.getEmail());
            newUser.setAddress(registerDTO.getAddress());
            newUser.setPhone(registerDTO.getPhone());
            newUser.setRole("client");
            newUser.setCreatedAt(new Date());
            newUser.setPassword(bCryptEcoder.encode(registerDTO.getPassword()));

            repo.save(newUser);

            model.addAttribute("registerDTO", new RegisterDTO());
            model.addAttribute("success", true);

        }
        catch (Exception ex) {
            result.addError(
                    new FieldError("registerDTO", "firsName"
                    , ex.getMessage())
            );
        }

        return "register";
    }
}
