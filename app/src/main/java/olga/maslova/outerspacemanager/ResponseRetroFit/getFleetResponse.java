package olga.maslova.outerspacemanager.ResponseRetroFit;

import java.util.List;

import olga.maslova.outerspacemanager.Ship;

/**
 * Created by omaslova on 13/03/2018.
 */

public class getFleetResponse {
    private int size;
    private List<Ship> ships;

    public int getSize() {
        return size;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
