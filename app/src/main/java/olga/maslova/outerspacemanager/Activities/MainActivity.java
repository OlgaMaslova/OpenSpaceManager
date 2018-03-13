package olga.maslova.outerspacemanager.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.Tools;
import olga.maslova.outerspacemanager.User;
import olga.maslova.outerspacemanager.UserDataSource;
import olga.maslova.outerspacemanager.ResponseRetroFit.getUserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final int VUE_GENERALE_REQUEST_CODE = 2;
    private static final int VUE_FLEET_REQUEST_CODE = 3;
    private static final int VUE_GALAXIE_REQUEST_CODE = 4;
    private Button btnVueGenerale;
    private Button btnBatiments;
    private Button btnDisconnect;
    private Button btnFlotte;
    private Button btnChantier;
    private Button btnGalaxie;
    private String token;
    private String username;
    private TextView userTextView;
    //database
    private UserDataSource userDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //connect to layout
        btnDisconnect = (Button) findViewById(R.id.disconnectID);
        btnVueGenerale = (Button) findViewById(R.id.btnGeneral);
        btnFlotte = (Button) findViewById(R.id.btnFlotte);
        btnChantier = (Button) findViewById(R.id.btnChantier);
        userTextView = (TextView) findViewById(R.id.Username);
        btnBatiments = (Button) findViewById(R.id.buildingID);
        btnGalaxie = (Button) findViewById(R.id.btnGalaxie);

        token = Tools.getToken(getApplicationContext());
        getUserRequest();
        btnVueGenerale.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showVueGenerale(username);
                    }
                }
        );
        btnDisconnect.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        disconnect(username);
                    }
                }
        );
        btnBatiments.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        startBuilding();
                    }
                }
        );
        btnFlotte.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showFleet();
                    }
                }
        );
        btnChantier.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showChantier();
                    }
                }
        );
        btnGalaxie.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showGalaxie();
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        userDataSource = new UserDataSource(getApplicationContext());
        userDataSource.open();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userDataSource.close();
    }

    private void getUserRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getUserResponse> request=service.getUser(token);
        request.enqueue(new Callback<getUserResponse>() {
            @Override
            public void onResponse(Call<getUserResponse> call, Response<getUserResponse> response) {
                if (response.code()==200) {
                    username = response.body().getUsername();
                    userTextView.setText(username);
                    refreshUserDB(response.body());
                } else {
                    Tools.showToast(getApplicationContext(), "Cannot get information for this user");
                }
            }

            @Override
            public void onFailure(Call<getUserResponse> call, Throwable t) {
                Tools.showToast(MainActivity.this, "Network error");
            }
        });
    }


    private void refreshUserDB(getUserResponse data) {
       userDataSource.insertOrRefreshUser(data);
    }

    private void disconnect(String username) {
        this.username = null;
        //clean token in Shared
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", null);
        editor.commit();
        //finish MainActivity
        Intent closeMain = new Intent();
        setResult(RESULT_OK,closeMain);
        finish();
    }
    //Start Activities
    private void showVueGenerale(String username) {
        User currentUser = userDataSource.getUser(username);
        //show Vue Generale
        Intent myIntent = new Intent(getApplicationContext(),VueGeneraleActivity.class);
        myIntent.putExtra("CURRENT_USER", currentUser);
        startActivityForResult(myIntent,VUE_GENERALE_REQUEST_CODE);
    }

    private void startBuilding() {
        Intent myIntent = new Intent(getApplicationContext(),BuildingActivity.class);

        startActivityForResult(myIntent,VUE_GENERALE_REQUEST_CODE);
    }

    private void showChantier() {
        Intent myIntent = new Intent(getApplicationContext(),ChantierActivity.class);
        startActivityForResult(myIntent,VUE_GENERALE_REQUEST_CODE);
    }

    private void showFleet() {
        Intent myIntent = new Intent(getApplicationContext(),FleetActivity.class);
        startActivityForResult(myIntent,VUE_FLEET_REQUEST_CODE);
    }

    private void showGalaxie(){
        Intent myIntent = new Intent(getApplicationContext(),GalaxieActivity.class);
        startActivityForResult(myIntent,VUE_GALAXIE_REQUEST_CODE);
    }
}
