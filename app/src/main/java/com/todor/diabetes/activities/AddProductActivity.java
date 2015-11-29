package com.todor.diabetes.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.todor.diabetes.R;
import com.todor.diabetes.db.general.GeneralProductDbManager;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.utils.Utils;

public class AddProductActivity extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productCarbohydratesEditText;
    private EditText productGlycemicIndexEditText;
    private EditText productGroupEditText;
    private Button addButton;
    private GeneralProductDbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);

        dbManager = new GeneralProductDbManager(this);

        productNameEditText = (EditText) findViewById(R.id.product_name);
        productCarbohydratesEditText = (EditText) findViewById(R.id.product_carbohydrates);
        productGlycemicIndexEditText = (EditText) findViewById(R.id.product_glycemic_index);
        productGroupEditText = (EditText) findViewById(R.id.product_group);
        addButton = (Button) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftKeyboard(AddProductActivity.this);
                String productName = productNameEditText.getText().toString().toLowerCase();
                String productCarbohydrates = productCarbohydratesEditText.getText().toString();
                String productGlycemicIndex = productGlycemicIndexEditText.getText().toString();
                String productGroup = productGroupEditText.getText().toString();

                if (!productName.isEmpty() && !productCarbohydrates.isEmpty() && !productGlycemicIndex.isEmpty()
                        && !productGroup.isEmpty()) {
                    if (!dbManager.checkIfProductExists(productName)) {
                        float intProductCarbohydrates = Float.parseFloat(productCarbohydrates);
                        int floatProductGlycemicIndex = Integer.parseInt(productGlycemicIndex);
                        Product product = new Product(productName, intProductCarbohydrates,
                                floatProductGlycemicIndex, productGroup);
                        dbManager.insertProduct(product);
                    } else {
                        Snackbar.make(v, "This product is already in your list", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(v, "Please fill in all of the fields", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Utils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }
}
