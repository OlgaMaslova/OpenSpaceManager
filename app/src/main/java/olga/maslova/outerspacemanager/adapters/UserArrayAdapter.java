package olga.maslova.outerspacemanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.User;

/**
 * Created by omaslova on 13/03/2018.
 */

public class UserArrayAdapter extends ArrayAdapter {
    private final Context context;
    private final List<User> values;

    public UserArrayAdapter(@NonNull Context context, @NonNull List<User> users) {
        super(context, R.layout.row_user, users);
        this.values = users;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_user, parent, false);
        TextView userName = (TextView) rowView.findViewById(R.id.playerName);
        TextView userScore = (TextView) rowView.findViewById(R.id.playerScore);
        User currentUser = values.get(position);
        userName.setText(currentUser.getUsername());
        userScore.setText(currentUser.getPoints());
        return rowView;
    }
}
