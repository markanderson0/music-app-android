package app.application.drawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.mikepenz.materialdrawer.model.AbstractDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Typefaceable;

import java.util.List;

import app.application.R;
import app.application.account.LoginActivity;
import app.application.account.SignupActivity;

public class AccountDrawerItem extends AbstractDrawerItem<AccountDrawerItem, AccountDrawerItem.ViewHolder> implements Typefaceable<AccountDrawerItem>, View.OnClickListener {

    protected Typeface typeface = null;
    Activity activity = null;

    @Override
    public ViewHolderFactory<AccountDrawerItem.ViewHolder> getFactory() {
        return new ItemFactory();
    }

    @Override
    public int getType() {
        return R.id.account_drawer_item;
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return R.layout.account_drawer_item;
    }

    @Override
    public void bindView(AccountDrawerItem.ViewHolder viewHolder, List payloads) {
        Context ctx = viewHolder.itemView.getContext();

        //set the identifier from the drawerItem here. It can be used to run tests
        viewHolder.itemView.setId(hashCode());
        viewHolder.login.setTypeface(getTypeface());
        viewHolder.login.setOnClickListener(this);
        viewHolder.signup.setTypeface(getTypeface());
        viewHolder.signup.setOnClickListener(this);

        //define how the divider should look like
        viewHolder.view.setClickable(false);
        viewHolder.view.setEnabled(false);
        viewHolder.view.setMinimumHeight(1);
        ViewCompat.setImportantForAccessibility(viewHolder.view,
                ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);

        //call the onPostBindView method to trigger post bind view actions (like the listener to modify the item if required)
        onPostBindView(this, viewHolder.itemView);
    }

    public AccountDrawerItem withTypeface(Typeface typeface) {
        this.typeface = typeface;
        return (AccountDrawerItem) this;
    }

    @Override
    public Typeface getTypeface() {
        return typeface;
    }

    public AccountDrawerItem withActivity (Activity activity) {
        this.activity = activity;
        return (AccountDrawerItem) this;
    }

    public Activity getActivity() { return activity; }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.drawer_login:
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(loginIntent);
                break;
            case R.id.drawer_signup:
                Intent signupIntent = new Intent(getActivity(), SignupActivity.class);
                getActivity().startActivity(signupIntent);
                break;
        }
    }

    public static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private View divider;
        private BootstrapButton login;
        private BootstrapButton signup;

        private ViewHolder(View view) {
            super(view);
            this.view = view;
            this.divider = view.findViewById(R.id.material_drawer_divider);
            this.login = (BootstrapButton) view.findViewById(R.id.drawer_login);
            this.signup = (BootstrapButton) view.findViewById(R.id.drawer_signup);
        }
    }
}