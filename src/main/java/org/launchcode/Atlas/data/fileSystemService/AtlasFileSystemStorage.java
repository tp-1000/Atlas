package org.launchcode.Atlas.data.fileSystemService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AtlasFileSystemStorage {
    void init();
    String saveFile(MultipartFile file, String newName);
    Resource loadFile(String fileName);
    void deleteFile(String fileName);
}
