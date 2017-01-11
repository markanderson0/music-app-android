package app.application.drawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.mikepenz.materialdrawer.model.AbstractDrawerItem;

import java.util.List;

import app.application.R;
import app.application.search.SearchActivity;

public class SearchDrawerItem extends AbstractDrawerItem<SearchDrawerItem, SearchDrawerItem.ViewHolder> implements View.OnClickListener {

    Activity activity = null;
    String query;
    private BootstrapEditText searchInput;

    @Override
    public ViewHolderFactory<SearchDrawerItem.ViewHolder> getFactory() {
        return new ItemFactory();
    }

    @Override
    public int getType() {
        return R.id.search_drawer_item;
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return R.layout.search_drawer_item;
    }

    @Override
    public void bindView(SearchDrawerItem.ViewHolder viewHolder, List payloads) {
        Context ctx = viewHolder.itemView.getContext();

        //set the identifier from the drawerItem here. It can be used to run tests
        viewHolder.itemView.setId(hashCode());
        viewHolder.searchBtn.setOnClickListener(this);
        searchInput = viewHolder.searchInput;
        //define how the divider should look like
        viewHolder.view.setClickable(false);
        viewHolder.view.setEnabled(false);
        viewHolder.view.setMinimumHeight(1);
        ViewCompat.setImportantForAccessibility(viewHolder.view,
                ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
        
        //call the onPostBindView method to trigger post bind view actions (like the listener to modify the item if required)
        onPostBindView(this, viewHolder.itemView);
    }

    public SearchDrawerItem withActivity (Activity activity) {
        this.activity = activity;
        return (SearchDrawerItem) this;
    }

    public Activity getActivity() { return activity; }

    @Override
    public void onClick(View v) {
        Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
        searchIntent.putExtra("query", searchInput.getText().toString());
        getActivity().startActivity(searchIntent);
    }

    public static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private View divider;
        private BootstrapEditText searchInput;
        private BootstrapButton searchBtn;

        private ViewHolder(View view) {
            super(view);
            this.view = view;
            this.divider = view.findViewById(R.id.material_drawer_divider);
            this.searchInput = (BootstrapEditText) view.findViewById(R.id.search_input);
            this.searchBtn = (BootstrapButton) view.findViewById(R.id.search_btn);
        }
    }
}