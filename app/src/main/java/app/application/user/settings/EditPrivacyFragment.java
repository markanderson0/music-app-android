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
 * Fragment that allows a user to edit their privacy settings.
 */
public class EditPrivacyFragment extends Fragment {

    @BindView(R.id.privacy_1_cb)
    CheckBox privacy1;
    @BindView(R.id.privacy_2_cb)
    CheckBox privacy2;
    @BindView(R.id.privacy_3_cb)
    CheckBox privacy3;
    @BindView(R.id.privacy_4_cb)
    CheckBox privacy4;
    @BindView(R.id.privacy_submit_btn)
    BootstrapButton submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_privacy, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.privacy_submit_btn)
    public void onClick(View v) {

    }
}
