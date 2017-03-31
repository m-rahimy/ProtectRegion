package ir.rahimy.protect_region.region;

import org.bukkit.Location;

/*
A simple class for holding data for a region

    : center location
    : name of region
    : region's radius

 */
public class Region {
    private Location location;
    private String name;
    private double rad;

    public Region(String name, Location location, double rad) {
        this.location = location;
        this.name = name;
        this.rad = rad;
    }

    public Location location() {
        return location;
    }

    public String name() {
        return name;
    }

    public double radius(){return rad;}

    public void setRad(double radius){ rad=radius;}

}
