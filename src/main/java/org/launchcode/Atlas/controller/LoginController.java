package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.UserRepository;
import org.launchcode.Atlas.dto.RegisterUserDTO;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute(new RegisterUserDTO());
        return "login/index";
    }

//    form gets submittied and then the fields get data binding
    @PostMapping("/register")
    public String processRegisterForm(@ModelAttribute @Valid RegisterUserDTO registerUserDTO, Errors error, Model model) {
        if(error.hasErrors()){
            model.addAttribute(registerUserDTO);
            return "login/register";
        }
        User existingUser = userRepository.findByuserName(registerUserDTO.getUserName());
        if(existingUser != null) {
            error.rejectValue("userName", "username.alreadyexists", "A user with that name already exists");
            return "login/register";
        }
        //if object fields not valid --> get "index" mapped to failed object and error message "field [failed requirement]"
        //if name in use --> get "index" mapped to new object and error message "name take"
        // if unused name and valid data --> get "success" and save user
        User user = new User();
        user.setPasswordHash(registerUserDTO.getPassword());
        user.setUserName(registerUserDTO.getUserName());
        userRepository.save(user);
        return "login/success";
    }
}
