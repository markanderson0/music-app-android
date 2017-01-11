package app.application.merch.merchproduct;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.application.drawer.DrawerActivity;
import app.application.custom.NoDefaultSpinner;
import app.application.R;
import app.application.merch.merchsearch.MerchSearchActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that displayed a selected item of merch.
 */
public class MerchProductActivity extends DrawerActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.merch_product_image)
    ImageView productImage;
    @BindView(R.id.merch_product_name)
    TextView productName;
    @BindView(R.id.merch_product_price)
    TextView productPrice;
    @BindView(R.id.merch_product_size)
    NoDefaultSpinner productSize;
    @BindView(R.id.merch_product_quantity)
    NoDefaultSpinner productQuantity;

    private String name, image, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merch_product);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        image = extras.getString("image");
        price = extras.getString("price");
        createLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);
        final MenuItem searchActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                Intent ticketsSearchIntent = new Intent(getActivity(), MerchSearchActivity.class);
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
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            createLayout();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            createLayout();
        }
    }

    public void createLayout(){
        //TODO: Use percentage layout
        Configuration configuration = this.getResources().getConfiguration();
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int heightPixels = metrics.heightPixels;
            double height = heightPixels * 0.6;
            productImage.getLayoutParams().height = (int) height;
        }
        Picasso.with(this).load(image).fit().into(productImage);
        productName.setText(name);
        productPrice.setText("Price: " + price);

        List<String> size = new ArrayList<String>();
        size.add("S");
        size.add("M");
        size.add("L");
        size.add("XL");
        size.add("XXL");
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, size);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSize.setAdapter(sizeAdapter);
        productSize.setOnItemSelectedListener(this);

        List<String> quantity = new ArrayList<String>();
        quantity.add("1");
        quantity.add("2");
        quantity.add("3");
        quantity.add("4");
        quantity.add("5");
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quantity);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productQuantity.setAdapter(quantityAdapter);
        productQuantity.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()){
            case R.id.merch_product_size:
                break;
            case R.id.merch_product_quantity:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
