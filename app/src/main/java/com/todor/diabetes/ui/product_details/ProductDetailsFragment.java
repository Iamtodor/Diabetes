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

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.db.DbHelperSingleton;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.BaseFragment;
import com.todor.diabetes.utils.Utils;

import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;

public class ProductDetailsFragment extends BaseFragment {

    private EditText       productGram;
    private TextView       numberBreadCount;
    private RadioButton    gramButton;
    private RadioButton    breadUnitButton;
    private SegmentedGroup segmentedGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_details_fragment, container, false);
        ButterKnife.bind(this, v);

        Product product = getActivity().getIntent().getParcelableExtra(Constants.PRODUCT_KEY);
        productGram = (EditText) v.findViewById(R.id.edt_product_value_for_calculation);
        numberBreadCount = (TextView) v.findViewById(R.id.tv_product_result_value);
        gramButton = (RadioButton) v.findViewById(R.id.btn_gram);
        breadUnitButton = (RadioButton) v.findViewById(R.id.btn_bread_unit);
        segmentedGroup = (SegmentedGroup) v.findViewById(R.id.segmented_gramm_xe);

        productGram.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = productGram.getText().toString();
                int valueInt = Integer.parseInt(value);
                if (breadUnitButton.isSelected()) {
                    numberBreadCount.setText(valueInt / Utils.getGlycemicIndex(getActivity()) + " ХЕ");
                } else if (gramButton.isSelected()) {
                    numberBreadCount.setText(value + " грамм");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        breadUnitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breadUnitButton.setSelected(true);
            }
        });

        gramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gramButton.setSelected(true);
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
