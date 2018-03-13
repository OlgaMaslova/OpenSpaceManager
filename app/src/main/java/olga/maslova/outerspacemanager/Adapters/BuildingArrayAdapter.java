package olga.maslova.outerspacemanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import olga.maslova.outerspacemanager.Building;
import olga.maslova.outerspacemanager.R;

/**
 * Created by omaslova on 23/01/2018.
 */

public class BuildingArrayAdapter extends ArrayAdapter {

    private final Context context;
    private final List<Building> values;

    public BuildingArrayAdapter(@NonNull Context context, @NonNull List<Building> buildings) {
        super(context, R.layout.row_building, buildings);
        this.values = buildings;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_building, parent, false);
        TextView buildingName = (TextView) rowView.findViewById(R.id.buidingTitle);
        TextView buildingDetails = (TextView) rowView.findViewById(R.id.buildingDesc);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        Building currentBuilding = values.get(position);
        buildingName.setText(currentBuilding.getName());
        buildingDetails.setText("You have : " + currentBuilding.getLevel());
        String name = currentBuilding.getName();
        String imageURL = currentBuilding.getImageUrl();
        Glide.with(this.context).load(imageURL).into(imageView);

        return rowView;
    }

}
