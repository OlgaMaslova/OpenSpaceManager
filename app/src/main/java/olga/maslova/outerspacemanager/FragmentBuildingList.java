package olga.maslova.outerspacemanager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import olga.maslova.outerspacemanager.Activities.BuildingActivity;
import olga.maslova.outerspacemanager.Adapters.BuildingArrayAdapter;
import olga.maslova.outerspacemanager.ResponseRetroFit.getBuildingsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentBuildingList extends Fragment {
    private String token;
    private List<Building> buildings;
    private ListView buildingsListView;
    private List<String> buildingsNames = new ArrayList<String>();
    private List<Integer> buildingsAmount = new ArrayList<Integer>();
    private Building chosenBuilding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a,container);
        buildingsListView = (ListView)v.findViewById(R.id.shipsListView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        token = Tools.getToken((BuildingActivity)getActivity());
        if (buildings != null && buildings.size() > 0) {
            buildingsListView.setAdapter(new BuildingArrayAdapter(getActivity(),buildings));
        }
        getBuildingsRequest(token);
        buildingsListView.setOnItemClickListener((BuildingActivity)getActivity());
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
                    Tools.showToast((BuildingActivity)getActivity(), "Cannot get information for this user");
                }
            }

            @Override
            public void onFailure(Call<getBuildingsResponse> call, Throwable t) {
                Tools.showToast((BuildingActivity)getActivity(), "Network error");
            }
        });

    }

    private void setBuildingsNamesAndAmount() {
        for (int k=0; k < buildings.size(); k++) {
            String name = buildings.get(k).getName();
            buildingsNames.add(name);
            buildingsAmount.add(buildings.get(k).getLevel());
        }
        buildingsListView.setAdapter(new BuildingArrayAdapter(getActivity(), buildings));
    }

    public List<Building> getBuildings () {
        return buildings;
    }

}
