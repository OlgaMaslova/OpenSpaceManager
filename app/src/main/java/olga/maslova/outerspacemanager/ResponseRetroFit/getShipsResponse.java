package olga.maslova.outerspacemanager.ResponseRetroFit;

import java.util.List;

import olga.maslova.outerspacemanager.Ship;

/**
 * Created by omaslova on 27/02/2018.
 */

public class getShipsResponse {

    private Integer size;
    private List<Ship> ships;

    public List<Ship> getShips() {
        return ships;
    }

    public Integer getSize() {
        return size;
    }
}
