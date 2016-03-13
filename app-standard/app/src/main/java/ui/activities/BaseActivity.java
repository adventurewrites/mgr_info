package ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import domain.services.DaggerSessionComponent;
import domain.services.Session;
import domain.services.SessionComponent;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject Session mSession;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpGraph();
    }

    private void setUpGraph() {
        SessionComponent component = DaggerSessionComponent.create();
        component.injectClient(this);
    }
}
