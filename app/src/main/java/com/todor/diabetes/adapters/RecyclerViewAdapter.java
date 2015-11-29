package com.todor.diabetes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todor.diabetes.R;
import com.todor.diabetes.models.Product;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Product> productList;

    public RecyclerViewAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getProductName());
        holder.productCarbonates.setText(String.valueOf(product.getProductCarbohydrates()));
        holder.productGlycemicIndex.setText(product.getProductGlycemicIndex());
        holder.productGroup.setText(product.getProductGroup());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productCarbonates;
        private TextView productGlycemicIndex;
        private TextView productGroup;

        public ViewHolder(View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.product_name);
            productCarbonates = (TextView) itemView.findViewById(R.id.product_carbohydrates);
            productGlycemicIndex = (TextView) itemView.findViewById(R.id.product_glycemic_index);
            productGroup = (TextView) itemView.findViewById(R.id.product_group);
        }
    }
}
