package com.todor.diabetes.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.todor.diabetes.R;
import com.todor.diabetes.db.DbHelperSingleton;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddProductActivity extends AppCompatActivity {

    @Bind(R.id.product_name) EditText productNameEditText;
    @Bind(R.id.product_carbohydrates) EditText productCarbohydratesEditText;
    @Bind(R.id.product_group) EditText productGroupEditText;
    @Bind(R.id.add_button) Button addButton;
    private ProductFunctionality dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);

        dbManager = new ProductFunctionality(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftKeyboard(AddProductActivity.this);
                String productName = productNameEditText.getText().toString().toLowerCase();
                String productCarbohydrates = productCarbohydratesEditText.getText().toString();
                String productGroup = productGroupEditText.getText().toString();

                if (!productName.isEmpty() && !productCarbohydrates.isEmpty() && !productGroup.isEmpty()) {
                    if (!dbManager.checkIfProductExists(productName)) {
                        float floatProductCarbohydrates = Float.parseFloat(productCarbohydrates);
                        Product product = new Product(productName, floatProductCarbohydrates,
                                productGroup, false);
                        dbManager.insertProduct(product);
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                        builder.setMessage(productName + getString(R.string.added_to_list));
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    } else {
                        Snackbar.make(v, R.string.product_exists, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(v, R.string.fill_all_fields, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Utils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DbHelperSingleton.closeDb();
    }
}
