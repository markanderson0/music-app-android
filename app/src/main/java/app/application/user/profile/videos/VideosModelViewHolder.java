package app.application.user.profile.videos;

import android.graphics.Color;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import app.application.R;
import butterknife.ButterKnife;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class VideosModelViewHolder extends GroupViewHolder {

    private TextView artistName;
    private ImageView artistImage, arrow;
    private RelativeLayout cardView;

    public VideosModelViewHolder(View itemView) {
        super(itemView);
        artistName = ButterKnife.findById(itemView, R.id.artist_name);
        artistImage = ButterKnife.findById(itemView, R.id.artist_image);
        arrow = ButterKnife.findById(itemView, R.id.list_item_genre_arrow);
        cardView = ButterKnife.findById(itemView, R.id.artist_video_row);
    }

    public void setArtistNameTitle(ExpandableGroup artistName) {
        this.artistName.setText(artistName.getTitle());
    }

    public void setArtistImage(String image) {
        Picasso.with(itemView.getContext()).load(image).fit().into(artistImage);
    }

    @Override
    public void expand() {
        cardView.setBackgroundColor(Color.parseColor("#f0f0f0"));
        animateExpand();
    }

    @Override
    public void collapse() {
        cardView.setBackgroundColor(Color.WHITE);
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
