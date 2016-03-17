package ui.activities;

import android.content.Intent;
import android.os.Bundle;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mSession.isActive(this)) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else {
            startActivity(new Intent(this, ConnectActivity.class));
        }
    }
}
