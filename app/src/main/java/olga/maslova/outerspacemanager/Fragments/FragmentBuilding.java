package olga.maslova.outerspacemanager.Fragments;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import olga.maslova.outerspacemanager.Activities.BuildingDetailActivity;
import olga.maslova.outerspacemanager.Building;
import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.ResponseRetroFit.postResponse;
import olga.maslova.outerspacemanager.Tools;
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
        token = Tools.getToken(getActivity());
        btnBuild.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(currentBuilding.getBuildingId());
                    }
                }
        );
        //postponeEnterTransition();
        getActivity().supportPostponeEnterTransition();
        return v;
    }

    public void updateView(Building building) {
        currentBuilding = building;
        buildingName.setText(building.getName());
        buildDesc.setText("Effect : " + building.getEffect() + "\n"+ "Gas Cost: " +currentBuilding.getGasCostByLevel() + "\n"+ "Mineral Cost: " +currentBuilding.getMineralCostByLevel() + "\n" +
                "Time to build in seconds: " + (currentBuilding.getTimeToBuildByLevel()*currentBuilding.getLevel()+currentBuilding.getTimeToBuildLevel0()) +
                "\n" + "Amount of Effect by level: " + currentBuilding.getAmountOfEffectByLevel());
        String imageURL = building.getImageUrl();
        Glide.with(this)
                .load(imageURL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //scheduleStartPostponedTransition(buildingImage);
                        getActivity().supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(buildingImage);
        if (currentBuilding.isBuilding()) {
            btnBuild.setEnabled(false);
            btnBuild.setAlpha((float) 0.5);
        } else {
            btnBuild.setEnabled(true);
            btnBuild.setAlpha((float) 1.0);
        }

    }

    private void showDialog(final Integer ID) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
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
                .baseUrl((getResources().getString(getResources().getIdentifier("base_url", "string", getActivity().getPackageName()))))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<postResponse> request = service.createBuilding(buildingID, token);
        request.enqueue(new Callback<postResponse>() {
            @Override
            public void onResponse(Call<postResponse> call, Response<postResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getCode().equals("ok")) {
                        Tools.showToast(getActivity(), "Started building " + currentBuilding.getName() + "!");
                    }
                } else {
                    Tools.showToast(getActivity(), "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<postResponse> call, Throwable t) {
                Tools.showToast(getActivity(), "Network error");
            }
        });

    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }

}
