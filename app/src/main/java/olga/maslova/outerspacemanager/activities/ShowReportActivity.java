package olga.maslova.outerspacemanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.responsesRetroFit.getReportResponse;
import olga.maslova.outerspacemanager.Ship;
import olga.maslova.outerspacemanager.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowReportActivity extends AppCompatActivity {
    private String token;
    private TextView gasWon, mineralsWon, userAttacked, attackingShipsTextView, survivedShipsTextView;
    private List<Ship> attackingShips, survivedShips;
    private Integer reportId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);
        reportId = (Integer) getIntent().getSerializableExtra("report number");
        token = Tools.getToken(getApplicationContext());
        gasWon = (TextView) findViewById(R.id.GasWon);
        mineralsWon = (TextView) findViewById(R.id.MineralsWon);
        userAttacked =  (TextView) findViewById(R.id.userAttacked);
        attackingShipsTextView =  (TextView) findViewById(R.id.AttackingShips);
        survivedShipsTextView =  (TextView) findViewById(R.id.SurvivedShips);
        getReportRequest();
    }

    private void getReportRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getResources().getString(getResources().getIdentifier("base_url", "string", getPackageName())))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        final Call<getReportResponse> request = service.getReport(reportId,1, token);
        request.enqueue(new Callback<getReportResponse>() {
            @Override
            public void onResponse(Call<getReportResponse> call, Response<getReportResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getReports().size() != 0) {
                        gasWon.setText("Gas won: " + String.format("%.2f", response.body().getReports().get(0).getGasWon()));
                        mineralsWon.setText("Minerals won: " +String.format("%.2f",response.body().getReports().get(0).getMineralsWon()));
                        userAttacked.setText("Attacker: " + response.body().getReports().get(0).getFrom() + "\n" + "User attacked: "+response.body().getReports().get(0).getTo());
                        attackingShips = response.body().getReports().get(0).getAttackerFleet();
                        survivedShips = response.body().getReports().get(0).getAttackerFleetAfterBattle().getFleet();
                        configureReportView(attackingShips, survivedShips);
                    } else {
                        Tools.showToast(getApplicationContext(), "You don't have reports yet!");
                    }

                } else {
                    Tools.showToast(getApplicationContext(), "Cannot get last report");
                }
            }

            @Override
            public void onFailure(Call<getReportResponse> call, Throwable t) {
                Tools.showToast(getApplicationContext(), "Network error");
            }
        });
    }

    void configureReportView(List<Ship> attackingShips,List<Ship> survivedShips) {
        String textForAttackingShips = "Attacker sent: \n";
        for (Ship ship:attackingShips) {
            textForAttackingShips += Integer.toString(ship.getAmount()) + " ";
            ship.setShipName(ship.getShipId());
            textForAttackingShips += ship.getName()  + "\n";
            //textForAttackingShips += " ";
        }
        attackingShipsTextView.setText(textForAttackingShips);
        String textForSurvivedShips = "Attacker's fleet after battle: \n";
        for (Ship survivedShip: survivedShips) {
            textForSurvivedShips += Integer.toString(survivedShip.getAmount()) + " ";
            textForSurvivedShips += survivedShip.getName() + "\n";
        }
        survivedShipsTextView.setText(textForSurvivedShips);


    }
}
