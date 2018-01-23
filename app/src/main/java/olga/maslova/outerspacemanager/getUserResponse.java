package olga.maslova.outerspacemanager;

/**
 * Created by omaslova on 16/01/2018.
 */

public class getUserResponse {
    private String gas;
    private String gasModifier;
    private String minerals;
    private String mineralsModifier;
    private String points;
    private String username;

    public String getGas() {
        return gas;
    }

    public String getMinerals() {
        return minerals;
    }

    public String getPoints() {
        return points;
    }

    public String getUsername() {
        return username;
    }

    public getUserResponse(String pUsername, String pPoints, String pGas, String pMinerals) {
        this.gas = pGas;
        this.username = pUsername;
        this.minerals = pMinerals;
        this.points = pPoints;
    }


}
