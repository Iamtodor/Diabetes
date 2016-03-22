package com.todor.diabetes.ui.product_favorite;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todor.diabetes.R;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.product_table.OnProductLongClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductFavoriteAdapter extends RecyclerView.Adapter<ProductFavoriteAdapter.ViewHolderProductItem> {

    private List<Product>         productArrayList;
    private Context                    context;
    private OnProductLongClickListener onProductLongClickListener;

    public ProductFavoriteAdapter(List<Product> productArrayList, Context context,
                               OnProductLongClickListener onProductLongClickListener) {
        this.productArrayList = productArrayList;
        this.context = context;
        this.onProductLongClickListener = onProductLongClickListener;
    }

    @Override
    public ViewHolderProductItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_product_item_favorite, parent, false);
        return new ViewHolderProductItem(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderProductItem holder, int position) {
        Product product = productArrayList.get(position);
        if (product != null) {
            holder.tvProductName.setText(product.name);
            holder.tvProductGram.setText(String.valueOf(product.carbohydrates));
            holder.tvProductGI.setText(product.group);
//            holder.bind(product, onProductLongClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    static class ViewHolderProductItem extends RecyclerView.ViewHolder {

        @Bind(R.id.card_view)        CardView cardView;
        @Bind(R.id.tv_name)          TextView tvProductName;
        @Bind(R.id.tv_carbohydrates) TextView tvProductGram;
        @Bind(R.id.tv_group)         TextView tvProductGI;

        public ViewHolderProductItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

//        public void bind(final Product product, final OnProductLongClickListener onProductLongClickListener) {
//            cardView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    onProductLongClickListener.onItemLongClick(getAdapterPosition(), product);
//                    return true;
//                }
//            });
//        }
    }

}
