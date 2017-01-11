package app.application.account;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import app.application.custom.CustomBootstrapColors;
import app.application.R;
import app.application.util.TextValidator;
import app.application.drawer.DrawerActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity allowing a user to signup.
 */
public class SignupActivity extends DrawerActivity implements View.OnClickListener {

    @BindView(R.id.signup_fb_btn)
    BootstrapButton facebookButton;
    @BindView(R.id.signup_twt_btn)
    BootstrapButton twitterButton;
    @BindView(R.id.signup_submit_btn)
    BootstrapButton submitButton;
    @BindView(R.id.signup_username_input)
    BootstrapEditText username;
    @BindView(R.id.signup_email_input)
    BootstrapEditText email;
    @BindView(R.id.signup_password_input)
    BootstrapEditText password;
    @BindView(R.id.signup_day_input)
    BootstrapEditText day;
    @BindView(R.id.signup_month_input)
    BootstrapEditText month;
    @BindView(R.id.signup_year_input)
    BootstrapEditText year;
    @BindView(R.id.signup_gender)
    RadioGroup gender;
    @BindView(R.id.signup_female_input)
    RadioButton genderFemale;
    @BindView(R.id.signup_male_input)
    RadioButton genderMale;
    @BindView(R.id.signup_terms_cb)
    CheckBox terms;

    private boolean validUsername, validEmail, validPassword, validGender, validDOB, validTerms = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        facebookButton.setBootstrapBrand(new CustomBootstrapColors(R.color.facebook));
        twitterButton.setBootstrapBrand(new CustomBootstrapColors(R.color.twitter));
    }

    @OnClick(R.id.signup_submit_btn)
    public void onClick(View v) {
        validateUsername();
        validateEmail();
        validatePassword();
        validateGender();
        validateDOB();
        validateTerms();
        validate();
        if(validUsername && validEmail && validPassword && validGender && validDOB && validTerms){
            Toast.makeText(getApplicationContext(), "Valid", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT);
        }
    }

    private void validate() {
        username.addTextChangedListener(new TextValidator(username) {
            @Override
            public void validate(TextView textView, String text) {
                validateUsername();
            }
        });

        email.addTextChangedListener(new TextValidator(email) {
            @Override
            public void validate(TextView textView, String text) {
                validateEmail();
            }
        });

        password.addTextChangedListener(new TextValidator(password) {
            @Override
            public void validate(TextView textView, String text) {
                validatePassword();
            }
        });

        genderFemale.setOnClickListener((View v) -> {
                validateGender();
        });

        genderMale.setOnClickListener((View v) -> {
                validateGender();
        });

        day.addTextChangedListener(new TextValidator(day) {
            @Override
            public void validate(TextView textView, String text) {
                validateDOB();
            }
        });

        month.addTextChangedListener(new TextValidator(month) {
            @Override
            public void validate(TextView textView, String text) {
                validateDOB();
            }
        });

        year.addTextChangedListener(new TextValidator(year) {
            @Override
            public void validate(TextView textView, String text) {
                validateDOB();
            }
        });

        terms.setOnClickListener((View v) -> {
                validateTerms();
        });
    }

    private void validateUsername() {
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

    private void validateEmail() {
        validEmail = false;
        if (email.getText().length() == 0) {
            email.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            email.setError("Enter an Email");
        } else if (!TextUtils.isEmpty(email.getText()) && !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            email.setError("Invalid Email");
        } else {
            email.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            validEmail = true;
        }
    }

    private void validatePassword() {
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

    private void validateGender() {
        validGender = false;
        if(!(genderFemale.isChecked() || genderMale.isChecked())) {
            genderMale.setError("Select a Gender");
            genderMale.setTextColor(Color.rgb(217, 83, 79));
            genderFemale.setTextColor(Color.rgb(217, 83, 79));
        } else {
            genderMale.setError(null);
            genderMale.setTextColor(Color.BLACK);
            genderFemale.setTextColor(Color.BLACK);
            validGender = true;
        }
    }

    private void validateDOB() {
        validDOB = false;
        //Invalid day
        if (day.getText().toString().isEmpty() || Integer.parseInt(day.getText().toString()) < 1 || Integer.parseInt(day.getText().toString()) > 31) {
            day.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            day.setError("Invalid Date");
        }
        //Invalid Month
        else if (month.getText().toString().isEmpty() || Integer.parseInt(month.getText().toString()) < 1 || Integer.parseInt(month.getText().toString()) > 12) {
            month.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            month.setError("Invalid Date");
        }
        //Invalid Year
        else if (year.getText().toString().isEmpty() || Integer.parseInt(year.getText().toString()) < 1917 || Integer.parseInt(year.getText().toString()) > 2015) {
            year.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            year.setError("Invalid Date");
        }
        //Leap year
        else if (Integer.parseInt(year.getText().toString()) % 4 != 0 &&
                 Integer.parseInt(month.getText().toString()) == 2 &&
                 Integer.parseInt(day.getText().toString()) > 28) {
            day.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            day.setError("Invalid Date");
            month.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            month.setError("Invalid Date");
            year.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            year.setError("Invalid Date");
        }
        //Invalid Febuary
        else if (Integer.parseInt(month.getText().toString()) == 2 &&
                 Integer.parseInt(day.getText().toString()) > 29) {
            day.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            day.setError("Invalid Date");
            month.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            month.setError("Invalid Date");
        }
        //31st of April, June September, November
        else if ((Integer.parseInt(month.getText().toString()) == 4 ||
                  Integer.parseInt(month.getText().toString()) == 6 ||
                  Integer.parseInt(month.getText().toString()) == 9 ||
                  Integer.parseInt(month.getText().toString()) == 11) &&
                  Integer.parseInt(day.getText().toString()) > 30) {
            day.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            day.setError("Invalid Date");
            month.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            month.setError("Invalid Date");
        }
        //Valid :)
        else {
            day.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            day.setError(null);
            month.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            month.setError(null);
            year.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            year.setError(null);
            validDOB = true;
        }
    }

    private void validateTerms() {
        validTerms = false;
        if (!terms.isChecked()) {
            terms.setError("Terms must be agreed");
            terms.setTextColor(Color.rgb(217, 83, 79));
        } else {
            terms.setError(null);
            terms.setTextColor(Color.BLACK);
            validTerms = true;
        }
    }
}
