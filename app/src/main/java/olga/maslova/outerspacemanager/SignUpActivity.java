package olga.maslova.outerspacemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final int MAIN_ACTIVITY_REQUEST_CODE = 1;
    private EditText nameEdit;
    private EditText passwordEdit;
    private EditText mailEdit;
    private Button btnValider;
    private String name;
    private String password;
    private String mail;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnValider = (Button) findViewById(R.id.btnValider);
        nameEdit = (EditText) findViewById(R.id.nameTextField);
        passwordEdit =  (EditText) findViewById(R.id.mdpTextField);
        mailEdit = (EditText) findViewById(R.id.mailTextEdit);

        //check if token exists already
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String tokenFromShared = settings.getString("token", null);
        if (tokenFromShared != null) {
            //Start MainActivity
            startMain();
        }

        btnValider.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        name = (String) nameEdit.getText().toString();
                        password = (String) passwordEdit.getText().toString();
                        mail = (String) mailEdit.getText().toString();
                        User user = new User(mail, name, password);
                        login(user);
                    }
                }
        );



    }

    private void login(User user) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<authResponse> request=service.createUser(user);
        request.enqueue(new Callback<authResponse>() {
            @Override
            public void onResponse(Call<authResponse> call, Response<authResponse> response) {
                if (response.code() == 200) {
                    token = response.body().getToken();
                    SaveToSharedToken(token);
                    startMain();
                } else {
                    Log.i("olgaLog", "code" + response.errorBody().toString());
                    Tools.showToast(SignUpActivity.this,"Cannot log in");
                }
            }
            @Override
            public void onFailure(Call<authResponse> call, Throwable t) {
                System.out.println(t.toString());
                Tools.showToast(SignUpActivity.this, "Erreur réseau");

            }
        });
    }



    private void SaveToSharedToken(String token) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", token);
        editor.commit();
    }

    private void startMain() {
        //start MainActivity
        Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(myIntent,MAIN_ACTIVITY_REQUEST_CODE);
    }



}
