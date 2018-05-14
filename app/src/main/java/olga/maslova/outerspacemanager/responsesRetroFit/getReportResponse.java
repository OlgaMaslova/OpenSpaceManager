package olga.maslova.outerspacemanager.responsesRetroFit;

import java.util.List;

import olga.maslova.outerspacemanager.Ship;

/**
 * Created by olgamaslova on 17/03/2018.
 */

public class getReportResponse {

    private List<Report> reports;
    private int size;

    public class Report {
        private List<Ship> attackerFleet;
        private FleetAfterBattle attackerFleetAfterBattle;
        private String date;
        private String dateInv;
        private List<Ship> defenderFleet;
        private FleetAfterBattle defenderFleetAfterBattle;
        private String from;
        private String to;
        private String type;
        private double gasWon;
        private double mineralsWon;

        public List<Ship> getAttackerFleet() {
            return attackerFleet;
        }

        public void setAttackerFleet(List<Ship> attackerFleet) {
            this.attackerFleet = attackerFleet;
        }

        public FleetAfterBattle getAttackerFleetAfterBattle() {
            return attackerFleetAfterBattle;
        }

        public void setAttackerFleetAfterBattle(FleetAfterBattle attackerFleetAfterBattle) {
            this.attackerFleetAfterBattle = attackerFleetAfterBattle;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDateInv() {
            return dateInv;
        }

        public void setDateInv(String dateInv) {
            this.dateInv = dateInv;
        }

        public List<Ship> getDefenderFleet() {
            return defenderFleet;
        }

        public void setDefenderFleet(List<Ship> defenderFleet) {
            this.defenderFleet = defenderFleet;
        }

        public FleetAfterBattle getDefenderFleetAfterBattle() {
            return defenderFleetAfterBattle;
        }

        public void setDefenderFleetAfterBattle(FleetAfterBattle defenderFleetAfterBattle) {
            this.defenderFleetAfterBattle = defenderFleetAfterBattle;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getGasWon() {
            return gasWon;
        }

        public void setGasWon(int gasWon) {
            this.gasWon = gasWon;
        }

        public double getMineralsWon() {
            return mineralsWon;
        }

        public void setMineralsWon(int mineralsWon) {
            this.mineralsWon = mineralsWon;
        }
    }

    public class FleetAfterBattle {
        private int capacity;
        private List<Ship> fleet;
        private int survivingShips;

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public List<Ship> getFleet() {
            return fleet;
        }

        public void setFleet(List<Ship> fleet) {
            this.fleet = fleet;
        }

        public int getSurvivingShips() {
            return survivingShips;
        }

        public void setSurvivingShips(int survivingShips) {
            this.survivingShips = survivingShips;
        }
    }

    public List<Report> getReports() {
        return reports;
    }

    public int getSize() {
        return size;
    }
}
