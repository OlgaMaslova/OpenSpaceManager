package olga.maslova.outerspacemanager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentShip extends Fragment {
    private TextView shipName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup  container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ship,container);
        shipName = (TextView)v.findViewById(R.id.ShipTitle);
        return v;
    }
    public void fillTextView(Ship ship) {
        shipName.setText(ship.getName());
    }

}
