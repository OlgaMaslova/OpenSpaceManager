package olga.maslova.outerspacemanager;

/**
 * Created by omaslova on 27/02/2018.
 */

public class Ship {

    private Integer gasCost;
    private Integer life;
    private Integer maxAttack;
    private Integer minAttack;
    private Integer mineralCost;
    private String name;
    private Integer shipId;
    private Integer shield;
    private Integer spatioportLevelNeeded;
    private Integer speed;
    private Integer timeToBuild;

    public String getName() {
        return name;
    }

    public Integer getShipId() {
        return shipId;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Integer getTimeToBuild() {
        return timeToBuild;
    }

    public Integer getGasCost() {
        return gasCost;
    }

    public Integer getLife() {
        return life;
    }

    public Integer getMaxAttack() {
        return maxAttack;
    }

    public Integer getMinAttack() {
        return minAttack;
    }

    public Integer getMineralCost() {
        return mineralCost;
    }

    public Integer getSpatioportLevelNeeded() {
        return spatioportLevelNeeded;
    }
}
