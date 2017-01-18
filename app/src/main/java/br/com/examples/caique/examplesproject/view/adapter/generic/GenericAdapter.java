package br.com.examples.caique.examplesproject.view.adapter.generic;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.model.generic.GenericEntity;

/**
 * Created by CBertan on 21/11/2016.
 */
public abstract class GenericAdapter<VH extends GenericViewHolder, C> extends RecyclerView.Adapter<VH> {

    protected static final int VT_PROGRESS = 9999;
    protected List<C> mList = new ArrayList<>();
    private boolean isLoading = false;
    protected C progressItem;

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

    public List<C> addItem(C customItem) {
        int lastPosition = mList.size();
        mList.add(customItem);
        notifyItemInserted(lastPosition);
        return mList;
    }

    public List<C> addItems(List<C> customItems) {
        int lastPosition = mList.size();
        mList.addAll(customItems);
        notifyItemRangeInserted(lastPosition, customItems.size());
        return mList;
    }

    public List<C> clearItems() {
        isLoading = false;
        mList.clear();
        mList = new ArrayList<>();
        notifyDataSetChanged();
        return mList;
    }

    /**
     * If your recyclerview is using the endlessScrolling behaviour, call this method once you want to show the loading (true) and when you want to dismiss it (false)
     *
     * @param loading
     * @param rvItems
     */
    public void setLoading(boolean loading, RecyclerView rvItems) {
        if (loading) {
            if (mList != null && !isLoading) {
                isLoading = true;
                mList.add(progressItem);
                rvItems.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        } else {
            if (mList != null && mList.size() > 0) {
                isLoading = false;
                if (getItem(mList.size() - 1) == progressItem) {
                    mList.remove(mList.size() - 1);
                    notifyItemRemoved(mList.size());
                }
            }
        }
    }

    protected class ProgressViewHolder extends GenericViewHolder {
        public ProgressViewHolder(View v) {
            super(v);
        }

        @Override
        public void bind(Object item) {}

    }

}
