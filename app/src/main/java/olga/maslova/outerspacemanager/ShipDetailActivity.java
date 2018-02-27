package olga.maslova.outerspacemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShipDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_detail);
        Ship ship = (Ship) getIntent().getSerializableExtra("chosenShip");
        FragmentShip fragShip = (FragmentShip)getSupportFragmentManager().findFragmentById(R.id.fragmentShip_ID);
        fragShip.fillTextView(ship);
    }
}
