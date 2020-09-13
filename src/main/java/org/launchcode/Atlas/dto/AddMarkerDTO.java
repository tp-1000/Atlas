package org.launchcode.Atlas.dto;

import org.launchcode.Atlas.validation.image.ValidImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class AddMarkerDTO {

    @NotBlank
    @Size(max = 25, min = 1, message = "Name needs 1 to 25 characters")
    private String markerName;

    @DecimalMin("-90.0000")
    @DecimalMax("90.0000")
    private BigDecimal latitude;
    //@NotBlank(message = "Latitude required Ex: 31.353637")

    @DecimalMin("-180.0000")
    @DecimalMax("180.0000")
    private BigDecimal longitude;
    //@NotBlank(message = "Longitude required Ex: 40.548475")

    @ValidImage
    private MultipartFile image;

    @NotBlank
    @Size(min = 1, max = 1000, message = "Must be 1 to 1000 characters")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMarkerName() {
        return markerName;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
