package domain.services;

import android.content.Context;
import android.preference.PreferenceManager;

public class Persistence {
    private static final String KEY_OBJECT_ID = "key_object_id";
    private static final String KEY_TOKEN = "key_token";

    public void saveCredentials(Context context, String objectId, String token) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_OBJECT_ID, objectId)
                .putString(KEY_TOKEN, token)
                .commit();
    }

    public void removeCredentials(Context context) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_OBJECT_ID, "")
                .putString(KEY_TOKEN, "")
                .commit();
    }

    public String retrieveToken(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(KEY_TOKEN, "");
    }

    public String retrieveObjectId(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(KEY_OBJECT_ID, "");
    }
}