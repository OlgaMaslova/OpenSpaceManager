package olga.maslova.outerspacemanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import olga.maslova.outerspacemanager.adapters.ResearchRecyclerViewAdapter;
import olga.maslova.outerspacemanager.OnSelectedIdListener;
import olga.maslova.outerspacemanager.OuterSpaceManagerService;
import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.Research;
import olga.maslova.outerspacemanager.responsesRetroFit.getResearchResponse;
import olga.maslova.outerspacemanager.responsesRetroFit.postResponse;
import olga.maslova.outerspacemanager.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResearchActivity extends AppCompatActivity implements OnSelectedIdListener {

    private String token;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Research> mResearches;
    private Research currentResearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);
        token = Tools.getToken(getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_research);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getResearchForUser();

    }

    private void getResearchForUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getResources().getString(getResources().getIdentifier("base_url", "string", getPackageName())))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<getResearchResponse> request = service.getResearch(this.token);
        request.enqueue(new Callback<getResearchResponse>() {
            @Override
            public void onResponse(Call<getResearchResponse> call, Response<getResearchResponse> response) {
                if (response.code() == 200) {
                    mResearches = response.body().getSearches();
                    mAdapter = new ResearchRecyclerViewAdapter(mResearches, ResearchActivity.this);
                    mRecyclerView.setAdapter(mAdapter);

                } else {
                    Tools.showToast(getApplicationContext(), "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<getResearchResponse> call, Throwable t) {
                Tools.showToast(getApplicationContext(), "Network error");
            }
        });

    }

    private void startResearchRequest(Integer searchID) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getResources().getString(getResources().getIdentifier("base_url", "string", getPackageName())))
                .build();

        OuterSpaceManagerService service = retrofit.create(OuterSpaceManagerService.class);
        Call<postResponse> request = service.createResearch(searchID, token);
        request.enqueue(new Callback<postResponse>() {
            @Override
            public void onResponse(Call<postResponse> call, Response<postResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getCode().equals("ok")) {
                        Tools.showToast(getApplicationContext(), "Started research!");
                    }
                } else {
                    Tools.showToast(getApplicationContext(), "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<postResponse> call, Throwable t) {
                Tools.showToast(getApplicationContext(), "Network error");
            }
        });
    }

    @Override
    public void onSelected(Integer id) {
        currentResearch = new Research();
        currentResearch.setSearchId(id);
        startResearchRequest(id);
    }
}
