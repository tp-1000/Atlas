package org.launchcode.Atlas.dto;

import org.launchcode.Atlas.validation.coordinate.CoordinatesNotNull;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class UpdateMarkerDTO {


    @Min(0)
    private int id;

    @NotBlank
    @Size(max = 25, min = 1, message = "Name needs 1 to 25 characters")
    private String markerName;

    @DecimalMin("-90.0000")
    @DecimalMax("90.0000")
    @CoordinatesNotNull
    private BigDecimal latitude;

    @DecimalMin("-180.0000")
    @DecimalMax("180.0000")
    @CoordinatesNotNull
    private BigDecimal longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarkerName() {
        return markerName;
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
