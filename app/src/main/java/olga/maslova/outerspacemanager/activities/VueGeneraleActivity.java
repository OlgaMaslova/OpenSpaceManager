package olga.maslova.outerspacemanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.ViewGroup;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeImageTransform;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.Slide;


import java.util.List;
import java.util.stream.IntStream;

import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.Tools;
import olga.maslova.outerspacemanager.User;
import olga.maslova.outerspacemanager.responsesRetroFit.getReportResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VueGeneraleActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private TextView scoreView;
    private TextView userView;
    private TextView gasPoints;
    private TextView mineralPoints;
    private TextView reportsNumber;
    private User currentUser;
    private Button btnReports;
    private Spinner dropdown;
    private int reportNmb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_generale);
        String token = Tools.getToken(getApplicationContext());
        scoreView = (TextView) findViewById(R.id.ScoreID);
        userView = (TextView) findViewById(R.id.userView);
        gasPoints = (TextView) findViewById(R.id.gas_points_general);
        mineralPoints = (TextView) findViewById(R.id.mineral_points_general);
        reportsNumber = (TextView) findViewById(R.id.reports_number);
        currentUser = (User) getIntent().getSerializableExtra("CURRENT_USER");
        btnReports = (Button) findViewById(R.id.getReportBtn);
        dropdown = findViewById(R.id.spinner_report);
        dropdown.setOnItemSelectedListener(this);

        btnReports.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                       showReportActivity(reportNmb);
                    }
                }
        );



        userView.setText(currentUser.getUsername());
        scoreView.setText("Your score " + currentUser.getPoints());
        gasPoints.setText(currentUser.getGas());
        mineralPoints.setText(currentUser.getMinerals());
        getReportRequest(token);
    }

    private void getReportRequest(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getResources().getString(getResources().getIdentifier("base_url", "string", getPackageName())))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        final Call<getReportResponse> request = service.getReport(0,20, token);
        request.enqueue(new Callback<getReportResponse>() {
            @Override
            public void onResponse(Call<getReportResponse> call, Response<getReportResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getReports().size() != 0) {
                        reportsNumber.setText("Reports available: " + response.body().getSize());
                        String[] numbers = new String[response.body().getSize()];
                        for(Integer i = 1; i <=  response.body().getSize(); i++) {
                            numbers[i-1] = i.toString();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(VueGeneraleActivity.this, android.R.layout.simple_spinner_dropdown_item, numbers);
                        dropdown.setAdapter(adapter);
                    } else {
                        Tools.showToast(getApplicationContext(), "You don't have reports yet!");
                        reportsNumber.setText("Reports available: 0");
                    }
                } else {
                    Tools.showToast(getApplicationContext(), "Cannot get reports");
                    reportsNumber.setText("Reports available: 0");
                }
            }

            @Override
            public void onFailure(Call<getReportResponse> call, Throwable t) {
                Tools.showToast(getApplicationContext(), "Network error");
            }
        });
    }

    private void showReportActivity(Integer reportId){
        Intent myIntent = new Intent(getApplicationContext(),ShowReportActivity.class);
        myIntent.putExtra("report number", reportId);
        startActivityForResult(myIntent, 1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
       reportNmb = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
