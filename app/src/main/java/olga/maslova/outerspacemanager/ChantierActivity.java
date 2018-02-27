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

        shipsListView = (ListView) findViewById(R.id.shipsListView);

        if (ships != null && ships.size() > 0) {
            shipsListView.setAdapter(new ShipsArrayAdapter(getApplicationContext(), ships));
        }
    }



}
