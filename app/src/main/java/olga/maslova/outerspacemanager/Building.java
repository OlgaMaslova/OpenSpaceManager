package olga.maslova.outerspacemanager;

/**
 * Created by omaslova on 23/01/2018.
 */

public class Building {
    private Integer level;
    private Integer amountOfEffectByLevel;
    private Integer amountOfEffectLevel0;
    private Integer buildingId;
    private boolean building;
    private String effect;
    private Integer gasCostByLevel;
    private Integer gasCostLevel0;
    private String imageUrl;
    private Integer mineralCostByLevel;
    private Integer mineralCostLevel0;
    private String name;
    private Integer timeToBuildByLevel;
    private Integer timeToBuildLevel0;

    public String getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getGasCostByLevel() {
        return gasCostByLevel;
    }

    public Integer getTimeToBuildByLevel() {
        return timeToBuildByLevel;
    }

    public Integer getMineralCostByLevel() {

        return mineralCostByLevel;
    }
}
