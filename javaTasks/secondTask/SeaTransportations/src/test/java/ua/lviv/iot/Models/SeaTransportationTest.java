package ua.lviv.iot.Models;

import org.junit.jupiter.api.Test;
import ua.lviv.iot.Manager.TransportationManager;

import static org.junit.jupiter.api.Assertions.*;
import static ua.lviv.iot.Models.SeaTransportation.RADIUS_OF_EARTH;

class SeaTransportationTest {
    private Port sender = new Port("Chornomorsk", 0.8, 0.5);
    private Port dest = new Port("Europort", 0.9, 0.07);

    private TransportationManager marCarrier = new TransportationManager();

    private int cargo = 30;
    private SeaTransportation transp = new SeaTransportation(sender, dest, cargo);

    @Test
    void calculateDistance() {
        double diffOfLatitude = dest.getLatitude() - sender.getLatitude();
        double diffOfLongtitude = dest.getLongtitude() - sender.getLongtitude();
        double averageLatitude = (dest.getLatitude() - sender.getLatitude())/2;
        double distance = RADIUS_OF_EARTH*(Math.sqrt(Math.pow(diffOfLatitude,2) + Math.pow((Math.acos(averageLatitude)*diffOfLongtitude),2)));
        double expected =  Math.floor(distance);
        assertEquals(expected, transp.calculateDistance());
    }

    @Test
    void calculatePrice() {
        int priceOfContainer;
        if(this.cargo <= 20) {
            priceOfContainer = 1000;
        } else if(this.cargo > 20 && this.cargo < 40) {
            priceOfContainer = 1500;
        } else {
            priceOfContainer = 50*this.cargo;
        }
        double expected = priceOfContainer + transp.calculateDistance();
        expected =  Math.floor(expected);
        assertEquals(expected, transp.calculatePrice());
    }

    @Test
    void calculateTime() {
        int averageSpeed = 400;
        int expected = (int)transp.calculateDistance() / averageSpeed;
        assertEquals(expected, transp.calculateTime(averageSpeed));
    }
}