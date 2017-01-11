package app.application.user.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import static app.application.R.id.profile_submit_btn;

/**
 * Fragment that allows a user to edit their email and date of birth.
 */
public class EditProfileFragment extends Fragment {

    @BindView(R.id.profile_email_input)
    BootstrapEditText email;
    @BindView(R.id.profile_day_input)
    BootstrapEditText day;
    @BindView(R.id.profile_month_input)
    BootstrapEditText month;
    @BindView(R.id.profile_year_input)
    BootstrapEditText year;
    @BindView(profile_submit_btn)
    BootstrapButton submitButton;

    boolean validEmail, validDOB = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(profile_submit_btn)
    public void onClick(View v) {
        validateEmail();
        validateDOB();
        validate();
        if(validEmail && validDOB){
            Log.i("Valid Form", "Valid Form");
        } else {
            Log.i("Invalid Form", "Invalid Form");
        }
    }

    public void validate() {
        email.addTextChangedListener(new TextValidator(email) {
            @Override
            public void validate(TextView textView, String text) {
                validateEmail();
            }
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
    }

    public void validateEmail() {
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

    public void validateDOB() {
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
}
