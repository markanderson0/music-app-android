package app.application.artist.videos;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import app.application.R;
import butterknife.ButterKnife;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class ArtistVideosModelViewHolder extends GroupViewHolder {

    private TextView location, date, venue;
    private ImageView arrow;
    private LinearLayout background;

    public ArtistVideosModelViewHolder(View itemView) {
        super(itemView);
        venue = ButterKnife.findById(itemView, R.id.artist_videos_show_venue);
        date = ButterKnife.findById(itemView, R.id.artist_videos_show_date);
        location = ButterKnife.findById(itemView, R.id.artist_videos_show_location);
        arrow = ButterKnife.findById(itemView, R.id.list_item_genre_arrow);
        background = ButterKnife.findById(itemView, R.id.artist_details_row);
        Typeface bold = Typeface.createFromAsset(itemView.getContext().getAssets(), "SourceSansPro-Bold.ttf");
        Typeface light = Typeface.createFromAsset(itemView.getContext().getAssets(), "SourceSansPro-Light.ttf");
        venue.setTypeface(bold);
        date.setTypeface(light);
    }

    public void setTitles(ExpandableGroup title) {
        String[] titles = title.getTitle().split(":");
        venue.setText(titles[1]);
        date.setText(titles[0]);
        location.setText(titles[2]);
    }

    @Override
    public void expand() {
        background.setBackgroundColor(Color.parseColor("#f0f0f0"));
        animateExpand();
    }

    @Override
    public void collapse() {
        background.setBackgroundColor(Color.WHITE);
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        AnimationSet animationSet = new AnimationSet(true);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        animationSet.addAnimation(rotate);
        arrow.startAnimation(animationSet);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        AnimationSet animationSet = new AnimationSet(true);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        animationSet.addAnimation(rotate);
        arrow.startAnimation(animationSet);
        arrow.setAnimation(rotate);
    }
}
