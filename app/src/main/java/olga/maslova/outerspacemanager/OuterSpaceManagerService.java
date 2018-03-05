package olga.maslova.outerspacemanager;

import java.util.HashMap;

import olga.maslova.outerspacemanager.ResponseRetroFit.authResponse;
import olga.maslova.outerspacemanager.ResponseRetroFit.getBuildingsResponse;
import olga.maslova.outerspacemanager.ResponseRetroFit.getShipsResponse;
import olga.maslova.outerspacemanager.ResponseRetroFit.getUserResponse;
import olga.maslova.outerspacemanager.ResponseRetroFit.postResponse;
import retrofit2.*;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by omaslova on 16/01/2018.
 */

public interface OuterSpaceManagerService {
    @POST("auth/create")
    Call<authResponse> createUser(@Body UserLogin user);

    @GET("users/get")
    Call<getUserResponse> getUser(@Header("x-access-token") String token);

    @POST("auth/login")
    Call<authResponse> singInUser(@Body UserLogin user);

    @GET("ships")
    Call<getShipsResponse> getFleetList(@Header("x-access-token") String token);

    @GET("buildings/list")
    Call<getBuildingsResponse> getBuildingsList(@Header("x-access-token") String token);

    @POST("buildings/create/{buildingId}")
    Call<postResponse> createBuilding(@Path("buildingId") Integer buildingID, @Header("x-access-token") String token);

    @POST("ships/create/{shipId}")
    Call<postResponse> createShip(@Path("shipId") Integer shipId, @Body HashMap<String, String> keyAmount, @Header("x-access-token") String token);
}
