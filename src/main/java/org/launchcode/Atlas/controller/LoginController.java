package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.UserRepository;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(new User());
        return "login/index";
    }

//    form gets submittied and then the fields get data binding
    @PostMapping()
    public String processLogin(@ModelAttribute @Valid User user, Errors error, Model model) {
        if(error.hasErrors()){
            model.addAttribute(user);
            return "login/index";
        }
        //if object fields not valid --> get "index" mapped to failed object and error message "field [failed requirement]"
        //if name in use --> get "index" mapped to new object and error message "name take"
        // if unused name and valid data --> get "success" and save user
        userRepository.save(user);
        return "login/success";
    }
}
