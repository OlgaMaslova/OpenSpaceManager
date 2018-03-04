package olga.maslova.outerspacemanager;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChantierActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String token;
    private List<Ship> ships;
    private ListView shipsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chantier);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        FragmentA fragA = (FragmentA) getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
        FragmentShip fragShip = (FragmentShip)getSupportFragmentManager().findFragmentById(R.id.fragmentShip_ID);
        if(fragShip == null|| !fragShip.isInLayout()){
            ships =  fragA.getShips();
            Intent i = new Intent(getApplicationContext(),ShipDetailActivity.class);
            i.putExtra("chosenShip", ships.get(position));
            startActivity(i);
        } else {
            ships =  fragA.getShips();
            fragShip.fillTextView(ships.get(position));
        }

    }



}
