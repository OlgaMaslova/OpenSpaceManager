package olga.maslova.outerspacemanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import olga.maslova.outerspacemanager.Building;
import olga.maslova.outerspacemanager.FragmentBuilding;
import olga.maslova.outerspacemanager.FragmentBuildingList;
import olga.maslova.outerspacemanager.R;

public class BuildingActivity extends AppCompatActivity {
    private List<Building> buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
    }


    public void updateView(Building building, Boolean fragBuildingShown) {
        FragmentBuildingList fragBuildingList = (FragmentBuildingList) getSupportFragmentManager().findFragmentById(R.id.fragmentBuildingList_ID);
        FragmentBuilding fragBuilding = (FragmentBuilding)getSupportFragmentManager().findFragmentById(R.id.fragmentBuilding_ID);
        if((fragBuilding == null|| !fragBuilding.isInLayout()) && !fragBuildingShown){
            buildings =  fragBuildingList.getBuildings();
            Intent i = new Intent(getApplicationContext(),BuildingDetailActivity.class);
            i.putExtra("chosenBuilding", building);
            startActivity(i);
        } else if (fragBuilding != null  && fragBuilding.isInLayout()) {
            buildings =  fragBuildingList.getBuildings();
            fragBuilding.updateView(building);
        }
    }
/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        FragmentBuildingList fragBuildingList = (FragmentBuildingList) getSupportFragmentManager().findFragmentById(R.id.fragmentBuildingList_ID);
        FragmentBuilding fragBuilding = (FragmentBuilding)getSupportFragmentManager().findFragmentById(R.id.fragmentBuilding_ID);
        if(fragBuilding == null|| !fragBuilding.isInLayout()){
            buildings =  fragBuildingList.getBuildings();
            Intent i = new Intent(getApplicationContext(),BuildingDetailActivity.class);
            i.putExtra("chosenBuilding", buildings.get(position));
            startActivity(i);
        } else {
            buildings =  fragBuildingList.getBuildings();
            fragBuilding.updateView(buildings.get(position));
        }

    }
*/
}
