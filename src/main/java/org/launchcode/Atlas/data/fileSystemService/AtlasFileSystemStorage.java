package org.launchcode.Atlas.data.fileSystemService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AtlasFileSystemStorage {
    void init();
    String saveFile(MultipartFile file);
    Resource loadFile(String fileName);
}
