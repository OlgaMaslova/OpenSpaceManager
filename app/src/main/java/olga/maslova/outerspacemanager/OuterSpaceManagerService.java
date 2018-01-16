package olga.maslova.outerspacemanager;

import retrofit2.*;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by omaslova on 16/01/2018.
 */

public interface OuterSpaceManagerService {
    @POST("auth/create")
    Call<authResponse> createUser(@Body User user);

    @GET("users/get")
    Call<getUserResponse> getUser(@Header("x-access-token") String token);
}
