package olga.maslova.outerspacemanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.ResponseRetroFit.getReportResponse;
import olga.maslova.outerspacemanager.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowReportActivity extends AppCompatActivity {
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);
        token = Tools.getToken(getApplicationContext());
        getReportRequest();
    }

    private void getReportRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getReportResponse> request = service.getReport(0,5, token);
        request.enqueue(new Callback<getReportResponse>() {
            @Override
            public void onResponse(Call<getReportResponse> call, Response<getReportResponse> response) {
                if (response.code() == 200) {


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
}
