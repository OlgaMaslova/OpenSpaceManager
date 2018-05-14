package olga.maslova.outerspacemanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import olga.maslova.outerspacemanager.Building;
import olga.maslova.outerspacemanager.Fragments.FragmentBuilding;
import olga.maslova.outerspacemanager.R;

public class BuildingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_detail);
        Building building = (Building) getIntent().getSerializableExtra("chosenBuilding");
        FragmentBuilding fragmentBuilding = (FragmentBuilding)getSupportFragmentManager().findFragmentById(R.id.fragmentBuilding_ID);
        fragmentBuilding.updateView(building);
    }



}
