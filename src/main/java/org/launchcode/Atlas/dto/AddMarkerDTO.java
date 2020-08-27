package org.launchcode.Atlas.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddMarkerDTO {

    @NotBlank
    @Size(max = 25, min = 1, message = "Name needs 1 to 25 characters")
    private String markerName;

    //TODO figure out proper validation
    // - will break if not a number
    @NotBlank(message = "Latitude required Ex: 31.353637")
    private String latitude;

    @NotBlank(message = "Longitude required Ex: 40.548475")
    private String longitude;

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
