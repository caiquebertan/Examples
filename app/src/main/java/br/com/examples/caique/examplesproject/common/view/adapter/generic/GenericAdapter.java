package br.com.examples.caique.examplesproject.common.view.adapter.generic;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import br.com.examples.caique.examplesproject.R;

/**
 * Created by CBertan on 21/11/2016.
 */
public abstract class GenericAdapter<V extends GenericViewHolder, C> extends RecyclerView.Adapter<V> {

    protected static final int VT_PROGRESS = 9999;
    protected List<C> mList = new ArrayList<>();
    public boolean isLoading = false;

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public C getItem(int position) {
        return mList != null && mList.size() > 0 ? mList.get(position) : null;
    }

    public List<C> getList() {
        return mList;
    }

    public List<C> addItems(List<C> customItems) {
        int lastPosition = mList.size();
        mList.addAll(customItems);
        notifyItemRangeInserted(lastPosition, customItems.size());
        return mList;
    }

    public List<C> clearItems() {
//        int size = mList.size();
//        notifyItemRangeRemoved(0, size);
        isLoading = false;
        mList.clear();
        mList = new ArrayList<>();
        notifyDataSetChanged();
        return mList;
    }

    /**
     * If your recyclerview is using the endlessScrolling behaviour, call this method once you want to show the loading (true) and when you want to dismiss it (false)
     * @param loading
     * @param rvItems
     */
    public void setLoading(boolean loading, RecyclerView rvItems) {
        if(loading) {
            if(mList != null && !isLoading) {
                isLoading = true;
                mList.add(null);
                rvItems.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }
        else {
            if (mList != null && mList.size() > 0) {
                isLoading = false;
                if(getItem(mList.size() - 1) == null) {
                    mList.remove(mList.size() - 1);
                    notifyItemRemoved(mList.size());
                }
            }
        }
    }

    protected class ProgressViewHolder extends GenericViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        }
    }

}
