package olga.maslova.outerspacemanager;

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

}
