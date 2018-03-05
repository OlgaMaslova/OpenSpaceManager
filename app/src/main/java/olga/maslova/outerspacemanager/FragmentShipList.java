package olga.maslova.outerspacemanager;

import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import olga.maslova.outerspacemanager.Activities.ChantierActivity;
import olga.maslova.outerspacemanager.ResponseRetroFit.getShipsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentShipList extends Fragment {
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
                shipsListView.setAdapter(new ShipsArrayAdapter(getActivity(), ships));
            }
            getChantierRequest(token);
            shipsListView.setOnItemClickListener((ChantierActivity)getActivity());
        }

    private void getChantierRequest(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getShipsResponse> request = service.getFleetList(token);
        request.enqueue(new Callback<getShipsResponse>() {
            @Override
            public void onResponse(Call<getShipsResponse> call, Response<getShipsResponse> response) {
                if (response.code() == 200) {
                    ships = response.body().getShips();
                    shipsListView.setAdapter(new ShipsArrayAdapter(getActivity(), ships));
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
}
