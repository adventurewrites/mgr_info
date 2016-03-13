package ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import domain.models.User;
import domain.services.Session;
import edu.victoralbertos.restapiparseauthandroid.R;
import ui.activities.LaunchActivity;

public class ProfileFragment extends BaseFragment {
    private View bt_update, bt_logout, pb_loading;
    private EditText et_username, et_email, et_phone;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        pb_loading = root.findViewById(R.id.pb_loading);
        bt_update = root.findViewById(R.id.bt_update);
        bt_logout = root.findViewById(R.id.bt_logout);

        et_username = (EditText) root.findViewById(R.id.et_username);
        et_email = (EditText) root.findViewById(R.id.et_email);
        et_phone = (EditText) root.findViewById(R.id.et_phone);

        return root;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        populateUser();
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
    }

    private void populateUser() {
        pb_loading.setVisibility(View.VISIBLE);
        bt_update.setVisibility(View.INVISIBLE);

        mSession.currentSession(getActivity(), new Session.Callback<User>() {
            @Override public void response(boolean success, User user) {
                pb_loading.setVisibility(View.INVISIBLE);
                bt_update.setVisibility(View.VISIBLE);

                if (!success || user == null) {
                    Toast.makeText(getActivity(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                    return;
                }

                et_username.setText(user.getUsername());
                et_email.setText(user.getEmail());
                et_phone.setText(user.getPhone());
            }
        });

    }

    private void updateUser() {
        pb_loading.setVisibility(View.VISIBLE);
        bt_update.setVisibility(View.INVISIBLE);

        String email = et_email.getText().toString();
        String username = et_username.getText().toString();
        String phone = et_phone.getText().toString();

        mSession.update(getActivity(), email, username, phone, new Session.Callback<String>() {
            @Override public void response(boolean success, String feedback) {
                pb_loading.setVisibility(View.INVISIBLE);
                bt_update.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), feedback, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logOut() {
        mSession.destroy(getActivity());
        startActivity(new Intent(getActivity(), LaunchActivity.class));
    }
}
