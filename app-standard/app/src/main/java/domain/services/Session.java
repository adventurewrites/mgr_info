package domain.services;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import javax.inject.Inject;

import domain.models.User;
import edu.victoralbertos.restapiparseauthandroid.R;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Session {
    private final Endpoints mEndpoints;
    private final Persistence mPersistence;

    @Inject public Session(Endpoints endpoints, Persistence persistence) {
        mEndpoints = endpoints;
        mPersistence = persistence;
    }

    public void login(final Context context, String username, String password, final Callback<String> callback) {
        mEndpoints.login(username, password, new retrofit.Callback<JsonObject>() {
            @Override public void success(JsonObject jsonObject, Response response) {
                handleSuccessConnection(context, jsonObject, callback);
            }

            @Override public void failure(RetrofitError error) {
                handleCommonFailure(context, error, callback);
            }
        });
    }

    public void signUp(final Context context, String email, String username, String phone, String password, final Callback<String> callback) {
        User user = new User(username, email, phone, password);
        mEndpoints.singUp(user, new retrofit.Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                handleSuccessConnection(context, jsonObject, callback);
            }

            @Override
            public void failure(RetrofitError error) {
                handleCommonFailure(context, error, callback);
            }
        });
    }

    public void update(final Context context, String email, String username, String phone, final Callback<String> callback) {
        String token = mPersistence.retrieveToken(context);
        String objectId = mPersistence.retrieveObjectId(context);

        User user = new User(username, email, phone);
        mEndpoints.update(token, objectId, user, new retrofit.Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                String successMessage = context.getString(R.string.success);
                callback.response(true, successMessage);
            }

            @Override
            public void failure(RetrofitError error) {
                handleCommonFailure(context, error, callback);
            }
        });
    }

    @Nullable public void currentSession(final Context context, final Callback<User> callback) {
        String token = mPersistence.retrieveToken(context);
        mEndpoints.currentSession(token, new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                callback.response(user != null, user);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.response(false, null);
            }
        });
    }


    public boolean isActive(Context context) {
        return !mPersistence.retrieveObjectId(context).isEmpty();
    }

    public void destroy(Context context) {
        mPersistence.removeCredentials(context);
    }

    private void handleSuccessConnection(Context context, JsonObject jsonObject, Callback<String> callback) {
        if (!jsonObject.has("sessionToken")) {
            callback.response(false, context.getString(R.string.failure));
        }

        String token = jsonObject.get("sessionToken").getAsString();
        String objectId = jsonObject.get("objectId").getAsString();
        mPersistence.saveCredentials(context, objectId, token);

        String successMessage = context.getString(R.string.success);
        callback.response(true, successMessage);
    }

    private void handleCommonFailure(Context context, RetrofitError error, Callback<String> callback) {
        if (error.getBodyAs(JsonObject.class) == null) {
            callback.response(false, context.getString(R.string.failure));
            return;
        }

        JsonObject jsonObject = (JsonObject) error.getBodyAs(JsonObject.class);

        if (jsonObject != null && jsonObject.has("error")) {
            String message = jsonObject.get("error").getAsString();
            callback.response(false, message);
            return;
        }

        callback.response(false, context.getString(R.string.failure));
    }

    public interface Callback<T> {
        void response(boolean success, T feedback);
    }
}
