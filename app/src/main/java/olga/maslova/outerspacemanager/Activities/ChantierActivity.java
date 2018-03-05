package olga.maslova.outerspacemanager.Activities;

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

public class ChantierActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    private List<Ship> ships;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chantier);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        FragmentShipList fragA = (FragmentShipList) getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
        FragmentShip fragShip = (FragmentShip)getSupportFragmentManager().findFragmentById(R.id.fragmentShip_ID);
        if(fragShip == null|| !fragShip.isInLayout()){
            ships =  fragA.getShips();
            Intent i = new Intent(getApplicationContext(),ShipDetailActivity.class);
            i.putExtra("chosenShip", ships.get(position));
            startActivity(i);
        } else {
            ships =  fragA.getShips();
            fragShip.updateView(ships.get(position));
        }

    }



}
