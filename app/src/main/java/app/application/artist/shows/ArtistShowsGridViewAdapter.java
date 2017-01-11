package app.application.artist.shows;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

import app.application.R;
import app.application.artist.shows.data.model.Setlist;
import butterknife.ButterKnife;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Assigns the shows data to the view
 */
public class ArtistShowsGridViewAdapter extends ArrayAdapter<Setlist> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Setlist> gridData = new ArrayList<Setlist>();
    private GridViewWithHeaderAndFooter gridView;
    View row;
    ViewHolder holder;
    int scrollPosition;

    public ArtistShowsGridViewAdapter(Context context, int layoutResourceId, ArrayList<Setlist> gridData, GridViewWithHeaderAndFooter gridView) {
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
     * @param gridData list of setlists
     */
    public void setGridData(ArrayList<Setlist> gridData) {
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
            holder.artistLabel = ButterKnife.findById(row, R.id.artist_name);
            holder.venueLabel = ButterKnife.findById(row, R.id.artist_shows_venue);
            holder.dateLabel = ButterKnife.findById(row, R.id.artist_shows_date);
            holder.locationLabel = ButterKnife.findById(row, R.id.artist_shows_location);
            holder.setlistButton = ButterKnife.findById(row, R.id.artist_setlist_btn);

            Typeface bold = Typeface.createFromAsset(getContext().getAssets(), "SourceSansPro-Bold.ttf");
            Typeface light = Typeface.createFromAsset(getContext().getAssets(), "SourceSansPro-Light.ttf");
            holder.artistLabel.setTypeface(bold);
            holder.dateLabel.setTypeface(light);

            holder.gridView = gridView;
            holder.gridView.setSelection(scrollPosition);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final Setlist setlist = gridData.get(position);
        holder.artistLabel.setText(setlist.getVenue().getName());
        holder.venueLabel.setText(setlist.getEventDate());
        holder.dateLabel.setText(setlist.getVenue().getCity().getName() + "," + setlist.getVenue().getCity().getStateCode() + " " + setlist.getVenue().getCity().getCountry().getName());
        holder.locationLabel.setText(setlist.getTour());
        holder.setlistButton.setText("Setlist");
        holder.setlistButton.setOnClickListener((View v) -> {
                LinearLayout setlistLayout = new LinearLayout(getContext());
                setlistLayout.setOrientation(LinearLayout.VERTICAL);
                ArrayList<String> songs = new ArrayList<>();
                if(setlist.getSets().getSet().get(0).getSong().size() > 0) {
                    for (int i = 0; i < setlist.getSets().getSet().get(0).getSong().size(); i++) {
                        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                        View trackView = layoutInflater.inflate(R.layout.artist_setlist_row, null);
                        TextView trackNum = ButterKnife.findById(trackView, R.id.track_num);
                        TextView songName = ButterKnife.findById(trackView, R.id.song_name);
                        trackNum.setText(String.valueOf(i+1) + ".");
                        songName.setText(setlist.getSets().getSet().get(0).getSong().get(i).getName());
                        setlistLayout.addView(trackView);
                        songs.add(setlist.getSets().getSet().get(0).getSong().get(i).getName());
                    }
                    new MaterialDialog.Builder(context)
                            .customView(setlistLayout, true)
                            .show();
                } else {
                    songs.add("No Setlist Available.");
                    new MaterialDialog.Builder(context)
                            .items(songs)
                            .itemsGravity(GravityEnum.CENTER)
                            .show();
                }
        });
        return row;
    }

    static class ViewHolder {
        TextView artistLabel;
        TextView venueLabel;
        TextView dateLabel;
        TextView locationLabel;
        BootstrapButton setlistButton;
        GridView gridView;
    }
}

