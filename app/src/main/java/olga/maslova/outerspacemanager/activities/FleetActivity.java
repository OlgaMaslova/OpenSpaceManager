package olga.maslova.outerspacemanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import olga.maslova.outerspacemanager.OnSelectedShipListener;
import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.responsesRetroFit.getFleetResponse;
import olga.maslova.outerspacemanager.Ship;
import olga.maslova.outerspacemanager.adapters.ShipsArrayAdapter;
import olga.maslova.outerspacemanager.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FleetActivity extends AppCompatActivity implements OnSelectedShipListener {

    private String token;
    private List<Ship> ships;
    private int size;
    private TextView chosenFleet;
    private ListView fleetListView;
    private Button attackBtn;
    private ArrayList<Ship> arrayShips  = new ArrayList<>();;
    private HashMap<String, Integer> attackingFleet = new HashMap<>();
    private ImageView shipImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_fleet);
        fleetListView = (ListView) findViewById(R.id.FleetListView);
        chosenFleet = (TextView) findViewById(R.id.chosen_fleet_textView);
        token = Tools.getToken(getApplicationContext());
        shipImage = (ImageView) findViewById(R.id.ship_image);
        getFleetRequest(token);

    }

    private void getFleetRequest(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl((getResources().getString(getResources().getIdentifier("base_url", "string", getPackageName()))))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getFleetResponse> request = service.getFleet(token);
        request.enqueue(new Callback<getFleetResponse>() {
            @Override
            public void onResponse(Call<getFleetResponse> call, Response<getFleetResponse> response) {
                if (response.code() == 200) {

                    size = response.body().getSize();
                    if (size == 0) {
                        shipImage.setVisibility(View.VISIBLE);
                        Tools.showToast(getApplicationContext(), "You have no ships yet!");
                    } else {
                        shipImage.setVisibility(View.INVISIBLE);
                        ships = response.body().getShips();
                        fleetListView.setAdapter(new ShipsArrayAdapter(getApplicationContext(), ships, FleetActivity.this));
                        attackBtn = (Button) findViewById(R.id.attackbtn);
                        attackBtn.setOnClickListener(
                                new View.OnClickListener() {
                                    public void onClick(View v) {
                                        showAttackActivity();
                                    }
                                }
                        );
                    }
                } else {
                    Tools.showToast(getApplicationContext(), "Cannot get fleet for this user");
                }
            }

            @Override
            public void onFailure(Call<getFleetResponse> call, Throwable t) {
                Tools.showToast(getApplicationContext(), "Network error");
            }
        });
    }

    public void showAttackActivity() {
        //prepare the fleet
        for (String chosenShip:attackingFleet.keySet()) {
            Ship ship = new Ship();
            ship.setIdFromName(chosenShip);
            ship.setAmount(attackingFleet.get(chosenShip));
            arrayShips.add(ship);
        }
        if (arrayShips.isEmpty()) {
            Tools.showToast(getApplicationContext(), "Choose your ships before attacking!");
            return;
        }
        Intent myIntent = new Intent(getApplicationContext(),AttackActivity.class);
        myIntent.putExtra("yourShips", arrayShips);
        startActivityForResult(myIntent,2);
    }

    @Override
    public void onSelected(Ship ship, int progress) {
        chosenFleet.setText("Attack with ");
        if (attackingFleet.containsKey(ship.getName())){
            attackingFleet.remove(ship.getName());
        }

        if (!attackingFleet.containsKey(ship.getName()) && progress != 0) {
            attackingFleet.put(ship.getName(), progress);
        }

        for (String chosenShip:attackingFleet.keySet()) {
            String previousText = (String) chosenFleet.getText();
            if (previousText != "Attack with ") {
                chosenFleet.setText(previousText + ", " + attackingFleet.get(chosenShip) + " " + chosenShip);
            } else {
                chosenFleet.setText(previousText + attackingFleet.get(chosenShip) + " " + chosenShip);
            }
        }
    }

}
