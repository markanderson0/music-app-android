package app.application.user.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import app.application.custom.CustomBootstrapColors;
import app.application.R;
import app.application.util.TextValidator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that allows a user to change their password.
 */
public class ChangePasswordFragment extends Fragment {

    @BindView(R.id.password_old_input)
    BootstrapEditText oldPassword;
    @BindView(R.id.password_new_input)
    BootstrapEditText newPassword;
    @BindView(R.id.password_confirm_input)
    BootstrapEditText confirmPassword;
    @BindView(R.id.password_submit_btn)
    BootstrapButton submitButton;

    boolean validOldPassword, validNewPassword, validConfirmPassword = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.password_submit_btn)
    public void onClick(View v) {
        validateOldPassword();
        validateNewPassword();
        validateConfirmPassword();
        validate();
        if(validOldPassword && validNewPassword && validConfirmPassword){
            Log.i("Valid Form", "Valid Form");
        } else {
            Log.i("Invalid Form", "Invalid Form");
        }
    }

    public void validate() {
        oldPassword.addTextChangedListener(new TextValidator(newPassword) {
            @Override
            public void validate(TextView textView, String text) {
                validateOldPassword();
            }
        });

        newPassword.addTextChangedListener(new TextValidator(oldPassword) {
            @Override
            public void validate(TextView textView, String text) {
                validateNewPassword();
                validateConfirmPassword();
            }
        });

        confirmPassword.addTextChangedListener(new TextValidator(confirmPassword) {
            @Override
            public void validate(TextView textView, String text) {
                validateConfirmPassword();
            }
        });

    }

    public void validateOldPassword() {
        validOldPassword = false;
        if (oldPassword.getText().length() == 0) {
            oldPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            oldPassword.setError("Enter a Password");
        } else if (oldPassword.getText().length() < 6) {
            oldPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            oldPassword.setError("Password too short");
        } else if (oldPassword.getText().length() > 20) {
            oldPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            oldPassword.setError("Password too long");
        } else {
            oldPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            validOldPassword = true;
        }
    }

    public void validateNewPassword() {
        validNewPassword = false;
        if (newPassword.getText().length() == 0) {
            newPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            newPassword.setError("Enter a Password");
        } else if (newPassword.getText().length() < 6) {
            newPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            newPassword.setError("Password too short");
        } else if (newPassword.getText().length() > 20) {
            newPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            newPassword.setError("Password too long");
        } else {
            newPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            validNewPassword = true;
        }
    }

    public void validateConfirmPassword() {
        validConfirmPassword = false;
        if (!(confirmPassword.getText().toString().equals(newPassword.getText().toString()))) {
            confirmPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            confirmPassword.setError("Passwords dont match");
        } else {
            confirmPassword.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            confirmPassword.setError(null);
            validConfirmPassword = true;
        }
    }
}
