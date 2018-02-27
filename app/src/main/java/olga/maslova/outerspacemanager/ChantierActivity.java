package olga.maslova.outerspacemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChantierActivity extends AppCompatActivity {

    private String token;
    private List<Ship> ships;
    private ListView shipsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chantier);

        token = Tools.getToken(ChantierActivity.this);
        getChantierRequest(token);
        shipsListView = (ListView) findViewById(R.id.shipsListView);

        if (ships != null && ships.size() > 0) {
            shipsListView.setAdapter(new ShipsArrayAdapter(getApplicationContext(), ships));
        }
    }

    private void getChantierRequest(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getShipsResponse> request = service.getFleetList(token);
        request.enqueue(new Callback<getShipsResponse>() {
            @Override
            public void onResponse(Call<getShipsResponse> call, Response<getShipsResponse> response) {
                if (response.code() == 200) {
                    ships = response.body().getShips();
                    shipsListView.setAdapter(new ShipsArrayAdapter(getApplicationContext(), ships));
                } else {
                    Tools.showToast(ChantierActivity.this, "Cannot get fleet for this user");
                }
            }

            @Override
            public void onFailure(Call<getShipsResponse> call, Throwable t) {
                Tools.showToast(ChantierActivity.this, "Network error");
            }
        });

    }

}
