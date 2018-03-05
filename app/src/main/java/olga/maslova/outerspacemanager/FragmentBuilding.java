package olga.maslova.outerspacemanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import olga.maslova.outerspacemanager.Activities.BuildingDetailActivity;
import olga.maslova.outerspacemanager.ResponseRetroFit.postResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by omaslova on 05/03/2018.
 */

public class FragmentBuilding extends Fragment {
    private String token;
    private TextView buildingName;
    private ImageView buildingImage;
    private TextView buildDesc;
    private Button btnBuild;
    private Building currentBuilding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_building,container);
        buildingName = (TextView)v.findViewById(R.id.BuildingTitle);
        buildingImage = (ImageView)v.findViewById(R.id.BuildImage);
        buildDesc = (TextView)v.findViewById(R.id.BuildingDescription);
        btnBuild = (Button)v.findViewById(R.id.BuildBtn);
        token = Tools.getToken((BuildingDetailActivity)getActivity());
        btnBuild.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(currentBuilding.getBuildingId());
                    }
                }
        );
        return v;
    }
    public void updateView(Building building) {
        currentBuilding = building;
        buildingName.setText(building.getName());
        buildDesc.setText("Effect : " + building.getEffect() + "\n"+ "Gas Cost: " +currentBuilding.getGasCostByLevel() + "\n"+ "Mineral Cost: " +currentBuilding.getMineralCostByLevel() + "\n" +
                "Time to build: " + currentBuilding.getTimeToBuildByLevel() + "\n" + "Amount of Effect by level: " + currentBuilding.getAmountOfEffectByLevel());
        String imageURL = building.getImageUrl();
        Glide.with(this).load(imageURL).into(buildingImage);
    }

    private void showDialog(final Integer ID) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder((BuildingDetailActivity)getActivity());
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
                    if (response.body().getCode().equals("ok")) {
                        Tools.showToast((BuildingDetailActivity)getActivity(), "Building " + currentBuilding.getName() + " is built!");
                    }
                } else {
                    Tools.showToast((BuildingDetailActivity)getActivity(), "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<postResponse> call, Throwable t) {
                Tools.showToast((BuildingDetailActivity)getActivity(), "Network error");
            }
        });

    }

}
