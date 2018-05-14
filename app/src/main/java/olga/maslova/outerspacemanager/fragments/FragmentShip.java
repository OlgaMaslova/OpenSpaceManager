package olga.maslova.outerspacemanager.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.responsesRetroFit.postResponse;
import olga.maslova.outerspacemanager.Ship;
import olga.maslova.outerspacemanager.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentShip extends Fragment {
    private TextView shipName;
    private ImageView shipImage;
    private TextView shipDesc;
    private Ship currentShip;
    private Button btnBuild;
    private String token;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup  container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ship,container);
        token = Tools.getToken(getActivity());
        shipName = (TextView)v.findViewById(R.id.ShipTitle);
        shipImage = (ImageView)v.findViewById(R.id.shipImage);
        shipDesc = (TextView)v.findViewById(R.id.ShipDescription);
        btnBuild = (Button)v.findViewById(R.id.BuildShipBtn);
        btnBuild.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(currentShip.getShipId());
                    }
                }
        );
        return v;
    }
    public void updateView(Ship ship) {
        currentShip = ship;
        shipName.setText(ship.getName());
        shipDesc.setText("Life: " + ship.getLife() + "\n" + "Maximum attack: " + ship.getMaxAttack() + "\n" + "Minimum attack: " + ship.getMinAttack() + "\n" + "Shield: " + ship.getShield() + "\n" +
        "Time to build: " + ship.getTimeToBuild() + "\n" + "Speed: " + ship.getSpeed());
        if (ship.getShipId() == 0) {
            shipImage.setImageResource(R.drawable.chasseur_leger);
        }
        if (ship.getShipId() == 1) {
            shipImage.setImageResource(R.drawable.chasseur_lourd);
        }
        if (ship.getShipId() == 2) {
            shipImage.setImageResource(R.drawable.spyship);
        }
        if (ship.getShipId() == 3) {
            shipImage.setImageResource(R.drawable.destroyer);
        }
        if (ship.getShipId() == 4) {
            shipImage.setImageResource(R.drawable.death_star);
        }
    }

    private void showDialog(final Integer ID) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Do you want to build this ship?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        createShipRequest(ID);
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

    private void createShipRequest(Integer shipID) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getResources().getString(getResources().getIdentifier("base_url", "string", getActivity().getPackageName())))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        HashMap<String, String> amountJson = new HashMap<>();
        amountJson.put("amount", "1");
        Call<postResponse> request = service.createShip(shipID, amountJson, token);
        request.enqueue(new Callback<postResponse>() {
            @Override
            public void onResponse(Call<postResponse> call, Response<postResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getCode().equals("ok")) {
                        Tools.showToast(getActivity(), "Ship " + currentShip.getName() + " is built!");
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

}
