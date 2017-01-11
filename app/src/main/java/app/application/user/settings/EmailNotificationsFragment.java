package app.application.user.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.beardedhen.androidbootstrap.BootstrapButton;

import app.application.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that allows a user to edit their email notification settings.
 */
public class EmailNotificationsFragment extends Fragment {

    @BindView(R.id.notification_1_cb)
    CheckBox notification1;
    @BindView(R.id.notification_2_cb)
    CheckBox notification2;
    @BindView(R.id.notification_submit_btn)
    BootstrapButton submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.email_notifications, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.notification_submit_btn)
    public void onClick(View v) {

    }
}
