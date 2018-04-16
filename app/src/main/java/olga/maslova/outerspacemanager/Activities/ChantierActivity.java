package olga.maslova.outerspacemanager.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import olga.maslova.outerspacemanager.FragmentShipList;
import olga.maslova.outerspacemanager.FragmentShip;
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
