package org.launchcode.Atlas.model;


import org.locationtech.jts.geom.*;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
public class Marker extends AbstractEntity{

    private String markerName;

    @Column(columnDefinition="geography(Point,4326)")
    private Point location;

    public Marker() {
    }

    public Marker(String markerName, String lng, String lat) {
        this.markerName = markerName;
        Double x = Double.valueOf(lng);
        Double y = Double.valueOf(lat);
        PrecisionModel pm = new PrecisionModel();
        GeometryFactory gf = new GeometryFactory( pm,4326);
        Coordinate xy = new Coordinate(x,y);
        this.location = gf.createPoint(xy);
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

//    public String wKT() {
//        return "SRID=4326;POINT("+getLocation().getX() + " " + getLocation().getY() + ")";
//    }
}
