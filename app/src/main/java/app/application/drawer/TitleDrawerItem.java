package app.application.drawer;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.mikepenz.materialdrawer.model.AbstractDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Typefaceable;

import java.util.List;

import app.application.R;

public class TitleDrawerItem extends AbstractDrawerItem<TitleDrawerItem, TitleDrawerItem.ViewHolder> implements Typefaceable<TitleDrawerItem> {

    protected Typeface typeface = null;

    @Override
    public ViewHolderFactory<TitleDrawerItem.ViewHolder> getFactory() {
        return new ItemFactory();
    }

    @Override
    public int getType() {
        return R.id.title_drawer_item;
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return R.layout.title_drawer_item;
    }

    @Override
    public void bindView(TitleDrawerItem.ViewHolder viewHolder, List payloads) {
        Context ctx = viewHolder.itemView.getContext();

        //set the identifier from the drawerItem here. It can be used to run tests
        viewHolder.itemView.setId(hashCode());
        viewHolder.title.setTypeface(getTypeface());

        //define how the divider should look like
        viewHolder.view.setClickable(true);
        viewHolder.view.setEnabled(false);
        viewHolder.view.setMinimumHeight(1);
        ViewCompat.setImportantForAccessibility(viewHolder.view,
                ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);

        //call the onPostBindView method to trigger post bind view actions (like the listener to modify the item if required)
        onPostBindView(this, viewHolder.itemView);
    }

    @Override
    public TitleDrawerItem withTypeface(Typeface typeface) {
        this.typeface = typeface;
        return (TitleDrawerItem) this;
    }

    @Override
    public Typeface getTypeface() {
        return typeface;
    }

    public static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private View divider;
        private TextView title;

        private ViewHolder(View view) {
            super(view);
            this.view = view;
            this.divider = view.findViewById(R.id.material_drawer_divider);
            this.title = (TextView) view.findViewById(R.id.title_drawer_item);
        }
    }
}