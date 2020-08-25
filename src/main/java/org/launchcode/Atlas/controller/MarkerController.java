package org.launchcode.Atlas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("marker")
public class MarkerController {

    //get Enviroment var to protect API key
    String API_KEY = System.getenv("API_KEY");

    @GetMapping("/view")
    public String viewMap(Model model){
        model.addAttribute("API_KEY", API_KEY);
        return "marker/index";
    }

    @GetMapping("/add")
    public String viewAddMarker(Model model){
        model.addAttribute("API_KEY", API_KEY);
        return "marker/add_marker";
    }

    @PostMapping("/add")
    public String processAddMarkerForm() {
        return "login/success";
    }

}
