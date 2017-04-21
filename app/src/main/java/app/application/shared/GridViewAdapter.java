package app.application.shared;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import app.application.R;
import butterknife.ButterKnife;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Assigns the grid data to the grid item view
 */
public class GridViewAdapter extends ArrayAdapter<GridItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<GridItem> gridData = new ArrayList<GridItem>();
    private GridViewWithHeaderAndFooter gridView;
    View row;
    ViewHolder holder;
    private int width;
    private double columns;
    private boolean tablet;
    private int scrollPosition;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<GridItem> gridData, GridViewWithHeaderAndFooter gridView) {
        super(context, layoutResourceId, gridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.gridData = gridData;
        this.gridView = gridView;
        this.width = getWidth();
        this.scrollPosition = gridView.getFirstVisiblePosition();
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param gridData list of {@link GridItem}s
     */
    public void setGridData(ArrayList<GridItem> gridData) {
        this.gridData = gridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = ButterKnife.findById(row, R.id.grid_item_title);
            holder.imageView = ButterKnife.findById(row, R.id.grid_item_image);
            holder.gridView = this.gridView;
            holder.gridView.setSelection(scrollPosition);
            setGridWidths();
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        GridItem item = gridData.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));
        if (item.getImage().contains("img/")) {
            try {
                InputStream ims = getContext().getAssets().open(item.getImage());
                Drawable d = Drawable.createFromStream(ims, null);
                holder.imageView.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(context).load(item.getImage()).fit().into(holder.imageView);
        }
        return row;
    }

    private void setGridWidths() {
        if (tablet) {
            holder.imageView.setAdjustViewBounds(true);
            holder.gridView.setColumnWidth(width * 2);
            holder.imageView.getLayoutParams().height = 400;
            holder.imageView.getLayoutParams().width = (width * 2);
            holder.imageView.setMaxWidth(width * 2);
            holder.titleTextView.setMaxWidth(width * 2);
        } else {
            holder.imageView.setAdjustViewBounds(true);
            holder.gridView.setNumColumns((int) columns);
            holder.gridView.setColumnWidth(width * 2);
            holder.imageView.setMaxWidth(width * 2);
            holder.titleTextView.setMaxWidth(width * 3);
        }
    }

    private int getWidth() {
        Configuration configuration = context.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        columns = Math.ceil(screenWidthDp / 210.00);
        if (columns > 5) {
            tablet = true;
            columns = Math.ceil(screenWidthDp / 400.00);
            return (screenWidthDp - (5 * (int) columns)) / (int) columns;
        } else {
            tablet = false;
            return (screenWidthDp - (5 * (int) columns)) / (int) columns;
        }
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
        GridView gridView;
    }
}
