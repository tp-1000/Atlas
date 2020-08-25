package org.launchcode.Atlas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("marker")
public class MarkerController {

    //get Enviroment var to protect API key
    String API_KEY = System.getenv("API_KEY");

    @GetMapping
    public String viewMap(Model model){
        model.addAttribute("API_KEY", API_KEY);
        return "marker/index";
    }
}
