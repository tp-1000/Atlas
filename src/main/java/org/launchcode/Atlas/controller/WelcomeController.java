package org.launchcode.Atlas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController extends AtlasController{

    @GetMapping
    public static String hello(){
        return "welcome/index";
    }
}
