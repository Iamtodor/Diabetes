package com.todor.diabetes.ui.product_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.todor.diabetes.R;
import com.todor.diabetes.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> productList;
    private OnProductListItemClickListener listener;

    public ProductAdapter(List<Product> productList, OnProductListItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_item, parent, false);
        return new ViewHolderProductItem(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderProductItem viewHolderProductItem = (ViewHolderProductItem) holder;
        if (position == 0) {
            Product product = productList.get(position);
            viewHolderProductItem.itemLayout.setVisibility(View.GONE);
            viewHolderProductItem.productGroupHeader.setText(product.group);
            viewHolderProductItem.productGroupHeader.setVisibility(View.VISIBLE);
        } else {
            if (viewHolderProductItem.productGroupHeader.getVisibility() == View.VISIBLE) {
                viewHolderProductItem.productGroupHeader.setVisibility(View.GONE);
            }
            if (viewHolderProductItem.itemLayout.getVisibility() == View.GONE) {
                viewHolderProductItem.itemLayout.setVisibility(View.VISIBLE);
            }
            Product currentProduct = productList.get(position - 1);
            Product nextProduct = productList.get(position);
            if (!currentProduct.group.equals(nextProduct.group)) {
                viewHolderProductItem.itemLayout.setVisibility(View.GONE);
                viewHolderProductItem.productGroupHeader.setText(nextProduct.group);
                viewHolderProductItem.productGroupHeader.setVisibility(View.VISIBLE);
            } else {
                viewHolderProductItem.productName.setText(currentProduct.name);
                viewHolderProductItem.productCarbonates.setText(String.valueOf(currentProduct.carbohydrates));
                viewHolderProductItem.bind(currentProduct, listener);
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolderProductItem extends RecyclerView.ViewHolder {

        private TextView productGroupHeader;
        private TextView productName;
        private TextView productCarbonates;
        private LinearLayout itemLayout;

        public ViewHolderProductItem(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productCarbonates = (TextView) itemView.findViewById(R.id.product_carbohydrates);
            productGroupHeader = (TextView) itemView.findViewById(R.id.product_group);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        }

        private void bind(final Product product, final OnProductListItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onProductClick(product);
                }
            });
        }
    }
}
