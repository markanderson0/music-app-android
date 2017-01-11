package app.application.tickets.ticketssearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import app.application.drawer.DrawerActivity;
import app.application.R;
import app.application.tickets.ticketssearch.data.model.TicketsSearchModel;
import butterknife.ButterKnife;

/**
 * Activity that displays the location of the artists tickets on GoogleMaps.
 */
public class TicketsMapActivity extends DrawerActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private GoogleMap map;
    private ArrayList<TicketsSearchModel> gridData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets_map);

        Bundle extras = getIntent().getExtras();
        gridData = extras.getParcelableArrayList("tickets");
        String search = extras.getString("search");
        setTitle(search + " Tickets");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tickets_search_map_menu, menu);
        final MenuItem searchActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                Intent ticketsSearchIntent = new Intent(TicketsMapActivity.this, TicketsSearchActivity.class);
                ticketsSearchIntent.putExtra("search", query);
                startActivity(ticketsSearchIntent);
                searchActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        final MenuItem listMenuItem = menu.findItem(R.id.list_icon);
        listMenuItem.setOnMenuItemClickListener((MenuItem menuItem) -> {
            onBackPressed();
            return false;
        });

        final MenuItem mapMenuItem = menu.findItem(R.id.map_icon);
        mapMenuItem.setVisible(false);
        return true;
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                String[] ticketDetails = marker.getTitle().split(":::");
                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                for(int i = 0; i < ticketDetails.length; i+=3) {
                    View infoWindow = getLayoutInflater().inflate(R.layout.tickets_map_info_window, null);

                    TextView ticketNameTextView = ButterKnife.findById(infoWindow, R.id.ticket_name);
                    ticketNameTextView.setText(ticketDetails[i]);

                    TextView ticketDateTextView = ButterKnife.findById(infoWindow, R.id.ticket_date);
                    ticketDateTextView.setText(ticketDetails[i + 1]);

                    TextView ticketVenueTextView = ButterKnife.findById(infoWindow, R.id.ticket_venue);
                    ticketVenueTextView.setText(ticketDetails[i + 2]);

                    if((i + 3) == ticketDetails.length) {
                        View ticketDivider = ButterKnife.findById(infoWindow, R.id.ticket_divider);
                        ticketDivider.setVisibility(View.GONE);
                    }
                    linearLayout.addView(infoWindow);
                }
                return linearLayout;
            }
        });

        ArrayList<Marker> markers = new ArrayList<>();

        for(int i = 0; i < gridData.size(); i++) {
            Log.i(gridData.get(i).getCity() + ", " + gridData.get(i).getCountry(), gridData.get(i).getVenue());
            if(gridData.get(i).getLat() != null || gridData.get(i).getLng() != null) {
                String venue = !gridData.get(i).getVenue().equals("") ?  gridData.get(i).getVenue() : "Venue TBA";
                final LatLng position = new LatLng(gridData.get(i).getLat(), gridData.get(i).getLng());
                if(i > 0 && markers.get(markers.size()-1).getPosition().latitude == gridData.get(i).getLat()) {
                    markers.get(markers.size()-1).setTitle(markers.get(markers.size()-1).getTitle() + ":::" + gridData.get(i).getName() + ":::" + gridData.get(i).getDate() + ":::" + venue);
                } else {
                    Marker marker = this.map.addMarker(new MarkerOptions()
                            .position(position)
                            .title(gridData.get(i).getName() + ":::" + gridData.get(i).getDate() + ":::" + venue));
                    markers.add(marker);
                }
            }
        }
        // Set a listener for marker click.
        this.map.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) { return false; }
}

