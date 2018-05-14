package olga.maslova.outerspacemanager.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import olga.maslova.outerspacemanager.activities.ChantierActivity;
import olga.maslova.outerspacemanager.adapters.ShipsArrayAdapter;
import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.responsesRetroFit.getShipsResponse;
import olga.maslova.outerspacemanager.Ship;
import olga.maslova.outerspacemanager.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentShipList extends Fragment implements AdapterView.OnItemClickListener {
        private ListView shipsListView;
        private List<Ship> ships;
        private String token;
        private Ship chosenShip;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_a,container);
            shipsListView = (ListView)v.findViewById(R.id.shipsListView);
            return v;
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            token = Tools.getToken((ChantierActivity)getActivity());
            if (ships != null && ships.size() > 0) {
                shipsListView.setAdapter(new ShipsArrayAdapter(getActivity(), ships, null));
            }
            getChantierRequest(token);
            shipsListView.setOnItemClickListener(this);
        }

    private void getChantierRequest(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getResources().getString(getResources().getIdentifier("base_url", "string", getActivity().getPackageName())))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getShipsResponse> request = service.getFleetList(token);
        request.enqueue(new Callback<getShipsResponse>() {
            @Override
            public void onResponse(Call<getShipsResponse> call, Response<getShipsResponse> response) {
                if (response.code() == 200) {
                    ships = response.body().getShips();
                    shipsListView.setAdapter(new ShipsArrayAdapter(getActivity(), ships, null));
                    ((ChantierActivity)getActivity()).updateView(ships.get(0), true);

                } else {
                    Tools.showToast((ChantierActivity)getActivity(), "Cannot get fleet for this user");
                }
            }

            @Override
            public void onFailure(Call<getShipsResponse> call, Throwable t) {
                Tools.showToast((ChantierActivity)getActivity(), "Network error");
            }
        });
    }

    public List<Ship> getShips() {
        return ships;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((ChantierActivity)getActivity()).updateView(ships.get(position), false);

    }
}
