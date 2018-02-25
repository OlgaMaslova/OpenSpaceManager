package olga.maslova.outerspacemanager;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildingActivity extends AppCompatActivity {

    private String token;
    private List<Building> buildings;
    private ListView buildingsListView;
    private List<String> buildingsNames = new ArrayList<String>();
    private List<Integer> buildingsAmount = new ArrayList<Integer>();
    private Building chosenBuilding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        token = Tools.getToken(BuildingActivity.this);
        getBuildingsRequest(token);

        buildingsListView = (ListView) findViewById(R.id.buildingsListView);


        if (buildings != null && buildings.size() > 0) {
            buildingsListView.setAdapter(new BuildingArrayAdapter(getApplicationContext(), buildings));
        }

        buildingsListView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String buildingName = (String) buildingsListView.getItemAtPosition(position);
                chosenBuilding = findBuildingByName(buildingName);
                showDialog(chosenBuilding.getBuildingId());
            }
        });
    }

    private void getBuildingsRequest(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getBuildingsResponse> request = service.getBuildingsList(token);
        request.enqueue(new Callback<getBuildingsResponse>() {
            @Override
            public void onResponse(Call<getBuildingsResponse> call, Response<getBuildingsResponse> response) {
                if (response.code() == 200) {
                    buildings = response.body().getBuildings();
                    setBuildingsNamesAndAmount();
                } else {
                    Tools.showToast(BuildingActivity.this, "Cannot get information for this user");
                }
            }

            @Override
            public void onFailure(Call<getBuildingsResponse> call, Throwable t) {
                Tools.showToast(BuildingActivity.this, "Network error");
            }
        });

    }

    private void setBuildingsNamesAndAmount() {
        for (int k=0; k < buildings.size(); k++) {
            String name = buildings.get(k).getName();
            buildingsNames.add(name);
            buildingsAmount.add(buildings.get(k).getLevel());
        }
        buildingsListView.setAdapter(new BuildingArrayAdapter(getApplicationContext(), buildings));
       //nmbOfBuildings.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, buildingsAmount));
    }

    private void showDialog(final Integer ID) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(BuildingActivity.this);
        builder1.setMessage("Do you want to build this building?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        createBuildingRequest(ID);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void createBuildingRequest(Integer buildingID) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<postResponse> request = service.createBuilding(buildingID, token);
        request.enqueue(new Callback<postResponse>() {
            @Override
            public void onResponse(Call<postResponse> call, Response<postResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getCode() == "ok") {
                        Tools.showToast(BuildingActivity.this, "Building " + chosenBuilding.getName() + " is built!");
                    }
                } else {
                    Tools.showToast(BuildingActivity.this, "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<postResponse> call, Throwable t) {
                Tools.showToast(BuildingActivity.this, "Network error");
            }
        });

    }

    private Building findBuildingByName (String name) {
        Building building = new Building();
        for (int k=0; k < buildings.size(); k++) {
            String currentName = buildings.get(k).getName();
            if (currentName == name) {
                building = buildings.get(k);
            }
        }
        return building;
    }
}
