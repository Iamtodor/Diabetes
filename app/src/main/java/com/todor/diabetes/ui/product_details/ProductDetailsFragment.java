package com.todor.diabetes.ui.product_details;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.BaseFragment;
import com.todor.diabetes.utils.PreferencesImpl;

import com.todor.diabetes.utils.*;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ProductDetailsFragment extends BaseFragment {

    @BindView(R.id.edt_product_value_for_calculation) EditText edtProductValueForCalculation;
    @BindView(R.id.btn_gram) RadioButton btnGram;
    @BindView(R.id.btn_bread_unit) RadioButton btnBreadUnit;
    @BindView(R.id.edt_wrapper) TextInputLayout edtProductValueWrapper;
    @BindView(R.id.tv_result_explanation) TextView tvResultExplanation;

    private ProductFunctionality dbManager;
    private Product product;
    private OnTableProductListener onTableProductListener;

    private int gram;
    private float glycemicIndex;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_product_details;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onTableProductListener = (OnTableProductListener) context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        product = getActivity().getIntent().getParcelableExtra(Constants.PRODUCT_KEY);
        edtProductValueWrapper.setHint(getString(R.string.hint_product_GL));

        edtProductValueForCalculation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    setValuesAndViews(s);
                } catch (NumberFormatException e) {
                    tvResultExplanation.setText(R.string.edit_correct_value);
                }
            }
        });
    }

    private void setValuesAndViews(Editable s) {
        float carbohydratesCount = PreferencesImpl.get().getCarbohydratesCount();
        if (btnGram.isChecked()) {
            if (checkCorrectResult(s.toString())) {
                float result = ProductCalculation.calculateGram(product.carbohydrates, carbohydratesCount,
                        s.toString());
                int value = ProductCalculation.parseStringToInt(s.toString());
                glycemicIndex = value;
                gram = (int) result;

                tvResultExplanation.setText(getString(R.string.bread_unit_to_gram, value, String.format("%.2f", result)));
            } else {
                tvResultExplanation.setText(R.string.edit_correct_value);
            }
        } else if (btnBreadUnit.isChecked()) {
            if (checkCorrectResult(s.toString())) {
                float result = ProductCalculation.calculateBreadUnits(product.carbohydrates, carbohydratesCount,
                        s.toString());
                int value = ProductCalculation.parseStringToInt(s.toString());
                glycemicIndex = value;
                gram = (int) result;
                tvResultExplanation.setText(getString(R.string.gram_to_bread_unit, value,
                        String.format("%.2f", result)));
            } else {
                tvResultExplanation.setText(R.string.edit_correct_value);
            }
        }
    }

    private void clickChangeBreadUnit() {
        float carbohydratesCount = PreferencesImpl.get().getCarbohydratesCount();
        String enteredValue = edtProductValueForCalculation.getText().toString();
        int intEnteredValue = ProductCalculation.parseStringToInt(enteredValue);
        edtProductValueWrapper.setHint(getString(R.string.hint_product_gram));
        if (checkCorrectResult(enteredValue)) {
            float result = ProductCalculation.calculateBreadUnits(product.carbohydrates, carbohydratesCount,
                    enteredValue);
            glycemicIndex = intEnteredValue;
            gram = (int) result;
            tvResultExplanation.setText(getString(R.string.gram_to_bread_unit, enteredValue,
                    String.format("%.2f", result)));
        } else {
            tvResultExplanation.setText(R.string.value);
        }
    }

    public void clickChangeBtnGram() {
        float carbohydratesCount = PreferencesImpl.get().getCarbohydratesCount();
        String enteredValue = edtProductValueForCalculation.getText().toString();
        int intEnteredValue = ProductCalculation.parseStringToInt(enteredValue);
        edtProductValueWrapper.setHint(getString(R.string.hint_product_GL));
        if (checkCorrectResult(enteredValue)) {
            float result = ProductCalculation.calculateGram(product.carbohydrates, carbohydratesCount,
                    enteredValue);
            glycemicIndex = intEnteredValue;
            gram = (int) result;
            tvResultExplanation.setText(getString(R.string.bread_unit_to_gram, enteredValue,
                    String.format("%.2f", result)));
        } else {
            tvResultExplanation.setText(R.string.value);
        }
    }

    private boolean checkCorrectResult(String enteredValue) {
        try {
            float carbohydratesCount = PreferencesImpl.get().getCarbohydratesCount();
            ProductCalculation.calculateGram(product.carbohydrates, carbohydratesCount,
                    enteredValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
        try {
            int value = ProductCalculation.parseStringToInt(edtProductValueForCalculation.getText().toString());
            edtProductValueForCalculation.setText(String.valueOf(value + 1));
        } catch (NumberFormatException e) {
            edtProductValueForCalculation.setText(String.valueOf(1));
        }
    }

    @OnClick(R.id.btn_minus)
    public void btnMinusClick() {
        try {
            int value = ProductCalculation.parseStringToInt(edtProductValueForCalculation.getText().toString());
            if (value - 1 < 0) {
                toast(R.string.edit_positive_value);
                return;
            }
            edtProductValueForCalculation.setText(String.valueOf(value - 1));
        } catch (NumberFormatException e) {
            edtProductValueForCalculation.setText(String.valueOf(1));
        }
    }

    @OnClick(R.id.btn_favorite)
    public void btnFavoriteClick() {
        if (dbManager == null) {
            dbManager = new ProductFunctionality(getActivity());
        }
        if (!product.isFavorite) {
            product.isFavorite = true;
            toast(R.string.added_to_favorite);
        } else {
            product.isFavorite = false;
            toast(R.string.deleted_from_favorite);
        }
        dbManager.updateProduct(product);
    }

    @OnClick(R.id.btn_eatNow)
    public void btnEatNowClick() {
        toast(R.string.product_added_on_table);
        onTableProductListener.setProduct(new TableProduct(product.name, gram, glycemicIndex));
    }
}
