package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.MarkerRepository;
import org.launchcode.Atlas.dto.UpdateMarkerDTO;
import org.launchcode.Atlas.model.Marker;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController extends AtlasController{

    @Autowired
    MarkerRepository markerRepository;

    @GetMapping("/all")
    public String showAllMarkers(Model model, HttpSession session) {
        User user = getUserFromSession(session);
        model.addAttribute(new UpdateMarkerDTO());
        model.addAttribute("markers", markerRepository.findByUser_id(user.getId()));
        return "user/index";
    }

    @PostMapping("/update")
    public String processUpdateMarkerForm(@ModelAttribute @Valid UpdateMarkerDTO updateMarkerDTO, Errors error, Model model, HttpSession session) {
        if (error.hasErrors()) {
            model.addAttribute("updateMarkerDTO", updateMarkerDTO);
            User user = getUserFromSession(session);
            model.addAttribute("markers", markerRepository.findByUser_id(user.getId()));
            return "user/index";
        }

        Optional<Marker> markerToBeUpdated = markerRepository.findById(updateMarkerDTO.getId());
        if(markerToBeUpdated.isEmpty()) {
            model.addAttribute("works", "Error Updating marker – try again");
            return "marker/success";
        }
//        prevents creative javascript from submission -- via altering marker id -- TODO add custom validation
        if(markerToBeUpdated.get().getUser().getId() != getUserFromSession(session).getId()) {
            model.addAttribute("works", "Error Updating marker – try again");
            return "marker/success";
        }

        Marker markerTBU = markerToBeUpdated.get();
        markerTBU.setLocationWithBigDecimal(updateMarkerDTO.getLongitude(), updateMarkerDTO.getLatitude());
        markerTBU.setMarkerName(updateMarkerDTO.getMarkerName());
        markerRepository.save(markerTBU);

        model.addAttribute("works", "updated!");
        return "marker/success";
    }

    @PostMapping("/delete")
    public String processDeleteMarkerForm(@RequestParam int deleteThisMarkerId, Model model, HttpSession session) {
        //what does an error look like? wrong or invalid id -- custom validation
//        if (error.hasErrors()) {
//            model.addAttribute("updateMarkerDTO", updateMarkerDTO);
//            User user = getUserFromSession(session);
//            model.addAttribute("markers", markerRepository.findByUser_id(user.getId()));
//            return "user/index";
//        }
        //is marker really a marker
        Optional<Marker> markerToBeUpdated = markerRepository.findById(deleteThisMarkerId);
        if(markerToBeUpdated.isEmpty()) {
            model.addAttribute("works", "Error deleting selected marker – Try again");
            return "marker/success";
        }
//      does marker belong to current user
//      prevents creative javascript from submission -- via altering marker id
        if(markerToBeUpdated.get().getUser().getId() != getUserFromSession(session).getId()) {
            model.addAttribute("works", "Error deleting selected marker – Try again");
            return "marker/success";
        }

        Marker markerTBU = markerToBeUpdated.get();
        model.addAttribute("works", "Marker \"" + markerTBU.getMarkerName() + "\" deleted.");
        markerRepository.deleteById(markerTBU.getId());
        return "marker/success";
    }



}
