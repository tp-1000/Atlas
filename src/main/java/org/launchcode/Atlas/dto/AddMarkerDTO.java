package org.launchcode.Atlas.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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


    private MultipartFile image;

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
