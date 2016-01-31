package com.todor.diabetes.ui.product_details;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import info.hoang8f.android.segmented.SegmentedGroup;

public class ProductDetailsFragment extends BaseFragment {

    @Bind(R.id.edt_product_value_for_calculation) EditText edtProductValueForCalculation;
    @Bind(R.id.tv_product_result_value) TextView productResultValue;
    @Bind(R.id.btn_gram) RadioButton btnGram;
    @Bind(R.id.btn_bread_unit) RadioButton btnBreadUnit;
    @Bind(R.id.btn_minus) Button btnMinus;
    @Bind(R.id.btn_plus) Button btnPlus;
    @Bind(R.id.edt_wrapper) TextInputLayout edt_product_value_wrapper;
    @Bind(R.id.btn_eatNow) Button btnEatNow;
    @Bind(R.id.btn_favorite) Button btnFavorite;
    @Bind(R.id.segmented_gramm_xe) SegmentedGroup segmentedGrammXe;

    private ProductFunctionality dbManager;
    private Product product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_details, container, false);
        ButterKnife.bind(this, v);
        dbManager = new ProductFunctionality(getActivity());
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
                String value = s.toString();
                int valueInt = 0;
                try {
                    valueInt = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), R.string.edit_correct_value, Toast.LENGTH_SHORT).show();
                }

                if (btnGram.isChecked()) {
                    float result = valueInt * Utils.getGlycemicIndex(getActivity()) / (product.carbohydrates / 100);
                    productResultValue.setText(String.format(getString(R.string.value_gram), result));
                } else if (btnBreadUnit.isChecked()) {
                    float result = valueInt * (product.carbohydrates / 100) / Utils.getGlycemicIndex(getActivity());
                    productResultValue.setText(String.format(getString(R.string.value_bread_unit), result));
                }
            }
        });

        return v;
    }

    private void clickChangeBreadUnit() {
        int value = 0;
        try {
            value = Integer.parseInt(edtProductValueForCalculation.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        float result = value * (product.carbohydrates / 100) / Utils.getGlycemicIndex(getActivity());
        productResultValue.setText(String.format(getString(R.string.value_bread_unit), result));
        edt_product_value_wrapper.setHint(getString(R.string.hint_product_gram));
    }

    public void clickChangeBtnGram() {
        int value = 0;
        try {
            value = Integer.parseInt(edtProductValueForCalculation.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        float result = value * Utils.getGlycemicIndex(getActivity()) / (product.carbohydrates / 100);
        productResultValue.setText(String.format(getString(R.string.value_gram), result));
        edt_product_value_wrapper.setHint(getString(R.string.hint_product_GL));
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
            int value = Integer.parseInt(edtProductValueForCalculation.getText().toString());
            edtProductValueForCalculation.setText(String.valueOf(value + 1));
        } catch (NumberFormatException e) {
            edtProductValueForCalculation.setText(String.valueOf(1));
        }
    }

    @OnClick(R.id.btn_minus)
    public void btnMinusClick() {
        try {
            int value = Integer.parseInt(edtProductValueForCalculation.getText().toString());
            if (value - 1 < 0) {
                Toast.makeText(getActivity(), getString(R.string.edit_positive_value), Toast.LENGTH_SHORT).show();
                return;
            }
            edtProductValueForCalculation.setText(String.valueOf(value - 1));
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), getString(R.string.edit_correct_value), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_favorite)
    public void btnFavoriteClick() {
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

    }

    @Override
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_product_details);
    }
}
