package org.launchcode.Atlas;

import org.springframework.boot.context.properties.ConfigurationProperties;


//Annotation maps this class to the file properties, and provides Java Access (Bind custom Properties)
// example -- file.upload.location .. used in Atlas Application
@ConfigurationProperties(prefix = "file.upload")
public class UploadConfigProperties {

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
