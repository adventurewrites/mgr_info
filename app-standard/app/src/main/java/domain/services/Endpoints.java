package domain.services;

import com.google.gson.JsonObject;

import domain.models.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface Endpoints {
    @Headers({ConfigEndpoints.HEADER_APP_ID, ConfigEndpoints.HEADER_REST_API_KEY})
    @GET("/login")
    void login(@Query("username") String username, @Query("password") String password, Callback<JsonObject> response);

    @Headers({ConfigEndpoints.HEADER_APP_ID, ConfigEndpoints.HEADER_REST_API_KEY})
    @POST("/users")
    void singUp(@Body User user, Callback<JsonObject> response);

    @Headers({ConfigEndpoints.HEADER_APP_ID, ConfigEndpoints.HEADER_REST_API_KEY})
    @GET("/users/me")
    void currentSession(@Header("X-Parse-Session-Token") String token, Callback<User> response);

    @Headers({ConfigEndpoints.HEADER_APP_ID, ConfigEndpoints.HEADER_REST_API_KEY})
    @PUT("/users/{objectId}")
    void update(@Header("X-Parse-Session-Token") String token, @Path("objectId") String objectId,
                @Body User user, Callback<JsonObject> response);
}
