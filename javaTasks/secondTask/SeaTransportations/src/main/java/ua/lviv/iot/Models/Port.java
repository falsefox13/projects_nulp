package ua.lviv.iot.Models;

public class Port {
    private String name;
    private double longtitude;
    private double latitude;

    public Port() {
    }

    public Port(final String newName) {
        this.name = newName;
    }

    public Port(final String newName, final double newLongtitude, final double newLatitude) {
        this.name = newName;
        this.longtitude = newLongtitude;
        this.latitude = newLatitude;
    }

    @Override
    public final String toString() {
        return "Port called " + this.name;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(final String newName) {
        this.name = newName;
    }

    public final double getLongtitude() {
        return this.longtitude;
    }

    public final void setLongtitude(final double newLongtitude) {
        this.longtitude = newLongtitude;
    }

    public final double getLatitude() {
        return this.latitude;
    }

    public final void setLatitude(final double newLatitude) {
        this.latitude = newLatitude;
    }
}
