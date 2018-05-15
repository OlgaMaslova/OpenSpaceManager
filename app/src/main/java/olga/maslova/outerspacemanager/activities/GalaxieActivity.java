package olga.maslova.outerspacemanager.activities;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import olga.maslova.outerspacemanager.adapters.UserArrayAdapter;
import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.responsesRetroFit.getGalaxieResponse;
import olga.maslova.outerspacemanager.Tools;
import olga.maslova.outerspacemanager.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalaxieActivity extends AppCompatActivity {

    private String token;
    private List<User> users;
    private ListView playersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxie);
        token = Tools.getToken(getApplicationContext());
        playersListView = (ListView) findViewById(R.id.playersListView);
        getAllUsers(token);
    }

    private void getAllUsers(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getResources().getString(getResources().getIdentifier("base_url", "string", getPackageName())))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getGalaxieResponse> request = service.getGalaxie(0, 20, token);
        request.enqueue(new Callback<getGalaxieResponse>() {
            @Override
            public void onResponse(Call<getGalaxieResponse> call, Response<getGalaxieResponse> response) {
                if (response.code() == 200) {
                    users = response.body().getUsers();
                    savePlayersToShared(users);
                    playersListView.setAdapter(new UserArrayAdapter(getApplicationContext(), users));
                } else {
                    Tools.showToast(getApplicationContext(), "Cannot get users");
                }
            }
            @Override
            public void onFailure(Call<getGalaxieResponse> call, Throwable t) {
                Tools.showToast(getApplicationContext(), "Network error");
            }
        });
    }

    private void savePlayersToShared(List<User> users) {
        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
        SharedPreferences.Editor editor = settings.edit();
        Set<String> playerNames = new HashSet<String>();
        for (User user:users) {
            playerNames.add(user.getUsername());
        }
        editor.putStringSet("players", playerNames);
        editor.commit();
    }
}
