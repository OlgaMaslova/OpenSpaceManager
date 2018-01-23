package olga.maslova.outerspacemanager;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by omaslova on 23/01/2018.
 */

public class User implements Serializable {

    public UUID getUuid() {
        return uuid;
    }

    private UUID uuid;

    public String getUsername() {
        return username;
    }

    public String getPoints() {
        return points;
    }

    public String getGas() {
        return gas;
    }

    public String getMinerals() {
        return minerals;
    }

    private String username;
    private String points;
    private String gas;
    private String minerals;

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public void setMinerals(String minerals) {
        this.minerals = minerals;
    }


}
