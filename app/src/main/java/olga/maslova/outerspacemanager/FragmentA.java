package olga.maslova.outerspacemanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by omaslova on 27/02/2018.
 */

public class FragmentA extends Fragment {
        private ListView shipsListView;
        private List<Ship> ships;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_a,container);
            shipsListView = (ListView)v.findViewById(R.id.shipsListView);
            return v;
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            shipsListView.setAdapter(new ShipsArrayAdapter(getActivity(), ships));
            shipsListView.setOnItemClickListener((ChantierActivity)getActivity());
        }
}
