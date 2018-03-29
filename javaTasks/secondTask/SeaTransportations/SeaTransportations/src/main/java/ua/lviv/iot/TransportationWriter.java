package ua.lviv.iot;

import ua.lviv.iot.Models.SeaTransportation;
import ua.lviv.iot.Models.Ship;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class TransportationWriter {
    public final void writeToFile(List<Ship> ships, Map<Integer, SeaTransportation> seaTransportations) {
        try (PrintWriter writer = new PrintWriter("file.csv")) {

            for (Ship ship : ships) {
                writer.println(ship.getHeaders());
                writer.println(ship.toCSV());
            }

            for (Map.Entry<Integer,SeaTransportation> transp : seaTransportations.entrySet()) {
                writer.println(transp.getValue().petHeaders());
                writer.println(transp.getValue().toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
