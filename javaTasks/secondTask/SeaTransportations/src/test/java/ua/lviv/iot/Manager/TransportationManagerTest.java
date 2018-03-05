package ua.lviv.iot.Manager;

import org.junit.jupiter.api.Test;
import ua.lviv.iot.Models.Port;
import ua.lviv.iot.Models.Ship;

import static org.junit.jupiter.api.Assertions.*;

class TransportationManagerTest {

    private Port sender = new Port("Chornomorsk", 0.8, 0.5);
    private Port dest = new Port("Europort", 0.9, 0.07);

    private TransportationManager marCarrier = new TransportationManager();


    @Test
    void selectCheapest() {
        assertEquals(marCarrier.selectCheapest(sender, dest, 100, 50), "No available ships");

        marCarrier.addShip(new Ship(10000, 4000, 30, 400));
        marCarrier.addShip(new Ship(10000, 4000, 20, 550));
        assertEquals(marCarrier.selectCheapest(sender, dest, 0, 25), "There is no such cheap transportation");
        //assertEquals(marCarrier.selectCheapest(sender, dest, 50000, 25), "The cheapest: Ship leng"
    }

    @Test
    void selectFastest() {
        assertEquals(marCarrier.selectCheapest(sender, dest, 100, 50), "No available ships");

        marCarrier.addShip(new Ship(10000, 4000, 30, 400));
        marCarrier.addShip(new Ship(10000, 4000, 20, 550));
        assertEquals(marCarrier.selectCheapest(sender, dest, 0, 25), "There is no such cheap transportation");
    }

    @Test
    void doTransportation() {

        marCarrier.addShip(new Ship(10000, 4000, 30, 400));
        marCarrier.addShip(new Ship(10000, 4000, 20, 550));
        assertTrue(marCarrier.doTransportation(sender, dest, 25));
    }

}