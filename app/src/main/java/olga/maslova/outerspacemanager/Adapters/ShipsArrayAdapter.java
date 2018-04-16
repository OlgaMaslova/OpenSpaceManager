package olga.maslova.outerspacemanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.Ship;

public class ShipsArrayAdapter extends ArrayAdapter {
    private final Context context;
    private final List<Ship> values;

    public ShipsArrayAdapter(@NonNull Context context, @NonNull List<Ship> ships) {
        super(context, R.layout.row_ship, ships);
        this.values = ships;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_ship, parent, false);
        TextView shipName = (TextView) rowView.findViewById(R.id.shipName);
        TextView shipAmount = (TextView) rowView.findViewById(R.id.amountShip);
        ImageView shipImage = (ImageView) rowView.findViewById(R.id.shipIcon);
        Ship currentShip = values.get(position);
        shipName.setText(currentShip.getName());
        if(currentShip.getAmount() != null) {
            shipAmount.setText(currentShip.getAmount().toString());
        } else {
            shipAmount.setText("");
        }
        if (currentShip.getShipId() == 0) {
            shipImage.setImageResource(R.drawable.chasseur_leger);
        }
        if (currentShip.getShipId() == 1) {
            shipImage.setImageResource(R.drawable.chasseur_lourd);
        }
        if (currentShip.getShipId() == 2) {
            shipImage.setImageResource(R.drawable.spyship);
        }
        if (currentShip.getShipId() == 3) {
            shipImage.setImageResource(R.drawable.destroyer);
        }
        if (currentShip.getShipId() == 4) {
            shipImage.setImageResource(R.drawable.death_star);
        }
        return rowView;
    }
}
