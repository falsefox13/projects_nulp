package ua.lviv.iot.Main;

import ua.lviv.iot.Manager.TransportationManager;
import ua.lviv.iot.Models.Port;
import ua.lviv.iot.Models.Ship;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Port sender = new Port("Chornomorsk", 0.8, 0.5);
        Port dest = new Port("Europort", 0.9, 0.07);
        
        TransportationManager marCarrier = new TransportationManager();
        marCarrier.addShip(new Ship(10000, 4000, 30, 400));
        marCarrier.addShip(new Ship(10000, 4000, 20, 550));
        
        System.out.println("Selecting the cheapest proposition:");
        System.out.println(marCarrier.selectCheapest(sender, dest, 50000, 25));
        
        System.out.println("Selecting the fastest proposition:");
        System.out.println(marCarrier.selectFastest(sender, dest, 15));
        
        if(marCarrier.doTransportation(sender, dest, 30)) {
            System.out.println("Transportation done");
        } else {
            System.out.println("Something went wrong");
        }
    }
    
}

