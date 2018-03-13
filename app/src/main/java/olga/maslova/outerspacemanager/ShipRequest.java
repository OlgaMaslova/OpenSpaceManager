package olga.maslova.outerspacemanager;

/**
 * Created by omaslova on 13/03/2018.
 */

public class ShipRequest {
    private String shipId;
    private String amount;

    public ShipRequest(String shipId, String amount) {
        this.shipId = shipId;
        this.amount = amount;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getShipId() {
        return shipId;
    }

    public String getAmount() {
        return amount;
    }
}
