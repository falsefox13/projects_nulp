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
        assertEquals(marCarrier.selectCheapest(sender, dest, 50000, 25), "The cheapest: Transportation from: Port called Chornomorsk to Port called Europort\n" +
                                                                                                "with cargo: 25tonns will cost: 4466.0$\n" +
                                                                                                "and will last for:7 days");
    }

    @Test
    void selectFastest() {

        assertEquals(marCarrier.selectFastest(sender, dest, 100), "No available ships");
        marCarrier.addShip(new Ship(10000, 4000, 30, 400));
    void selectFastest() {0, 4000, 30, 400};
        marCarrier.addShip(new Ship(10000, 4000, 20, 550));
        assertEquals(marCarrier.selectFastest(sender, dest, 15), "The fastest: Transportation from: Port called Chornomorsk to Port called Europort\n" +
                                                                                "with cargo: 15tonns will cost: 3966.0$\n" +
                                                                                "and will last for:5 days");
    }

    @Test
    void doTransportation() {
        marCarrier.addShip(new Ship(10000, 4000, 30, 400));
        marCarrier.addShip(new Ship(10000, 4000, 20, 550));
        assertTrue(marCarrier.doTransportation(sender, dest, 25));
        assertFalse(marCarrier.doTransportation(sender, dest, 60));
    }

}