package com.todor.diabetes.ui.product_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todor.diabetes.R;
import com.todor.diabetes.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Product> productList;
    private OnProductListItemClickListener listener;
    private ProductHeader productHeader;

    public ProductAdapter(ProductHeader productHeader, List<Product> productList, OnProductListItemClickListener listener) {
        this.productHeader = productHeader;
        this.productList = productList;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_header, parent, false);
            return new ViewHolderProductHeader(v);
        } else if (viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_item, parent, false);
            return new ViewHolderProductItem(v);
        }
        throw new RuntimeException("There is no suitable type");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderProductHeader) {
            ViewHolderProductHeader viewHolderProductHeader = (ViewHolderProductHeader) holder;
            viewHolderProductHeader.productHeader.setText(productHeader.header);
        } else if (holder instanceof ViewHolderProductItem) {
            ViewHolderProductItem viewHolderProductItem = (ViewHolderProductItem) holder;
            Product product = productList.get(position - 1);
            viewHolderProductItem.productName.setText(product.name);
            viewHolderProductItem.productCarbonates.setText(String.valueOf(product.carbohydrates));
            viewHolderProductItem.bind(product, listener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return productList.size() + 1;
    }

    class ViewHolderProductItem extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productCarbonates;

        public ViewHolderProductItem(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productCarbonates = (TextView) itemView.findViewById(R.id.product_carbohydrates);
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

    class ViewHolderProductHeader extends RecyclerView.ViewHolder {

        private TextView productHeader;

        public ViewHolderProductHeader(View itemView) {
            super(itemView);
            productHeader = (TextView) itemView.findViewById(R.id.product_header);
        }
    }
}
