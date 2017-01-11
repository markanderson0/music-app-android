package app.application.merch;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.application.R;
import app.application.merch.data.models.MerchItem;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Assigns the merch data to the merch_grid_item view
 */
public class MerchGridViewAdapter extends ArrayAdapter<MerchItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<MerchItem> gridData = new ArrayList<MerchItem>();
    private GridViewWithHeaderAndFooter gridView;
    ViewHolder holder;
    private int width;
    private double columns;
    private int scrollPosition;

    public MerchGridViewAdapter(Context context, int layoutResourceId, ArrayList<MerchItem> gridData, GridViewWithHeaderAndFooter gridView) {
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
     * @param gridData list of merch items
     */
    public void setGridData(ArrayList<MerchItem> gridData) {
        this.gridData = gridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.priceTextView = (TextView) row.findViewById(R.id.merch_grid_item_price);
            holder.nameTextView = (TextView) row.findViewById(R.id.merch_grid_item_name);
            holder.imageView = (ImageView) row.findViewById(R.id.merch_grid_item_image);
            holder.gridView = this.gridView;
            holder.gridView.setSelection(scrollPosition);
            setGridWidths();
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        MerchItem item = gridData.get(position);
        holder.priceTextView.setText(Html.fromHtml(item.getPrice()));
        holder.nameTextView.setText(Html.fromHtml(item.getName()));

        Picasso.with(context).load(item.getImage()).fit().into(holder.imageView);

        return row;
    }

    public int getWidth(){
        Configuration configuration = context.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        double screenWidthDpCalc = (((screenWidthDp / 110.00) % 1) * 10);
        if(screenWidthDpCalc  > 5 || screenWidthDpCalc == 0) {
            columns = Math.ceil(screenWidthDp / 110.00);
            width = (screenWidthDp - (5 * (int) columns)) / (int) columns;
            return width;
        } else {
            columns = Math.floor(screenWidthDp / 110.00);
            width = (screenWidthDp - (5 * (int) columns)) / (int) columns;
            return width;
        }
    }

    private void setGridWidths(){
        holder.imageView.setAdjustViewBounds(true);
        holder.gridView.setNumColumns((int) Math.ceil(columns / 1.5));
        holder.gridView.setColumnWidth(width * 3);
        holder.imageView.setMaxWidth(width * 3);
        holder.priceTextView.setMaxWidth(width * 3);
        holder.nameTextView.setMaxWidth(width * 3);
    }

    static class ViewHolder {
        TextView priceTextView;
        TextView nameTextView;
        ImageView imageView;
        GridView gridView;
    }
}
