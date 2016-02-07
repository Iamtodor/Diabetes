package com.todor.diabetes.ui.product_table;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todor.diabetes.R;
import com.todor.diabetes.models.TableProduct;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductTableAdapter extends RecyclerView.Adapter<ProductTableAdapter.ViewHolderProductItem> {

    private List<TableProduct> productArrayList;
    private Context            context;

    public ProductTableAdapter(List<TableProduct> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
    }

    @Override
    public ViewHolderProductItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_product_item_table, parent, false);
        return new ViewHolderProductItem(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderProductItem holder, int position) {
        TableProduct product = productArrayList.get(position);
        holder.tvProductName.setText(product.name);
        holder.tvProductGram.setText(String.valueOf(product.gram) + " грамм");
        holder.tvProductGI.setText(String.valueOf(product.glycemicIndex) + " ХЕ");
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class ViewHolderProductItem extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)           TextView tvProductName;
        @Bind(R.id.tv_gram)           TextView tvProductGram;
        @Bind(R.id.tv_glycemic_index) TextView tvProductGI;

        public ViewHolderProductItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
