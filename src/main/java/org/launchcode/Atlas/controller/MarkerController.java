package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.MarkerRepository;
import org.launchcode.Atlas.dto.AddMarkerDTO;
import org.launchcode.Atlas.model.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("marker")
public class MarkerController {

    @Autowired
    MarkerRepository markerRepository;

    //get Environment var to protect API key
    String API_KEY = System.getenv("API_KEY");

    @GetMapping("/view")
    public String viewMap(Model model){
        //get all markers from repository
        model.addAttribute("markers", markerRepository.findAll());

        model.addAttribute("API_KEY", API_KEY);
        return "marker/index";
    }

    @GetMapping("/add")
    public String viewAddMarker(Model model){
        model.addAttribute("API_KEY", API_KEY);
        model.addAttribute(new AddMarkerDTO());
        return "marker/add_marker";
    }

    @PostMapping("/add")
    public String processAddMarkerForm(@ModelAttribute @Valid AddMarkerDTO addMarkerDTO, Errors error, Model model) {
        if(error.hasErrors()) {
            //may not be needed, may require redirect
            model.addAttribute("API_KEY", API_KEY);
            model.addAttribute("addMarkerDTO", addMarkerDTO);
            return "marker/add_marker";
        }

        Marker aMarker = new Marker(addMarkerDTO.getMarkerName(), addMarkerDTO.getLatitude(), addMarkerDTO.getLongitude());
        markerRepository.save(aMarker);
        // confirm placement only used to provide success details on success page
        Optional<Marker> marker = markerRepository.findById(aMarker.getId());
        Marker marker1 = marker.get();
        model.addAttribute("works", marker1.getLocation());
        return "login/success";
    }

}

//TODO use story - Show marker for area
// - []There is a form that takes a location and returns a map of that area
// - []Markers are selected from database based on location parameter and displayed on the map
// - []A message displays no markers for an area if there are no markers.
// - []if there are markers for an area they are displayed
// - []problems with the form location data produce an error message
// - [x]map can zoom and pan
// - []A new location can be entered and a new map generated.
