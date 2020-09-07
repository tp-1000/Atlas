package org.launchcode.Atlas.model;

import org.launchcode.Atlas.data.fileSystemService.AtlasFileSystemService;
import org.launchcode.Atlas.data.fileSystemService.AtlasFileSystemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class FileResponse {
    private String fileName;
    private String fileUrl;
    private String message;

    public FileResponse(String fileName, String fileUrl, String message) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
