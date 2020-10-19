package org.launchcode.Atlas.data.fileSystemService;

import org.launchcode.Atlas.UploadConfigProperties;
import org.launchcode.Atlas.data.fileSystemService.exception.AtlasFileNotFoundException;
import org.launchcode.Atlas.data.fileSystemService.exception.AtlasFileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Base64;

@Service
public class AtlasFileSystemService implements AtlasFileSystemStorage{
    private final Path DIRLOCATION;

    @Autowired
    public AtlasFileSystemService(UploadConfigProperties uploadConfigProperties) {
        //sets location, the path is formatted correctly with methods
        this.DIRLOCATION = Path.of(uploadConfigProperties.getLocation()).toAbsolutePath().normalize();
    }

    @Override
    @PostConstruct //timing and execution instructions
    //make this dir at this location else throw error
    public void init() {
        try {
            Files.createDirectories(this.DIRLOCATION);
        } catch (IOException e) {
            throw new AtlasFileStorageException("Could not create upload dir!");
        }
    }


    public String saveFile(MultipartFile file, String newName) {
        if(newName.equals("no_image_picked")){
            return "no image was uploaded";
        }

            //try with resources to auto close
        try (InputStream fileStream = file.getInputStream()) {
            String fileName = newName;
            Path dfile = this.DIRLOCATION.resolve(fileName);
            //no other option but replace existing at this time
            // input stream, target, options.
            Files.copy(fileStream, dfile, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new AtlasFileStorageException("Could not upload the file");
        }
    }

    @Override
    public String saveDataURL(String dataURL, String fullFileName) {
        //first part of dataURL is ignored for base64 conversion
        byte[] imagedata = Base64.getDecoder().decode(dataURL.substring(dataURL.indexOf(",")+1));

        try(ByteArrayInputStream ByteArrayImageStream = new ByteArrayInputStream(imagedata)) {
            Path dfile = this.DIRLOCATION.resolve(fullFileName);
//            BufferedImage bufferedImage = ImageIO.read(ByteArrayImageStream);
//            ImageIO.write(bufferedImage, "jpeg", new File(newName));
            Files.copy(ByteArrayImageStream, dfile, StandardCopyOption.REPLACE_EXISTING);
            return "ID_image.jpeg added to local storage";
//        ImageIO.write(bufferedImage, "jpeg", ImageIO.createImageOutputStream(bufferedImage));
        } catch (IOException e) {
            throw  new AtlasFileStorageException("Could not upload the file");
        }
    }

    @Override
    public Resource loadFile(String fileName) {
       try {
           Path file = this.DIRLOCATION.resolve(fileName).normalize();
           Resource resource = new UrlResource(file.toUri());
           if(resource.exists() || resource.isReadable()) {
               return resource;
           }
           else {
               throw new AtlasFileNotFoundException("Could not find file");
           }
       } catch (MalformedURLException e) {
            throw new AtlasFileNotFoundException("Could not download file");
       }
    }

    @Override
    public void deleteFile(String fileName) {
        if(fileName.equals("no_image_picked")){
            return; //nothing to delete so don't continue method
        }
        try {
            Path file = this.DIRLOCATION.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                resource.getFile().delete();
            }
            else {
                throw new AtlasFileNotFoundException("Could not find file");
            }
        } catch (MalformedURLException e) {
            throw new AtlasFileNotFoundException("Could not download file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
