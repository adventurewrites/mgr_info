package ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import domain.services.DaggerSessionComponent;
import domain.services.Session;
import domain.services.SessionComponent;

public abstract class BaseFragment extends Fragment {
    @Inject Session mSession;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpGraph();
    }

    private void setUpGraph() {
        SessionComponent component = DaggerSessionComponent.create();
        component.injectClient(this);
    }
}
