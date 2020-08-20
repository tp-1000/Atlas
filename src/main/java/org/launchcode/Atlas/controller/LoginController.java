package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.UserRepository;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String processLogin(@ModelAttribute User user) {
        User x = user;
        userRepository.save(user);
        return "login/success";
    }
}
