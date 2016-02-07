package com.todor.diabetes.ui.product_details;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.BaseFragment;
import com.todor.diabetes.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ProductDetailsFragment extends BaseFragment {

    @Bind(R.id.edt_product_value_for_calculation) EditText        edtProductValueForCalculation;
    @Bind(R.id.tv_product_result_value)           TextView        productResultValue;
    @Bind(R.id.btn_gram)                          RadioButton     btnGram;
    @Bind(R.id.btn_bread_unit)                    RadioButton     btnBreadUnit;
    @Bind(R.id.edt_wrapper)                       TextInputLayout edt_product_value_wrapper;
    @Bind(R.id.tv_result_explanation)             TextView        tvResultExplanation;

    private ProductFunctionality   dbManager;
    private Product                product;
    private OnTableProductListener onTableProductListener;

    @Override
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_product_details);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onTableProductListener = (OnTableProductListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_details, container, false);
        ButterKnife.bind(this, v);

        product = getActivity().getIntent().getParcelableExtra(Constants.PRODUCT_KEY);
        edt_product_value_wrapper.setHint(getString(R.string.hint_product_GL));
        productResultValue.setText(String.format(getString(R.string.value_gram), 0.0));

        edtProductValueForCalculation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int value = parseStringToInt(s.toString());
                if (btnGram.isChecked()) {
                    float result = getGramFromBreadUnits(value);
                    productResultValue.setText(String.format(getString(R.string.value_gram), result));
                    tvResultExplanation.setText(value + " ХЕ это " + String.format("%.2f", result) + " грамм");
                } else if (btnBreadUnit.isChecked()) {
                    float result = getBreadUnitsFromGram(value);
                    productResultValue.setText(String.format(getString(R.string.value_bread_unit), result));
                    tvResultExplanation.setText(value + " грамм это " + String.format("%.2f", result) + " ХЕ");
                }
            }
        });

        return v;
    }

    public float getGramFromBreadUnits(int value) {
        return value * Utils.getGlycemicIndex(getActivity()) / (product.carbohydrates / 100);
    }

    public float getBreadUnitsFromGram(int value) {
        return value * (product.carbohydrates / 100) / Utils.getGlycemicIndex(getActivity());
    }

    public int parseStringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void clickChangeBreadUnit() {
        int value = parseStringToInt(edtProductValueForCalculation.getText().toString());
        float result = getBreadUnitsFromGram(value);
        productResultValue.setText(String.format(getString(R.string.value_bread_unit), result));
        edt_product_value_wrapper.setHint(getString(R.string.hint_product_gram));
        tvResultExplanation.setText(value + " грамм это " + String.format("%.2f", result) + " XE");
    }

    public void clickChangeBtnGram() {
        int value = parseStringToInt(edtProductValueForCalculation.getText().toString());
        float result = getGramFromBreadUnits(value);
        productResultValue.setText(String.format(getString(R.string.value_gram), result));
        edt_product_value_wrapper.setHint(getString(R.string.hint_product_GL));
        tvResultExplanation.setText(value + " ХЕ это " + String.format("%.2f", result) + " грамм");
    }

    @OnClick(R.id.btn_bread_unit)
    public void btnBreadUnitClick() {
        clickChangeBreadUnit();
    }

    @OnCheckedChanged(R.id.btn_bread_unit)
    public void btnBreadUnitChanged() {
        clickChangeBreadUnit();
    }

    @OnClick(R.id.btn_gram)
    public void btnGramClick() {
        clickChangeBtnGram();
    }

    @OnCheckedChanged(R.id.btn_gram)
    public void btnGramChanged() {
        clickChangeBtnGram();
    }

    @OnClick(R.id.btn_plus)
    public void btnPlusClick() {
        edtProductValueForCalculation.setText(String.valueOf(
                parseStringToInt(edtProductValueForCalculation.getText().toString()) + 1));
    }

    @OnClick(R.id.btn_minus)
    public void btnMinusClick() {
        int value = parseStringToInt(edtProductValueForCalculation.getText().toString());
        if (value - 1 < 0) {
            Toast.makeText(getActivity(), getString(R.string.edit_positive_value), Toast.LENGTH_SHORT).show();
            return;
        }
        edtProductValueForCalculation.setText(String.valueOf(value - 1));
    }

    @OnClick(R.id.btn_favorite)
    public void btnFavoriteClick() {
        if (dbManager == null) {
            dbManager = new ProductFunctionality(getActivity());
        }
        if (!product.isFavorite) {
            product.isFavorite = true;
            Toast.makeText(getActivity(), R.string.added_to_favorite, Toast.LENGTH_SHORT).show();
        } else {
            product.isFavorite = false;
            Toast.makeText(getActivity(), R.string.deleted_from_favorite, Toast.LENGTH_SHORT).show();
        }
        dbManager.updateProduct(product);
    }

    @OnClick(R.id.btn_eatNow)
    public void btnEatNowClick() {
        onTableProductListener.setProduct(product);
    }
}
