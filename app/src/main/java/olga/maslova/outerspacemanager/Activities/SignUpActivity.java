package olga.maslova.outerspacemanager.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.Tools;
import olga.maslova.outerspacemanager.UserLogin;
import olga.maslova.outerspacemanager.ResponseRetroFit.authResponse;
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
    private Button btnValiderSignIn;
    private String name;
    private String password;
    private String mail;
    private String token;
    private EditText nameEditSignIn;
    private EditText passwordEditSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //connect with layout
        btnValider = (Button) findViewById(R.id.btnValider);
        nameEdit = (EditText) findViewById(R.id.nameTextField);
        passwordEdit =  (EditText) findViewById(R.id.mdpTextField);
        mailEdit = (EditText) findViewById(R.id.mailTextEdit);
        nameEditSignIn = (EditText) findViewById(R.id.nameTextFieldSignIn);
        passwordEditSignIn = (EditText) findViewById(R.id.mdpTextFieldSignIn);
        btnValiderSignIn = (Button) findViewById(R.id.btnValiderSignIn);

        //check if token exists already
        String tokenFromShared = Tools.getToken(getApplicationContext());
        if (tokenFromShared != null) {
            //Start MainActivity
            startMain(getNameFromShared());
        }

        btnValider.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        name = (String) nameEdit.getText().toString();
                        password = (String) passwordEdit.getText().toString();
                        mail = (String) mailEdit.getText().toString();
                        UserLogin user = new UserLogin(mail, name, password);
                        signUp(user);
                        nameEdit.getText().clear();
                        passwordEdit.getText().clear();
                        mailEdit.getText().clear();
                    }
                }
        );

        btnValiderSignIn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        name = (String) nameEditSignIn.getText().toString();
                        password = (String) passwordEditSignIn.getText().toString();
                        UserLogin user = new UserLogin(name, password);
                        signIn(user);
                        nameEditSignIn.getText().clear();
                        passwordEditSignIn.getText().clear();
                    }
                }
        );
    }

    private void signUp(UserLogin user) {
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
                    SaveToSharedToken(token, name);
                    startMain(name);
                } else {
                    Log.i("olgaLog", "code" + response.errorBody().toString());
                    Tools.showToast(SignUpActivity.this,"Cannot sign up");
                }
            }
            @Override
            public void onFailure(Call<authResponse> call, Throwable t) {
                System.out.println(t.toString());
                Tools.showToast(SignUpActivity.this, "Erreur réseau");

            }
        });
    }

    private void signIn(UserLogin user) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<authResponse> request=service.singInUser(user);
        request.enqueue(new Callback<authResponse>() {
            @Override
            public void onResponse(Call<authResponse> call, Response<authResponse> response) {
                if (response.code() == 200) {
                    token = response.body().getToken();
                    SaveToSharedToken(token, name);
                    startMain(name);
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



    private void SaveToSharedToken(String token, String name) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", token);
        editor.putString("username", name);
        editor.commit();
    }

    private void startMain(String name) {
        //start MainActivity
        Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
        myIntent.putExtra("username", name);
        startActivityForResult(myIntent,MAIN_ACTIVITY_REQUEST_CODE);
    }

    private String getNameFromShared() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String userFromShared = settings.getString("username", null);
        return userFromShared;
    }

}
