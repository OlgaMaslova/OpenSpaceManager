package olga.maslova.outerspacemanager;

import java.io.Serializable;

/**
 * Created by omaslova on 27/02/2018.
 */

public class Ship implements Serializable {

    private Integer amount;
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

    public Integer getShield() {
        return shield;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setShipName(Integer shipId) {
        switch (shipId) {
            case 0:
                this.name = "Chasseur léger";
            case 1:
                this.name = "Chassuer lourd";
            case 2:
                this.name = "Sonde d'espionnage";
            case 3:
                this.name = "Destroyer";
            case 4:
                this.name = "Etoile de la mort";
        }
    }
}
