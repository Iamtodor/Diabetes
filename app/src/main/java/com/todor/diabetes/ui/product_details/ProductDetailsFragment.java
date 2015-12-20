package com.todor.diabetes.ui.product_details;

import android.os.Bundle;
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
import com.todor.diabetes.db.DbHelperSingleton;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.BaseFragment;
import com.todor.diabetes.utils.Utils;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;

public class ProductDetailsFragment extends BaseFragment {

    @Bind(R.id.edt_product_value_for_calculation)
    EditText       edtProductValueForCalculation;
    @Bind(R.id.tv_product_result_value)
    TextView       productResultValue;
    @Bind(R.id.btn_gram)
    RadioButton    btnGram;
    @Bind(R.id.btn_bread_unit)
    RadioButton    btnBreadUnit;
    @Bind(R.id.segmented_gramm_xe)
    SegmentedGroup segmentedGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_details_fragment, container, false);
        ButterKnife.bind(this, v);

        Product product = getActivity().getIntent().getParcelableExtra(Constants.PRODUCT_KEY);

        edtProductValueForCalculation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = edtProductValueForCalculation.getText().toString();
                int valueInt = 0;
                DecimalFormat df = new DecimalFormat("#.##");
                try {
                    valueInt = Integer.parseInt(value);
                } catch (NumberFormatException e) {

                }
                if (btnGram.isSelected()) {
                    float result = valueInt * Utils.getGlycemicIndex(getActivity());
                    productResultValue.setText(String.valueOf(df.format(result)) + " " +
                                    getResources().getString(R.string.gram));
                } else if (btnBreadUnit.isSelected()) {
                    float result = valueInt / Utils.getGlycemicIndex(getActivity());
                    productResultValue.setText(String.valueOf(df.format(result)) + " " +
                                    getResources().getString(R.string.bread_unit));
                }
            }
        });

        btnBreadUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBreadUnit.setSelected(true);
                btnGram.setSelected(false);
            }
        });

        btnGram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGram.setSelected(true);
                btnBreadUnit.setSelected(false);
            }
        });

        return v;
    }

    @Override
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_product_details);
    }

    @Override
    public void onStop() {
        super.onStop();
        DbHelperSingleton.closeDb();
    }
}
