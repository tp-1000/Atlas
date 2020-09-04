package org.launchcode.Atlas.model;


import org.locationtech.jts.geom.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class Marker extends AbstractEntity{

    private String markerName;

    @Column(columnDefinition="geography(Point,4326)")
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Marker() {
    }

    public Marker(String markerName, BigDecimal lng, BigDecimal lat) {
        this.markerName = markerName;
        Double x = lng.doubleValue();
        Double y = lat.doubleValue();
        PrecisionModel pm = new PrecisionModel();
        GeometryFactory gf = new GeometryFactory( pm,4326);
        Coordinate xy = new Coordinate(x,y);
        this.location = gf.createPoint(xy);
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

}
