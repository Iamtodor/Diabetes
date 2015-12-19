package com.todor.diabetes.ui.product_details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.models.Product;

import butterknife.ButterKnife;

/**
 * Created by todor on 17.12.15
 */
public class ProductDetailsActivity extends AppCompatActivity {

    private NumberPicker amongProductPicker;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.product_details_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) toolbar.findViewById(R.id.edit);
        amongProductPicker = (NumberPicker) findViewById(R.id.amongProduct);

        Product product = getIntent().getParcelableExtra(Constants.PRODUCT_KEY);
        getSupportActionBar().setTitle(product.name);

        amongProductPicker.setMinValue(10);
        amongProductPicker.setMaxValue(100);
        amongProductPicker.setValue(20);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDetailsActivity.this, "smth", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return (true);
        }
        return false;
    }
}
