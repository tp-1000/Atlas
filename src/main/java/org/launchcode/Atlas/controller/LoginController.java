package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.UserRepository;
import org.launchcode.Atlas.dto.LoginUserDTO;
import org.launchcode.Atlas.dto.RegisterUserDTO;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    //string to be used in new session attribute
    private static final String USERSESSIONKEY = "default_user";

    //set a user (if present) into the seesion attributes returns nothong.
    public void setSessionWithUser(HttpSession session, User user){
        session.setAttribute(USERSESSIONKEY, user.getId());
    }

    //get user from session data --> and check USERSESSIONKEY for a user returns user if present or null.
    public User getUserFromSession(HttpSession session) {
        Integer userID = (Integer) session.getAttribute(USERSESSIONKEY);
        if(userID == null){
            return null;
        }

        Optional<User> user = userRepository.findById(userID);
        if(user.isEmpty()){
            return null;
        }

        return user.get();
    }

    @ModelAttribute("validUser")
    public User getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(); //get current session or make new one
        return getUserFromSession(session);
//        User user = loginController.getUserFromSession(session); //look for user from session, if present then login process worked (could be null)
    }

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
        return "login/success";

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
                return "login/success";
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
