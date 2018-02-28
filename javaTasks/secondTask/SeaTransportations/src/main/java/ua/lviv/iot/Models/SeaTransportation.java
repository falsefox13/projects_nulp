package ua.lviv.iot.Models;

/**
 * Class that describe one concrete transportation,
 * can calculate time, price and distance of one transportation
 * @author falsefox
 */
public class SeaTransportation {

    /**
     * For calculating a distance
     */
    public static final double RADIUS_OF_EARTH = 6371.009;
    
    private Port sender;
    private Port destination;
    private double priceOfTransportation;
    private int maxPrice;
    private int durationInDays;
    private int cargo; //in tonns

    public SeaTransportation() {}

    public SeaTransportation(Port sender, Port destination, int cargo) {
        this.sender = sender;
        this.destination = destination;
        this.cargo = cargo;
        calculatePrice();
        calculateTime(300);
    }
    
    public SeaTransportation(Port sender, Port destination, int cargo, int maxPrice) {
        this.sender = sender;
        this.destination = destination;
        this.cargo = cargo;
        this.maxPrice = maxPrice;
        calculatePrice();
        calculateTime(300);
    }
    
    /**
     * Calculate distance of transportation, using formula from wikipedia
     * @return distance in kilometers
     */
    public double calculateDistance() {
        double diffOfLatitude = destination.getLatitude() - sender.getLatitude();
        double diffOfLongtitude = destination.getLongtitude() - sender.getLongtitude();
        double averageLatitude = (destination.getLatitude() - sender.getLatitude())/2;
        double distance = RADIUS_OF_EARTH*(Math.sqrt(Math.pow(diffOfLatitude,2) + Math.pow((Math.acos(averageLatitude)*diffOfLongtitude),2)));
        return Math.floor(distance);
    }
    
    /**
     * Calculate price of transportation using data from http://itl-ltd.com
     * @return price in $
     */
    public double calculatePrice() {
        int priceOfContainer;
        if(this.cargo <= 20) {
            priceOfContainer = 1000;
        } else if(this.cargo > 20 && this.cargo < 40) {
            priceOfContainer = 1500;
        } else {
            priceOfContainer = 50*this.cargo;
        }
        this.priceOfTransportation = priceOfContainer+calculateDistance();
        return Math.floor(this.priceOfTransportation);
    }
    
    /**
     * Calculate duration of the transportation
     * @param averageSpeed speed of the Ship
     * @return duration of the transportation in days
     */
    public int calculateTime(int averageSpeed) {
        this.durationInDays = (int)calculateDistance() / averageSpeed; 
        return this.durationInDays;
    }

    @Override
    public String toString() {
        return "Transportation from: "+ this.sender.toString()
                    + " to " + this.destination.toString()
                    + "\nwith cargo: " + this.cargo 
                    + "tonns will cost: " + this.priceOfTransportation 
                    + "$\nand will last for:" + this.durationInDays + " days";
    }

    public Port getSender() {
        return this.sender;
    }

    public void setSender(Port newSender) {
        this.sender = newSender;
    }

    public Port getDestination() {
        return this.destination;
    }
    
    public void setDestination(Port newDestination) {
        this.destination = newDestination;
    }

    public double getPriceOfTransportation() {
        return this.priceOfTransportation;
    }

    public void setPriceOfTransportation(double newPrice) {
        this.priceOfTransportation = newPrice;
    }

    public int getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(int newMaxPrice) {
        this.maxPrice = newMaxPrice;
    }
 
    public int getDuration() {
        return this.durationInDays;
    }

    public void setDuration(int newDuration) {
        this.durationInDays = newDuration;
    }

    public int getCargo() {
        return this.cargo;
    }
    
    public void setCargo(int newCargo) {
        this.cargo = newCargo;
    }
}
