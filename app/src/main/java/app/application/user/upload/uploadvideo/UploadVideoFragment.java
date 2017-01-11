package app.application.user.upload.uploadvideo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.util.ArrayList;

import javax.inject.Inject;

import app.application.custom.CustomBootstrapColors;
import app.application.R;
import app.application.dagger.DaggerInjector;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;
import static java.lang.Integer.parseInt;

/**
 * Fragment that allows for videos to be uploaded.
 */
public class UploadVideoFragment extends Fragment implements UploadVideoContract.View {

    @BindView(R.id.upload_artist_input)
    BootstrapEditText artistTextView;
    @BindView(R.id.upload_year_input)
    BootstrapEditText yearTextView;
    @BindView(R.id.upload_events)
    TextView eventsTextView;
    @BindView(R.id.upload_selected_songs)
    TextView selectedSongsTextView;
    @BindView(R.id.upload_btn)
    BootstrapButton uploadButton;

    @Inject
    UploadVideoPresenter uploadVideoPresenter;

    private boolean validArtistName, validEventYear, validEvents, validSongs, submitted;
    private String artistName, mbid, eventYear, selectedSongsString;
    private MaterialDialog materialDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View view = inflater.inflate(R.layout.upload_video, container, false);
        DaggerInjector.get().inject(this);
        ButterKnife.bind(this, view);
        uploadVideoPresenter.attachView(this);

        artistName = "";
        mbid = "";
        eventYear = "";
        selectedSongsString = "";
        submitted = false;

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uploadVideoPresenter.detachView();
    }

    @OnTextChanged(value = R.id.upload_artist_input, callback = AFTER_TEXT_CHANGED)
    public void artistChanged(Editable editable) {
        if (!editable.toString().equals(null)) {
            uploadVideoPresenter.getArtistName(editable.toString());
        }
        initializeSelection();
        validateArtistName();
    }

    @OnTextChanged(value = R.id.upload_year_input, callback = AFTER_TEXT_CHANGED)
    public void yearChanged(Editable editable) {
        if (editable.toString().length() == 4 && !mbid.equals("")) {
            eventYear = editable.toString();
            uploadVideoPresenter.getEvent(eventYear, "1");
        }
        initializeSelection();
        validateEventYear();
    }

    @OnClick(R.id.upload_btn)
    public void submitUpload(){
        submitted = true;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View uploadView = layoutInflater.inflate(R.layout.upload_video_dialog, null);
        TextView uploadArtistTextView = ButterKnife.findById(uploadView, R.id.upload_artist_input);
        TextView uploadYearTextView = ButterKnife.findById(uploadView, R.id.upload_year_input);
        TextView uploadVenueTextView = ButterKnife.findById(uploadView, R.id.upload_events_spinner);
        TextView uploadSongsTextView = ButterKnife.findById(uploadView, R.id.upload_selected_songs);
        CheckBox termsCheckBox = ButterKnife.findById(uploadView, R.id.upload_terms_cb);
        BootstrapButton submitButton = ButterKnife.findById(uploadView, R.id.upload_btn);
        validateArtistName();
        validateEventYear();
        validateEvents();
        validateSongs();
        if (validArtistName && validEventYear && validEvents && validSongs) {
            uploadArtistTextView.setText(artistName);
            uploadYearTextView.setText(eventYear);
            uploadVenueTextView.setText(eventsTextView.getText());
            uploadSongsTextView.setText(selectedSongsTextView.getText());
            materialDialog = new MaterialDialog.Builder(getContext())
                    .customView(uploadView, true)
                    .show();
        }
        termsCheckBox.setOnClickListener((View termsView) -> {
            if (!termsCheckBox.isChecked()) {
                termsCheckBox.setError("Terms must be agreed");
                termsCheckBox.setTextColor(Color.rgb(217, 83, 79));
            } else {
                termsCheckBox.setError(null);
                termsCheckBox.setTextColor(Color.BLACK);
            }
        });

        submitButton.setOnClickListener((View termsView) -> {
            if (termsCheckBox.isChecked()) {
                submitted = false;
                artistTextView.setText("");
                yearTextView.setText("");
                artistName = "";
                mbid = "";
                eventYear = "";
                selectedSongsString = "";
                initializeSelection();
                materialDialog.dismiss();
            } else {
                termsCheckBox.setError("Terms must be agreed");
                termsCheckBox.setTextColor(Color.rgb(217, 83, 79));
            }
        });
    }

    @Override
    public void getArtist(String artistName) {
        this.artistName = artistName;
        uploadVideoPresenter.getArtist(artistName, "1");
    }

    @Override
    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    @Override
    public void getEvent(String eventYear, String page) {
        uploadVideoPresenter.getEvent(eventYear, page);
    }

    @Override
    public void setEvents(ArrayList<String> eventIds, ArrayList<String> events) {
        eventsTextView.setOnClickListener((View view) -> {
            LinearLayout eventsLayout = new LinearLayout(getContext());
            eventsLayout.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < events.size(); i++) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View eventsView = layoutInflater.inflate(R.layout.upload_dialog_row, null);
                ImageView selectedEventTick = ButterKnife.findById(eventsView, R.id.selected_song_tick);
                TextView eventTitle = ButterKnife.findById(eventsView, R.id.row_num);
                TextView eventName = ButterKnife.findById(eventsView, R.id.row_item);
                selectedEventTick.setVisibility(View.GONE);
                eventTitle.setText(String.valueOf(eventIds.get(i)));
                eventTitle.setVisibility(View.GONE);
                eventName.setText(events.get(i));
                eventsLayout.addView(eventsView);
                eventName.setPadding(0, 0, 0, 10);
                eventsView.setOnClickListener((View selectedEventView) -> {
                    TextView selectedEventNumber = ButterKnife.findById(selectedEventView, R.id.row_num);
                    TextView selectedEventTitle = ButterKnife.findById(selectedEventView, R.id.row_item);
                    eventsTextView.setText(selectedEventTitle.getText());
                    selectedSongsTextView.setText("");
                    uploadVideoPresenter.getSongs(selectedEventNumber.getText().toString());
                    validateEvents();
                    materialDialog.dismiss();
                });
            }
            materialDialog = new MaterialDialog.Builder(getContext())
                    .customView(eventsLayout, true)
                    .show();
        });
    }

    @Override
    public void setSongs(ArrayList<String> songs, ArrayList<String> selectedSongs) {
        selectedSongsTextView.setOnClickListener((View view) -> {
            LinearLayout setlistLayout = new LinearLayout(getContext());
            setlistLayout.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < songs.size(); i++) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View setlistView = layoutInflater.inflate(R.layout.upload_dialog_row, null);
                ImageView selectedSongTick = ButterKnife.findById(setlistView, R.id.selected_song_tick);
                TextView songNumber = ButterKnife.findById(setlistView, R.id.row_num);
                TextView songName = ButterKnife.findById(setlistView, R.id.row_item);
                selectedSongTick.setVisibility(View.INVISIBLE);
                songNumber.setText(String.valueOf(i));
                songNumber.setVisibility(View.INVISIBLE);
                songName.setText(songs.get(i));
                setlistLayout.addView(setlistView);
                setlistView.setOnClickListener((View selectedSongView) -> {
                    //ImageView selectedSongTick = (ImageView) view.findViewById(R.id.selected_song_tick);
                    selectedSongTick.setVisibility(View.VISIBLE);
                    TextView selectedSongNumber = ButterKnife.findById(selectedSongView, R.id.row_num);
                    TextView selectedSongName = ButterKnife.findById(selectedSongView, R.id.row_item);
                    if (!selectedSongs.contains(selectedSongName.getText().toString())) {
                        selectedSongs.set(parseInt(selectedSongNumber.getText().toString()), selectedSongName.getText().toString());
                    } else {
                        selectedSongs.set(parseInt(selectedSongNumber.getText().toString()), "");
                    }
                    selectedSongsTextView.setText("");
                    selectedSongsString = "";
                    for (String selectedSong : selectedSongs) {
                        if (!selectedSong.equals("")) {
                            selectedSongsString += selectedSong + ", ";
                        }
                    }
                    if (selectedSongsString.length() > 2) {
                        selectedSongsString = selectedSongsString.substring(0, selectedSongsString.length() - 2);
                        selectedSongsTextView.setText(selectedSongsString);
                    }
                    validateSongs();
                    materialDialog.dismiss();
                });
            }
            materialDialog = new MaterialDialog.Builder(getContext())
                    .customView(setlistLayout, true)
                    .show();
        });
    }

    @Override
    public void initializeSelection() {
        uploadVideoPresenter.initializeSelection();
        eventsTextView.setText("");
        selectedSongsTextView.setText("");
    }

    private boolean validateArtistName() {
        validArtistName = false;
        if (artistTextView.getText().length() == 0 && submitted) {
            artistTextView.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            artistTextView.setError("Enter an Artists Name");
        } else {
            artistTextView.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            validArtistName = true;
        }
        return validArtistName;
    }

    private boolean validateEventYear() {
        validEventYear = false;
        if (yearTextView.getText().length() == 0 && submitted) {
            yearTextView.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_danger));
            yearTextView.setError("Enter a Year");
        } else {
            yearTextView.setBootstrapBrand(new CustomBootstrapColors(R.color.bootstrap_brand_secondary_border));
            validEventYear = true;
        }
        return validEventYear;
    }

    private boolean validateEvents() {
        validEvents = false;
        if (eventsTextView.getText().length() == 0 && submitted) {
            eventsTextView.setBackgroundResource(R.drawable.back_error);
            eventsTextView.setError("Select an Event");
        } else {
            eventsTextView.setBackgroundResource(R.drawable.back);
            eventsTextView.setError(null);
            validEvents = true;
        }
        return validEvents;
    }

    private boolean validateSongs() {
        validSongs = false;
        if (selectedSongsTextView.getText().length() == 0 && submitted) {
            selectedSongsTextView.setBackgroundResource(R.drawable.back_error);
            selectedSongsTextView.setError("Select Songs");
        } else {
            selectedSongsTextView.setBackgroundResource(R.drawable.back);
            selectedSongsTextView.setError(null);
            validSongs = true;
        }
        return validSongs;
    }
}
