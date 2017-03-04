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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> products;
    private OnProductClickListener onProductClickListener;
    private OnProductLongClickListener onProductLongClickListener;

    public ProductListAdapter(List<Product> products, OnProductClickListener onProductClickListener, OnProductLongClickListener onProductLongClickListener) {
        this.products = new ArrayList<>(products);
        this.onProductClickListener = onProductClickListener;
        this.onProductLongClickListener = onProductLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_item_list, parent, false);
        return new ViewHolderProductItem(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderProductItem viewHolder = (ViewHolderProductItem) holder;
        Product product = products.get(position);

        if (needShowGroupHeader(position, product)) {
            viewHolder.productGroupHeader.setText(product.group);
            viewHolder.productGroupHeader.setVisibility(View.VISIBLE);
        } else {
            viewHolder.productGroupHeader.setVisibility(View.GONE);
        }

        viewHolder.productName.setText(product.name);
        viewHolder.productCarbonates.setText(String.valueOf(product.carbohydrates));

        viewHolder.bind(product, onProductClickListener, onProductLongClickListener);
    }

    private boolean needShowGroupHeader(int position, Product product) {
        if (position == 0) {
            return true;
        }

        Product previousProduct = products.get(position - 1);
        return !product.group.equals(previousProduct.group);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProducts(List<Product> filteredModelList) {
        products.clear();
        products.addAll(filteredModelList);
        notifyDataSetChanged();
    }

    public void animateTo(List<Product> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Product> newModels) {
        for (int i = products.size() - 1; i >= 0; i--) {
            final Product model = products.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Product> newModels) {
        for (int i = 0; i < newModels.size(); i++) {
            final Product model = newModels.get(i);
            if (!products.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Product> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Product model = newModels.get(toPosition);
            final int fromPosition = products.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Product removeItem(int position) {
        final Product model = products.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Product model) {
        products.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final Product model = products.remove(fromPosition);
        products.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    static class ViewHolderProductItem extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_product_group) TextView productGroupHeader;
        @BindView(R.id.item_layout) LinearLayout itemLayout;
        @BindView(R.id.product_name) TextView productName;
        @BindView(R.id.product_carbohydrates) TextView productCarbonates;

        public ViewHolderProductItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void bind(final Product product, final OnProductClickListener onProductClickListener,
                          final OnProductLongClickListener onProductLongClickListener) {

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProductClickListener.onProductClick(product);
                }
            });

            itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onProductLongClickListener.onItemLongClick(getAdapterPosition(), product);
                    return true;
                }
            });
        }

    }
}