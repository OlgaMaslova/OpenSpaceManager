package olga.maslova.outerspacemanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import olga.maslova.outerspacemanager.Fragments.FragmentShipList;
import olga.maslova.outerspacemanager.Fragments.FragmentShip;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.Ship;

public class ChantierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chantier);
    }

    public void updateView(Ship ship, Boolean fragShipShown) {
        FragmentShipList fragShipList = (FragmentShipList) getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
        FragmentShip fragShip = (FragmentShip)getSupportFragmentManager().findFragmentById(R.id.fragmentShip_ID);
        if((fragShip == null || !fragShip.isInLayout()) && !fragShipShown){
            Intent i = new Intent(getApplicationContext(),ShipDetailActivity.class);
            i.putExtra("chosenShip", ship);
            startActivity(i);
        } else if (fragShip != null  && fragShip.isInLayout()) {
            fragShip.updateView(ship);
        }
    }



}
