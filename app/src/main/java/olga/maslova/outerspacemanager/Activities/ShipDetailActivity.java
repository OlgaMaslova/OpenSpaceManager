package olga.maslova.outerspacemanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import olga.maslova.outerspacemanager.Fragments.FragmentShip;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.Ship;

public class ShipDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_detail);
        Ship ship = (Ship) getIntent().getSerializableExtra("chosenShip");
        FragmentShip fragShip = (FragmentShip)getSupportFragmentManager().findFragmentById(R.id.fragmentShip_ID);
        fragShip.updateView(ship);
    }
}
