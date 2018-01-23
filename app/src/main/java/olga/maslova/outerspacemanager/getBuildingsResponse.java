package olga.maslova.outerspacemanager;

import java.util.List;

/**
 * Created by omaslova on 23/01/2018.
 */

public class getBuildingsResponse {
    public Integer getSize() {
        return size;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    private Integer size;
    private List<Building> buildings;
}
