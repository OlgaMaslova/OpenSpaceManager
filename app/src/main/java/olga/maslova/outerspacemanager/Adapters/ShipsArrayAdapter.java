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
        Ship currentShip = values.get(position);
        shipName.setText(currentShip.getName());
        if(currentShip.getAmount() != null) {
            shipAmount.setText(currentShip.getAmount().toString());
        } else {
            shipAmount.setText("");
        }
        return rowView;
    }
}
