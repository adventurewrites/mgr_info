import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import domain.models.User;
import domain.services.Endpoints;
import domain.services.Persistence;
import domain.services.Session;
import retrofit.Callback;
import retrofit.RetrofitError;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionTest {
    @Mock private Endpoints mEndpoints;
    @Mock private Persistence mPersistence;
    @Mock private Context mContext;
    @Captor private ArgumentCaptor<Callback<JsonObject>> mCallbackJsonCaptor;
    @Captor private ArgumentCaptor<Callback<User>> mCallbackUserCaptor;
    private Session mSession;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        mSession = new Session(mEndpoints, mPersistence);
    }

    @Test public void loginSuccess()  {
        mSession.login(mContext, null, null, new Session.Callback<String>() {
            @Override
            public void response(boolean success, String feedback) {
                assertThat("Login should be successful", success);
                verify(mPersistence).saveCredentials(isA(Context.class), anyString(), anyString());
            }
        });

        verify(mEndpoints).login(anyString(), anyString(), mCallbackJsonCaptor.capture());
        mCallbackJsonCaptor.getValue().success(successResponse(), null);
    }

    @Test public void loginFailure()  {
        mSession.login(mContext, null, null, new Session.Callback<String>() {
            @Override
            public void response(boolean success, String feedback) {
                assertThat("Login should not be successful", !success);
                verify(mPersistence, never()).saveCredentials(isA(Context.class), anyString(), anyString());
            }
        });

        verify(mEndpoints).login(anyString(), anyString(), mCallbackJsonCaptor.capture());
        mCallbackJsonCaptor.getValue().failure(failureResponse());
        mCallbackJsonCaptor.getValue().failure(mock(RetrofitError.class));
    }

    @Test public void signUpSuccess()  {
        mSession.signUp(mContext, null, null, null, null, new Session.Callback<String>() {
            @Override
            public void response(boolean success, String feedback) {
                assertThat("Sign up should be successful", success);
                verify(mPersistence).saveCredentials(isA(Context.class), anyString(), anyString());
            }
        });

        verify(mEndpoints).singUp(any(User.class), mCallbackJsonCaptor.capture());
        mCallbackJsonCaptor.getValue().success(successResponse(), null);
    }

    @Test public void signUpFailure()  {
        mSession.signUp(mContext, null, null, null, null, new Session.Callback<String>() {
            @Override
            public void response(boolean success, String feedback) {
                assertThat("Sign up should not be successful", !success);
                verify(mPersistence, never()).saveCredentials(isA(Context.class), anyString(), anyString());
            }
        });

        verify(mEndpoints).singUp(any(User.class), mCallbackJsonCaptor.capture());
        mCallbackJsonCaptor.getValue().failure(failureResponse());
        mCallbackJsonCaptor.getValue().failure(mock(RetrofitError.class));
    }

    @Test public void currentSessionSuccess()  {
        mSession.currentSession(mContext, new Session.Callback<User>() {
            @Override
            public void response(boolean success, User feedback) {
                assertThat("CurrentSession should be successful", success);
            }
        });

        verify(mEndpoints).currentSession(anyString(), mCallbackUserCaptor.capture());
        mCallbackUserCaptor.getValue().success(mock(User.class), null);
    }

    @Test public void currentSessionFailure()  {
        mSession.currentSession(mContext, new Session.Callback<User>() {
            @Override
            public void response(boolean success, User feedback) {
                assertThat("CurrentSession should not be successful", !success);

            }
        });
        verify(mEndpoints).currentSession(anyString(), mCallbackUserCaptor.capture());
        mCallbackUserCaptor.getValue().failure(failureResponse());
        mCallbackUserCaptor.getValue().failure(null);
    }

    private static JsonObject successResponse() {
        JsonParser parser = new JsonParser();
        return  (JsonObject) parser.parse("{'sessionToken':'dummyST', 'objectId': 'dummyOI'}");
    }

    private static RetrofitError failureResponse() {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse("{'error':'dummyE'}");

        RetrofitError error = mock(RetrofitError.class);
        when(error.getBodyAs(JsonObject.class)).thenReturn(json);

        return error;
    }
}

