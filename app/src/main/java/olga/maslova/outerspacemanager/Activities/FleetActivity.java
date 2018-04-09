package olga.maslova.outerspacemanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.ResponseRetroFit.getFleetResponse;
import olga.maslova.outerspacemanager.Ship;
import olga.maslova.outerspacemanager.Adapters.ShipsArrayAdapter;
import olga.maslova.outerspacemanager.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FleetActivity extends AppCompatActivity {

    private String token;
    private List<Ship> ships;
    private int size;
    private ListView fleetListView;
    private Button attackBtn;
    private ArrayList<Ship> arrayShips;
    private Button reportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_fleet);
        fleetListView = (ListView) findViewById(R.id.FleetListView);
        token = Tools.getToken(getApplicationContext());
        getFleetRequest(token);
        reportBtn = (Button) findViewById(R.id.getReportBtn);
        reportBtn.setVisibility(View.VISIBLE);
        reportBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showReportActivity();
                    }
                }
        );
    }

    private void getFleetRequest(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getFleetResponse> request = service.getFleet(token);
        request.enqueue(new Callback<getFleetResponse>() {
            @Override
            public void onResponse(Call<getFleetResponse> call, Response<getFleetResponse> response) {
                if (response.code() == 200) {

                    size = response.body().getSize();
                    if (size == 0) {
                        Tools.showToast(getApplicationContext(), "You have no ships yet!");
                    } else {
                        ships = response.body().getShips();
                        arrayShips = new ArrayList<Ship>(ships);
                        fleetListView.setAdapter(new ShipsArrayAdapter(getApplicationContext(), ships));
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
        Intent myIntent = new Intent(getApplicationContext(),AttackActivity.class);
        myIntent.putExtra("yourShips", arrayShips);
        startActivityForResult(myIntent,2);
    }

    private void showReportActivity(){
        Intent myIntent = new Intent(getApplicationContext(),ShowReportActivity.class);
        startActivityForResult(myIntent, 1);
    }
}
