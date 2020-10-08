package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.MarkerRepository;
import org.launchcode.Atlas.data.fileSystemService.AtlasFileSystemStorage;
import org.launchcode.Atlas.model.Marker;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;


@RestController
@RequestMapping("/api")
public class APIController extends AtlasController{
    @Autowired
    AtlasFileSystemStorage atlasFileSystemStorage;
    @Autowired
    MarkerRepository markerRepository;

//    @GetMapping("/markers/user/{fileName:\\d+}")
    @GetMapping("/markers/user/allMarkers")
    public List<Marker> allUsersMarkers(HttpSession session){
        User user = getUserFromSession(session);
        //TODO Address correct rejection of request if invalid user
        if(user == null){
            return null;
        };

        List<Marker> markers = markerRepository.findByUser_id(user.getId());
        return markers;
    }

    @GetMapping("/markers/search")
    public List<Marker> markerSearch(@RequestParam("lat") String lat, @RequestParam("lng") String lng, @RequestParam("radius") String radius, HttpSession session){
        User user = getUserFromSession(session);
        //TODO Address correct rejection of request if invalid user
        if(user == null){
            return null;
        };
        double r = Double.parseDouble(radius);
        String lnglat = "SRID=4326;POINT(" + lng + " " + lat + ")";
        List<Marker> markers = markerRepository.getMarkerNearPoint(lnglat, r);

        return markers;
    }


    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = atlasFileSystemStorage.loadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
