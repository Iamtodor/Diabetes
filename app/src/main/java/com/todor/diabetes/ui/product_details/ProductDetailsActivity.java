package com.todor.diabetes.ui.product_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.db.DbHelperSingleton;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.EditProductActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailsActivity extends AppCompatActivity implements OnTableProductListener {

    private TableProduct productForTable;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        product = getIntent().getParcelableExtra(Constants.PRODUCT_KEY);
        getSupportActionBar().setTitle(product.name);
    }

    @OnClick(R.id.edit)
    public void editProductImageViewClick() {
        Intent intent = new Intent(ProductDetailsActivity.this, EditProductActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DbHelperSingleton.closeDb();
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

    @Override
    public void setProduct(TableProduct product) {
        productForTable = product;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(Constants.PRODUCT_FOR_TABLE, productForTable);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
}
