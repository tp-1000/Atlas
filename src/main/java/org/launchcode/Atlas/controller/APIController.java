package org.launchcode.Atlas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class APIController {

    @GetMapping("/upload")
    public String uploadForm () {
        return "user/upload";
    }
}
