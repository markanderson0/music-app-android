package app.application.tickets.ticketssearch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import app.application.R;
import app.application.tickets.ticketssearch.data.model.TicketsSearchModel;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Assigns the ticket data to the tickets_search_row view
 */
public class TicketsSearchGridViewAdapter extends ArrayAdapter<TicketsSearchModel> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<TicketsSearchModel> gridData = new ArrayList<TicketsSearchModel>();
    private GridViewWithHeaderAndFooter gridView;
    View row;
    ViewHolder holder;
    int scrollPosition;

    public TicketsSearchGridViewAdapter(Context context, int layoutResourceId, ArrayList<TicketsSearchModel> gridData, GridViewWithHeaderAndFooter gridView) {
        super(context, layoutResourceId, gridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.gridData = gridData;
        this.gridView = gridView;
        this.scrollPosition = gridView.getFirstVisiblePosition();
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param gridData list of tickets
     */
    public void setGridData(ArrayList<TicketsSearchModel> gridData) {
        this.gridData = gridData;
        notifyDataSetChanged();
    }

    public ArrayList<TicketsSearchModel> getGridData() {
        return gridData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.artistLabel = (TextView) row.findViewById(R.id.artist_name);
            holder.venueLabel = (TextView) row.findViewById(R.id.ticket_venue);
            holder.dateLabel = (TextView) row.findViewById(R.id.ticket_date);
            holder.locationLabel = (TextView) row.findViewById(R.id.ticket_location);

            Typeface bold = Typeface.createFromAsset(getContext().getAssets(), "SourceSansPro-Bold.ttf");
            Typeface light = Typeface.createFromAsset(getContext().getAssets(), "SourceSansPro-Light.ttf");
            holder.artistLabel.setTypeface(bold);
            holder.dateLabel.setTypeface(light);

            holder.gridView = this.gridView;
            holder.gridView.setSelection(scrollPosition);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        TicketsSearchModel item = gridData.get(position);
        holder.artistLabel.setText(item.getName());
        holder.venueLabel.setText(item.getVenue());
        holder.dateLabel.setText(item.getDate());
        holder.locationLabel.setText(item.getCity() + "," + item.getState() + " " + item.getCountry());
        return row;
    }

    static class ViewHolder {
        TextView artistLabel;
        TextView venueLabel;
        TextView dateLabel;
        TextView locationLabel;
        GridView gridView;
    }
}
