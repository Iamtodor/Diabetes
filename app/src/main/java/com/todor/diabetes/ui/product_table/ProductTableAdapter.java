package com.todor.diabetes.ui.product_table;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todor.diabetes.R;
import com.todor.diabetes.models.TableProduct;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductTableAdapter extends RecyclerView.Adapter<ProductTableAdapter.ViewHolderProductItem> {

    private List<TableProduct> productArrayList;
    private Context context;
    private OnProductLongClickListener onProductLongClickListener;

    ProductTableAdapter(List<TableProduct> productArrayList, Context context,
                        OnProductLongClickListener onProductLongClickListener) {
        this.productArrayList = productArrayList;
        this.context = context;
        this.onProductLongClickListener = onProductLongClickListener;
    }

    @Override
    public ViewHolderProductItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_product_item_table, parent, false);
        return new ViewHolderProductItem(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderProductItem holder, int position) {
        TableProduct product = productArrayList.get(position);
        if (product != null) {
            holder.tvProductName.setText(product.name);
            holder.tvProductGram.setText(String.format(context.getString(R.string.gram_product_table), product.gram));
            holder.tvProductGI.setText(String.format(context.getString(R.string.bread_unit_product_table), product.glycemicIndex));
            holder.bind(product, onProductLongClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public TableProduct removeItem(int position) {
        final TableProduct model = productArrayList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, TableProduct model) {
        productArrayList.add(position, model);
        notifyItemInserted(position);
    }

    static class ViewHolderProductItem extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view) CardView cardView;
        @BindView(R.id.tv_name) TextView tvProductName;
        @BindView(R.id.tv_carbohydrates) TextView tvProductGram;
        @BindView(R.id.tv_group) TextView tvProductGI;

        public ViewHolderProductItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final TableProduct product, final OnProductLongClickListener onProductLongClickListener) {
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onProductLongClickListener.onItemLongClick(getAdapterPosition(), product);
                    return true;
                }
            });
        }
    }

}
