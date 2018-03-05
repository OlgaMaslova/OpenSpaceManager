package olga.maslova.outerspacemanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.User;

public class VueGeneraleActivity extends AppCompatActivity {
    private ListView parametersList;
    private ListView valuesList;
    private TextView scoreView;
    private TextView userView;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_generale);
        valuesList = (ListView) findViewById(R.id.valuesID);
        scoreView = (TextView) findViewById(R.id.ScoreID);
        userView = (TextView) findViewById(R.id.userView);
        parametersList = (ListView) findViewById(R.id.parametersID);
        currentUser = (User) getIntent().getSerializableExtra("CURRENT_USER");
        List<String> parameters =  Arrays.asList("gas", "minerals");
        parametersList.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, parameters));
        List<Double> values = Arrays.asList(Double.parseDouble(currentUser.getGas()), Double.parseDouble(currentUser.getMinerals()));
        valuesList.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, values));
        userView.setText(currentUser.getUsername());
        scoreView.setText(currentUser.getPoints());

    }
}
