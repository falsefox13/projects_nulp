package ua.lviv.iot.Manager;

import ua.lviv.iot.Models.Port;
import ua.lviv.iot.Models.SeaTransportation;
import ua.lviv.iot.Models.Ship;

import java.util.ArrayList;

/**
 * Class that handle transportations,
 * can select the cheapest or the fastest propositions.
 *
 * @author falsefox13
 */
public class TransportationManager {
    private ArrayList<Ship> availableShips = new ArrayList<Ship>();
    private ArrayList<SeaTransportation> transportations = new ArrayList<SeaTransportation>();

    public TransportationManager() {
    }

    /**
     * Get an index of first suitable available ship.
     *
     * @param cargo needed cargo.
     * @return index of needed Ship.
     */
    private int getIndexOfNeededShip(final int cargo) {
        int indexOfNeededShip = -1;
        if (availableShips.isEmpty() != true) {
            for (int i = 0; i < availableShips.size(); i++) {
                if (availableShips.get(i).getCapacity() >= cargo) {
                    indexOfNeededShip = i;
                }
            }
        }
        return indexOfNeededShip;
    }

    /**
     * Select the first cheapest available transportation.
     *
     * @return String with description of the transportation.
     */
    public final String selectCheapest(final Port sender, final Port dest, final int maxPrice, final int cargo) {
        SeaTransportation possible = new SeaTransportation(sender, dest, cargo, maxPrice);
        int indexOfShip = getIndexOfNeededShip(cargo);
        if (indexOfShip >= 0) {
            if (possible.calculatePrice() < maxPrice) {
                possible.calculateTime(availableShips.get(indexOfShip).getSpeed());
                return "The cheapest: " + possible.toString();
            } else {
                return "There is no such cheap transportation";
            }
        } else {
            return "No available ships";
        }
    }

    /**
     * Select the first fastest available transportation.
     *
     * @return String with description of the transportation.
     */
    public final String selectFastest(final Port sender, final Port dest, final int cargo) {
        SeaTransportation possible = new SeaTransportation(sender, dest, cargo);
        int indexOfShip = getIndexOfNeededShip(cargo);
        if (indexOfShip >= 0) {
            int fastestShipIndex = -1;
            int fastest = 0;
            for (int i = 0; i < availableShips.size(); i++) {
                if (availableShips.get(i).getSpeed() > fastest) {
                    fastestShipIndex = i;
                }
            }
            possible.calculateTime(availableShips.get(fastestShipIndex).getSpeed());
            return "The fastest: " + possible.toString();
        } else {
            return "No available ships";
        }
    }

    /**
     * Proceed the transportation.
     *
     * @return true if successful.
     */
    public final boolean doTransportation(final Port sender, final Port dest, final int cargo) {
        int indexOfShip = getIndexOfNeededShip(cargo);
        if (indexOfShip >= 0) {
            transportations.add(new SeaTransportation(sender, dest, cargo));
            availableShips.remove(indexOfShip);
            return true;
        } else {
            return false;
        }
    }

    public final ArrayList<Ship> getAvailableShips() {
        return this.availableShips;
    }

    public final void addShip(final Ship obj) {
        this.availableShips.add(obj);
    }

    public final ArrayList<SeaTransportation> getTransportations() {
        return this.transportations;
    }
}
