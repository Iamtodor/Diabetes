package com.todor.diabetes.ui.product_add;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.todor.diabetes.R;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.BaseActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;

public class AddProductActivity extends BaseActivity {

    @BindView(R.id.product_name) protected EditText productNameEditText;
    @BindView(R.id.product_carbohydrates) protected EditText productCarbohydratesEditText;
    @BindView(R.id.tv_product_group) protected DelayAutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.progress_bar) protected ProgressBar progressBar;
    @BindView(R.id.edit_btn) protected Button addButton;

    private ProductFunctionality dbManager;

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> groupArrayList = getProductGroupList();

        setupAutoCompleteTextView((ArrayList<String>) groupArrayList);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String productName = productNameEditText.getText().toString();
                String productCarbohydrates = productCarbohydratesEditText.getText().toString();
                String productGroup = autoCompleteTextView.getText().toString();

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

}
