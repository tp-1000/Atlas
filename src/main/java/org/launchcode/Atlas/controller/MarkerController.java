package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.MarkerRepository;
import org.launchcode.Atlas.data.fileSystemService.AtlasFileSystemStorage;
import org.launchcode.Atlas.dto.AddMarkerDTO;
import org.launchcode.Atlas.model.Marker;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("marker")
public class MarkerController extends AtlasController{

    @Autowired
    MarkerRepository markerRepository;
    @Autowired
    AtlasFileSystemStorage atlasFileSystemStorage;

    @GetMapping("/view")
    public String viewMap(Model model){
        model.addAttribute("instructions", "Click the map to look for Markers");
        return "marker/index";
    }

    @GetMapping("/add")
    public String viewAddMarker(Model model){
        model.addAttribute(new AddMarkerDTO());
        return "marker/add_marker";
    }

    @PostMapping("/add")
    public String processAddMarkerForm(@ModelAttribute @Valid AddMarkerDTO addMarkerDTO, Errors error, Model model, HttpSession session) {

        if(error.hasErrors()) {
            model.addAttribute("addMarkerDTO", addMarkerDTO);
            return "marker/add_marker";
        }

        MultipartFile image = addMarkerDTO.getImage();

        Marker aMarker = new Marker(addMarkerDTO.getMarkerName(), addMarkerDTO.getLongitude(), addMarkerDTO.getLatitude(), image.getOriginalFilename(), addMarkerDTO.getDescription());
        User user = getUserFromSession(session);
        aMarker.setUser(user);

        markerRepository.save(aMarker);

        atlasFileSystemStorage.saveFile(image, aMarker.getImageName());
//         confirm placement only used to provide success details on success page
        Optional<Marker> marker = markerRepository.findById(aMarker.getId());
        Marker marker1 = marker.get();
        model.addAttribute("works", marker1.getMarkerName());
        return "marker/success";
    }

}