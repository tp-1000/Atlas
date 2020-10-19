package org.launchcode.Atlas.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.launchcode.Atlas.data.MarkerSerializer;
import org.locationtech.jts.geom.*;

import javax.persistence.*;
import java.math.BigDecimal;

@JsonSerialize(using = MarkerSerializer.class)
@Entity
public class Marker extends AbstractEntity implements Cloneable{

    private String markerName;

    @Column(columnDefinition="geography(Point,4326)")
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    String imageName;

    String description;

    public Marker() {
    }

    public Marker(String markerName, BigDecimal lng, BigDecimal lat, String originalName, String description) {
        this.markerName = markerName;
        Double x = lng.doubleValue();
        Double y = lat.doubleValue();
        PrecisionModel pm = new PrecisionModel();
        GeometryFactory gf = new GeometryFactory( pm,4326);
        Coordinate xy = new Coordinate(x,y);
        this.location = gf.createPoint(xy);
        setImageName(originalName);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImageName() {
        if(imageName.equals("no_image_picked")){
            return imageName;
        }
        return this.getId()+this.imageName;
    }

    //preserves extension and when get() adds marker id to the base
    public void setImageName(String originalName) {
        if(originalName == null || originalName.isEmpty()){
            this.imageName = "no_image_picked";
        } else {
            this.imageName = "_image.jpeg";
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setLocationWithBigDecimal(BigDecimal lng, BigDecimal lat){
        Double x = lng.doubleValue();
        Double y = lat.doubleValue();
        PrecisionModel pm = new PrecisionModel();
        GeometryFactory gf = new GeometryFactory( pm,4326);
        Coordinate xy = new Coordinate(x,y);
        this.location = gf.createPoint(xy);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
