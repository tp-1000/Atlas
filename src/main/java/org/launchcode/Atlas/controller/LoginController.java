package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.dto.LoginUserDTO;
import org.launchcode.Atlas.dto.RegisterUserDTO;
import org.launchcode.Atlas.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController extends AtlasController{

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute(new RegisterUserDTO());
        return "login/register";
    }

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

        User user = new User();
        user.setPasswordHash(registerUserDTO.getPassword());
        user.setUserName(registerUserDTO.getUserName());
        userRepository.save(user);
        model.addAttribute("validUser", user);
        return "welcome/index";

    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute(new LoginUserDTO());
        return "login/index";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginUserDTO loginUserDTO, Errors error, HttpServletRequest request, Model model) {
        //any form errors stop processing
        if(error.hasErrors()){
            return "login/index";
        }
        //check if user exists
        User loggedInUser  = userRepository.findByuserName(loginUserDTO.getUserName());
        if(loggedInUser != null){
            Boolean isCorrectPass = loggedInUser.isPasswordValid(loginUserDTO.getPassword());
            if(isCorrectPass){
                setSessionWithUser(request.getSession(), loggedInUser);
                model.addAttribute("validUser", loggedInUser);
                return "welcome/index";
            }
            error.rejectValue("password", "password.incorrect", "Incorrect password, try again");
                return "login/index";

        }
        //no user
        error.rejectValue("userName", "username.doesntexist", "No user with that name exists");
        return "login/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
            request.getSession().invalidate();
            return "redirect/login";
    }

}
