package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.fileSystemService.AtlasFileSystemStorage;
import org.launchcode.Atlas.model.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    AtlasFileSystemStorage atlasFileSystemStorage;

    @PostMapping("/uploadfile") //returns a http response
    public ResponseEntity<FileResponse> uploadSingleFile (@RequestParam("file") MultipartFile file) {
        String upfile = atlasFileSystemStorage.saveFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(upfile)
                .toUriString();
        return ResponseEntity.status(HttpStatus.OK).body(new FileResponse(upfile, fileDownloadUri, "File uploaded with success!"));
    }

    @PostMapping("/uploadfiles")
    public ResponseEntity<List<FileResponse>> uploadMultipuleFiles (@RequestParam("files") MultipartFile[] files) {
        //converts a list of multipart files into a list of file responses (collect - repacks them)
        // each file gets added to the file system
        // the response object gets built with lambda
        List<FileResponse> responses = Arrays.asList(files).stream().map(
                file -> {
                    String upfile = atlasFileSystemStorage.saveFile(file);
                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/download/")
                            .path(upfile)
                            .toUriString();
                    return new FileResponse(upfile, fileDownloadUri, "Files uploaded success");
                }
        )
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = atlasFileSystemStorage.loadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
