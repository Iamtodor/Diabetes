package com.todor.diabetes.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.todor.diabetes.R;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.product_add.DelayAutoCompleteTextView;
import com.todor.diabetes.ui.product_add.GroupAutoCompleteAdapter;
import com.todor.diabetes.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditProductActivity extends AppCompatActivity {

    @Bind(R.id.product_name) protected          EditText                  productNameEditText;
    @Bind(R.id.product_carbohydrates) protected EditText                  productCarbohydratesEditText;
    @Bind(R.id.tv_product_group) protected      DelayAutoCompleteTextView autoCompleteTextView;
    @Bind(R.id.progress_bar) protected          ProgressBar               progressBar;
    @Bind(R.id.edit_btn) protected              Button                    editButton;

    private ProductFunctionality dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        ButterKnife.bind(this);

        List<String> groupArrayList = getProductGroupList();
        Product product = getIntent().getExtras().getParcelable("product");

        if (product != null) {
            productNameEditText.setText(product.name);
            productCarbohydratesEditText.setText(String.valueOf(product.carbohydrates));
            autoCompleteTextView.setText(product.name);
        }

        setupAutoCompleteTextView((ArrayList<String>) groupArrayList);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftKeyboard(EditProductActivity.this);
                String productName = productNameEditText.getText().toString().toLowerCase();
                String productCarbohydrates = productCarbohydratesEditText.getText().toString();
                String productGroup = autoCompleteTextView.getText().toString();

                if (!productName.isEmpty() && !productCarbohydrates.isEmpty() && !productGroup.isEmpty()) {
                    if (!dbManager.checkIfProductExists(productName)) {
                        float floatProductCarbohydrates = Float.parseFloat(productCarbohydrates);
                        Product product = new Product(productName, floatProductCarbohydrates,
                                productGroup, false);
                        dbManager.insertProduct(product);
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);
                        builder.setMessage(productName + getString(R.string.added_to_list));
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(RESULT_OK);
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

    @NonNull
    private List<String> getProductGroupList() {
        dbManager = new ProductFunctionality(this);
        HashSet<String> groupList = (HashSet<String>) dbManager.getGroupProducts();
        return new ArrayList<>(groupList);
    }

    private void setupAutoCompleteTextView(ArrayList<String> groupArrayList) {
        autoCompleteTextView.setAdapter(new GroupAutoCompleteAdapter(this, groupArrayList));
        autoCompleteTextView.setThreshold(3);
        autoCompleteTextView.setLoadingIndicator(progressBar);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String productGroup = (String) adapterView.getItemAtPosition(position);
                autoCompleteTextView.setText(productGroup);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Utils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }
}
