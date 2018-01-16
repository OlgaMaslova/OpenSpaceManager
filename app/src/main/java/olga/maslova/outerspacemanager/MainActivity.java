package olga.maslova.outerspacemanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private Button btnVueGenerale;
    private Button btnBatiments;
    private String token;
    private String username;
    private TextView userTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnVueGenerale = (Button) findViewById(R.id.btnGeneral);
        userTextView = (TextView) findViewById(R.id.Username);
        token = getToken();
        getUser();


        btnVueGenerale.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                       getUser();
                    }
                }
        );
    }

    private void getUser() {
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
                    showPersonalInfo();
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

    private String getToken() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String tokenFromShared = settings.getString("token", null);
        return tokenFromShared;
    }

    private void showPersonalInfo() {

    }
}
