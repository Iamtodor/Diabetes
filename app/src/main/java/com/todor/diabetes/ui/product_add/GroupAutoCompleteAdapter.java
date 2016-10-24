package com.todor.diabetes.ui.product_add;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.todor.diabetes.R;

import java.util.ArrayList;

public class GroupAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private Context           context;
    private ArrayList<String> groupList;
    private ArrayList<String> filteredResult;

    public GroupAutoCompleteAdapter(Context context, ArrayList<String> groupList) {
        this.context = context;
        this.groupList = groupList;
        filteredResult = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return filteredResult.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredResult.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.drop_down_group_line, parent);
        }

        String productGroup = filteredResult.get(position);
        ((TextView) convertView.findViewById(R.id.tv_product_group)).setText(productGroup);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    ArrayList<String> groups = new ArrayList<>();
                    for (int i = 0; i < groupList.size(); i++) {
                        if (groupList.get(i).toLowerCase().contains(constraint.toString().toLowerCase())) {
                            groups.add(groupList.get(i));
                        }
                    }
                    filterResults.count = groups.size();
                    filterResults.values = groups;
                } else {
                    filterResults.count = groupList.size();
                    filterResults.values = groupList;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    filteredResult = (ArrayList<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }


}
