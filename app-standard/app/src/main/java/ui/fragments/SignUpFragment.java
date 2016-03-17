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

public class SignUpFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.sign_up_bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignUp(view);
            }
        });
    }

    public void performSignUp(final View button) {
        final View pb_loading = getView().findViewById(R.id.sign_up_pb_loading);
        pb_loading.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);

        String email = ((EditText) getView().findViewById(R.id.sign_up_et_email)).getText().toString();
        String username = ((EditText) getView().findViewById(R.id.sign_up_et_username)).getText().toString();
        String phone = ((EditText) getView().findViewById(R.id.sign_up_et_phone)).getText().toString();
        String password = ((EditText) getView().findViewById(R.id.sign_up_et_password)).getText().toString();

        mSession.signUp(getActivity(), email, username, phone, password, new Session.Callback<String>() {
            @Override
            public void response(boolean success, String feedback) {
                pb_loading.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), feedback, Toast.LENGTH_SHORT).show();

                if (success) startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
    }
}
