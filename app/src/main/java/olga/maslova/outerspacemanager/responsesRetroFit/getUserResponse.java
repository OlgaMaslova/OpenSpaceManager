package olga.maslova.outerspacemanager.responsesRetroFit;

/**
 * Created by omaslova on 16/01/2018.
 */

public class getUserResponse {
    private double gas;
    private String gasModifier;
    private double minerals;
    private String mineralsModifier;
    private String points;
    private String username;

    public double getGas() {
        return gas;
    }

    public double getMinerals() {
        return minerals;
    }

    public String getPoints() {
        return points;
    }

    public String getUsername() {
        return username;
    }

    public getUserResponse(String pUsername, String pPoints, double pGas, double pMinerals) {
        this.gas = pGas;
        this.username = pUsername;
        this.minerals = pMinerals;
        this.points = pPoints;
    }


}
