package com.todor.diabetes.ui.product_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.todor.diabetes.R;
import com.todor.diabetes.models.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product>                  productList;
    private OnProductListItemClickListener listener;

    public ProductListAdapter(List<Product> productList, OnProductListItemClickListener listener) {
        this.productList = new ArrayList<>(productList);
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_item_list, parent, false);
        return new ViewHolderProductItem(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderProductItem viewHolder = (ViewHolderProductItem) holder;
        Product product = productList.get(position);

        if (needShowGroupHeader(position, product)) {
            viewHolder.productGroupHeader.setText(product.group);
            viewHolder.productGroupHeader.setVisibility(View.VISIBLE);
        } else {
            viewHolder.productGroupHeader.setVisibility(View.GONE);
        }

        viewHolder.productName.setText(product.name);
        viewHolder.productCarbonates.setText(String.valueOf(product.carbohydrates));

        viewHolder.bind(product, listener);
    }

    private boolean needShowGroupHeader(int position, Product product) {
        if (position == 0) {
            return true;
        }

        Product previousProduct = productList.get(position - 1);
        return !product.group.equals(previousProduct.group);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void animateTo(List<Product> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Product> newModels) {
        for (int i = productList.size() - 1; i >= 0; i--) {
            final Product model = productList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Product> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Product model = newModels.get(i);
            if (!productList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Product> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Product model = newModels.get(toPosition);
            final int fromPosition = productList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Product removeItem(int position) {
        final Product model = productList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Product model) {
        productList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Product model = productList.remove(fromPosition);
        productList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    class ViewHolderProductItem extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_product_group)      TextView     productGroupHeader;
        @Bind(R.id.item_layout)           LinearLayout itemLayout;
        @Bind(R.id.product_name)          TextView     productName;
        @Bind(R.id.product_carbohydrates) TextView     productCarbonates;

        public ViewHolderProductItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void bind(final Product product, final OnProductListItemClickListener listener) {
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onProductClick(product);
                }
            });
        }
    }

}