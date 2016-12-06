package br.com.examples.caique.examplesproject.common.view.adapter.generic;


import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericFilterAdapter<V extends GenericViewHolder, C> extends GenericAdapter<V, C> implements Filterable {
    private List<C> originalList = new ArrayList<>();
    protected Context context;
    private boolean isFiltered;

    public GenericFilterAdapter(Context context){
        this.context = context;
    }

    @Override
    public List<C> addItems(List<C> customItems) {
        this.originalList.addAll(customItems);
        if(isFiltered)
            return mList;
        return super.addItems(customItems);
    }

    @Override
    public List<C> clearItems() {
        this.originalList.clear();
        return super.clearItems();
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mList = (List<C>) results.values;
                GenericFilterAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<C> filteredResults = null;
                setLoading(false, null);
                if (constraint.length() == 0) {
                    isFiltered = false;
                    filteredResults = originalList;
                } else {
                    isFiltered = true;
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    private List<C> getFilteredResults(String constraint) {
        List<C> results = new ArrayList<>();

        for (C item : originalList) {
            if (item != null && item.toString().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

}