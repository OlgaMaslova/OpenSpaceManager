package olga.maslova.outerspacemanager.activities;

import android.icu.text.StringPrepParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.adapters.SpinnerCustomAdapter;
import olga.maslova.outerspacemanager.responsesRetroFit.postResponse;
import olga.maslova.outerspacemanager.Ship;
import olga.maslova.outerspacemanager.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class AttackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private String token;
    private Spinner spinner;
    private ArrayList<Ship> attackingShips;
    private Button attackBtn;
    private Set<String> playerNames;
    private String targetUser;
    private List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
        spinner = (Spinner) findViewById(R.id.ships_spinner);
        attackBtn = (Button) findViewById(R.id.attackActionBtn);
        token = Tools.getToken(getApplicationContext());
        playerNames = Tools.getPlayers(getApplicationContext());
        attackingShips = (ArrayList<Ship>) getIntent().getSerializableExtra("yourShips");

        names = new ArrayList<String>();
        for(String name:playerNames) {
            names.add(name);
        }
        SpinnerCustomAdapter customAdapter=new SpinnerCustomAdapter(getApplicationContext(),names);
        spinner.setAdapter(customAdapter);
        spinner.setOnItemSelectedListener(this);

        attackBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        attackRequest();
                    }
                }
        );
    }


    private void attackRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getResources().getString(getResources().getIdentifier("base_url", "string", getPackageName())))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        try {
            JSONObject finalJson = new JSONObject();
            JSONArray obj = new JSONArray();

            for (Ship ship:attackingShips) {
                JSONObject paramObject = new JSONObject();
                paramObject.put("shipId", ship.getShipId());
                paramObject.put("amount", ship.getAmount());
                obj.put(paramObject);
            }
            finalJson.put("ships", obj);

            JsonParser jsonParser = new JsonParser();
            JsonObject gsonObject = (JsonObject) jsonParser.parse(finalJson.toString());
            Call<postResponse> request = service.postAttack("Morty", gsonObject, token);
            request.enqueue(new Callback<postResponse>() {
                @Override
                public void onResponse(Call<postResponse> call, Response<postResponse> response) {
                    if (response.code() == 200) {
                        if (response.body().getCode().equals("ok")) {
                            Tools.showToast(getApplicationContext(), "Attack is finished!");

                        }
                    } else {
                        Tools.showToast(getApplicationContext(), "Error" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<postResponse> call, Throwable t) {
                    Tools.showToast(getApplicationContext(), "Network error");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        targetUser = names.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
