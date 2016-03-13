package ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import domain.services.Session;
import edu.victoralbertos.restapiparseauthandroid.R;
import ui.activities.ProfileActivity;

public class LoginFragment extends BaseFragment {
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.login_bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                performLogin(view);
            }
        });
    }

    public void performLogin(final View button) {
        final View pb_loading = getView().findViewById(R.id.login_pb_loading);
        pb_loading.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);

        String username = ((EditText) getView().findViewById(R.id.login_et_username)).getText().toString();
        String password = ((EditText) getView().findViewById(R.id.login_et_password)).getText().toString();

        mSession.login(getActivity(), username, password, new Session.Callback<String>() {
            @Override public void response(boolean success, String feedback) {
                pb_loading.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), feedback, Toast.LENGTH_SHORT).show();

                if (success) startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
    }
}
