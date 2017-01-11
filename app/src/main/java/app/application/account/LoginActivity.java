package app.application.account;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.TypefaceProvider;

import app.application.custom.CustomBootstrapColors;
import app.application.R;
import app.application.util.TextValidator;
import app.application.drawer.DrawerActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity allowing a user to login.
 */
public class LoginActivity extends DrawerActivity implements View.OnClickListener {


    boolean validUsername, validPassword = false;
    @BindView(R.id.login_label)
    TextView loginLabel;
    @BindView(R.id.login_fb_btn)
    BootstrapButton fbButton;
    @BindView(R.id.login_twt_btn)
    BootstrapButton twtButton;
    @BindView(R.id.login_username_label)
    TextView loginUsernameLabel;
    @BindView(R.id.login_username_input)
    BootstrapEditText username;
    @BindView(R.id.login_password_label)
    TextView loginPasswordLabel;
    @BindView(R.id.login_password_input)
    BootstrapEditText password;
    @BindView(R.id.login_remember_cb)
    CheckBox loginRememberCb;
    @BindView(R.id.login_submit_btn)
    BootstrapButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initializeLayout();
    }

    private void initializeLayout() {
        TypefaceProvider.registerDefaultIconSets();
        fbButton.setBootstrapBrand(new CustomBootstrapColors(R.color.facebook));
        twtButton.setBootstrapBrand(new CustomBootstrapColors(R.color.twitter));
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        validateUsername();
        validatePassword();
        validate();
        if (validUsername && validPassword) {
            Toast.makeText(getApplicationContext(), "Valid", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT);
        }
    }

    public void validate() {
        username.addTextChangedListener(new TextValidator(username) {
            @Override
            public void validate(TextView textView, String text) {
                validateUsername();
            }
        });

        password.addTextChangedListener(new TextValidator(password) {
            @Override
            public void validate(TextView textView, String text) {
                validatePassword();
            }
        });
    }

    public void validateUsername() {
        validUsername = false;
        if (username.getText().length() == 0) {
            username.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            username.setError("Enter a Username");
        } else if (username.getText().length() < 6) {
            username.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            username.setError("Username too short");
        } else if (username.getText().length() > 20) {
            username.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            username.setError("Username too long");
        } else {
            username.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            validUsername = true;
        }
    }

    public void validatePassword() {
        validPassword = false;
        if (password.getText().length() == 0) {
            password.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            password.setError("Enter a Password");
        } else if (password.getText().length() < 6) {
            password.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            password.setError("Password too short");
        } else if (password.getText().length() > 20) {
            password.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            password.setError("Password too long");
        } else {
            password.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            validPassword = true;
        }
    }
}
