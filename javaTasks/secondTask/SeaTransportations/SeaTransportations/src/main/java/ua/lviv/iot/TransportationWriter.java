package ua.lviv.iot;

import ua.lviv.iot.Models.SeaTransportation;
import ua.lviv.iot.Models.Ship;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TransportationWriter {
    public final void writeToFile(List<Ship> ships, List<SeaTransportation> seaTransportations) {
        try (PrintWriter writer = new PrintWriter("file.csv")) {

            for (Ship ship : ships) {
                writer.println(ship.getHeaders());
                writer.println(ship.toCSV());
            }

            for (SeaTransportation transportation : seaTransportations) {
                writer.println(transportation.getHeaders());
                writer.println(transportation.toCSV());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
