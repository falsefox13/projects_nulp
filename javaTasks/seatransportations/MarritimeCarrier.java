
package seatransportations;

import java.util.ArrayList;


public class MarritimeCarrier {
    private ArrayList<Ship> availableShips = new ArrayList<Ship>();
    private ArrayList<SeaTransportation> transportations = new ArrayList<SeaTransportation>();
    
    public MarritimeCarrier() {}
    
    private int getIndexOfNeededShip(int cargo)
    {
        int indexOfNeededShip = -1;
        if(availableShips.isEmpty() != true)
        {
            for(int i = 0; i < availableShips.size(); i++)
            {
                if(availableShips.get(i).getCapacity() >= cargo)
                    indexOfNeededShip = i;
            }
        }
        return indexOfNeededShip;
    }
    
    public String selectCheapest(Port sender, Port dest, int maxPrice, int cargo)
    {
        SeaTransportation possible = new SeaTransportation(sender, dest, cargo, maxPrice);
        int indexOfShip = getIndexOfNeededShip(cargo);
        if(indexOfShip >= 0)
        {
            if(possible.calculatePrice() < maxPrice)       
            {
                possible.calculateTime(availableShips.get(indexOfShip).getSpeed());
                return "The cheapest: " + possible.ToString();
            }
            else
                return "There is no such cheap transportation";
        }
        else
            return "No available ships";
    }
        
     public String selectFastest(Port sender, Port dest, int cargo)
    {
        SeaTransportation possible = new SeaTransportation(sender, dest, cargo);
        int indexOfShip = getIndexOfNeededShip(cargo);
        if(indexOfShip >= 0)
        {
            int fastestShipIndex = -1;
            int fastest = 0;
            for(int i = 0; i < availableShips.size(); i++)
            {
                if(availableShips.get(i).getSpeed() > fastest)
                    fastestShipIndex = i;
            }
            possible.calculateTime(availableShips.get(fastestShipIndex).getSpeed());
            return "The fastest: " + possible.ToString();
        }
        else
            return "No available ships";
    }   
     
    public boolean doTransportation(Port sender, Port dest, int cargo) 
    {
        int indexOfShip = getIndexOfNeededShip(cargo);
        if(indexOfShip >= 0)
        {
            transportations.add(new SeaTransportation(sender, dest, cargo));
            availableShips.remove(indexOfShip);
            return true;
        }
        else
            return false;            
    }
    
    public ArrayList<Ship> getAvailableShips()
    {
        return this.availableShips;
    }
    
    public void addShip(Ship obj)
    {
        this.availableShips.add(obj);
    }
    
    public ArrayList<SeaTransportation> getTransportations()
    {
        return this.transportations;
    }
}

