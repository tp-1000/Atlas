package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.MarkerRepository;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController extends AtlasController{

    @Autowired
    MarkerRepository markerRepository;

    //get Environment var to protect API key
    String API_KEY = System.getenv("API_KEY");

    @GetMapping("/all")
    public String showAllMarkers(Model model, HttpSession session) {
        User user = getUserFromSession(session);

        model.addAttribute("markers", markerRepository.findByUser_id(user.getId()));
        return "user/index";
    }
}
