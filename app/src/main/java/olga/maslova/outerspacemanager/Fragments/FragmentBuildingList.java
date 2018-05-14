package olga.maslova.outerspacemanager.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import olga.maslova.outerspacemanager.Activities.BuildingActivity;
import olga.maslova.outerspacemanager.Adapters.BuildingArrayAdapter;
import olga.maslova.outerspacemanager.Building;
import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.ResponseRetroFit.getBuildingsResponse;
import olga.maslova.outerspacemanager.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentBuildingList extends Fragment implements AdapterView.OnItemClickListener{
    private String token;
    private List<Building> buildings;
    private ListView buildingsListView;
    private List<String> buildingsNames = new ArrayList<String>();
    private List<Integer> buildingsAmount = new ArrayList<Integer>();
    private Building chosenBuilding;
    private Boolean firstLaunch = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_building_list,container);
        buildingsListView = (ListView)v.findViewById(R.id.buildingsListView);
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
        buildingsListView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
      //  getBuildingsRequest(token);
    }

    private void getBuildingsRequest(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getResources().getString(getResources().getIdentifier("base_url", "string", getActivity().getPackageName())))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getBuildingsResponse> request = service.getBuildingsList(token);
        request.enqueue(new Callback<getBuildingsResponse>() {
            @Override
            public void onResponse(Call<getBuildingsResponse> call, Response<getBuildingsResponse> response) {
                if (response.code() == 200) {
                    buildings = response.body().getBuildings();
                    setBuildingsNamesAndAmount();
                    if (firstLaunch) {
                        ((BuildingActivity)getActivity()).updateView(buildings.get(0), true, null);
                        firstLaunch = false;
                    }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((BuildingActivity)getActivity()).updateView(buildings.get(position), false, (ImageView)view.findViewById(R.id.image));
    }

}
