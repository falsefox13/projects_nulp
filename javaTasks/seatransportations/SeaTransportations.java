
package seatransportations;


public class SeaTransportations {


    public static void main(String[] args) {
        Port sender = new Port("Chornomorsk", 0.8, 0.5);
        Port dest = new Port("Europort", 0.9, 0.07);
        
        MarritimeCarrier marCarrier = new MarritimeCarrier();
        marCarrier.addShip(new Ship(10000, 4000, 30, 400));
        marCarrier.addShip(new Ship(10000, 4000, 20, 550));
        
        System.out.println(marCarrier.selectCheapest(sender, dest, 50000, 25));
        System.out.println(marCarrier.selectFastest(sender, dest, 15));
        
        if(marCarrier.doTransportation(sender, dest, 30))
            System.out.println("Transportation done");
        else
            System.out.println("Something went wrong");
    }
    
}
