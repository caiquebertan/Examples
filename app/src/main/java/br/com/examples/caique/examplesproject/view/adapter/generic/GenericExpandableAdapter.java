package br.com.examples.caique.examplesproject.view.adapter.generic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.viewexamples.lists.expandableList.adapter.ExpandableAdapter;

/**
 * Created by CBertan on 21/11/2016.
 */
public abstract class GenericExpandableAdapter<P extends Parent<C>, C, PVH extends ParentViewHolder, CVH extends ChildViewHolder> extends ExpandableRecyclerAdapter<P, C, PVH, CVH> {

    protected static final int VT_PROGRESS = 9999;
    protected P progressItem;
    //    private  List<P> getParentList() = new ArrayList<>();
    private boolean isLoading = false;

    public GenericExpandableAdapter(@NonNull List<P> parentList) {
        super(parentList);
    }

    public P getItem(int position) {
        return getParentList().get(position);
    }

    public List<P> addItem(P parentItem) {
        int lastPosition = getParentList().size();
        getParentList().add(parentItem);
        notifyParentInserted(lastPosition);
        return getParentList();
    }

    public List<P> addItems(List<P> parentItems) {
        int lastPosition = getParentList().size();
        getParentList().addAll(parentItems);
        notifyParentRangeInserted(lastPosition, parentItems.size());
        return getParentList();
    }

    public List<P> clearItems() {
        isLoading = false;
        getParentList().clear();
//        getParentList() = new ArrayList<>();
        notifyParentDataSetChanged(false);
        return getParentList();
    }

    /**
     * If your recyclerview is using the endlessScrolling behaviour, call this method once you want to show the loading (true) and when you want to dismiss it (false)
     * @param loading
     * @param rvItems
     */
    public void setLoading(boolean loading, RecyclerView rvItems) {
        if(loading) {
            if(!isLoading) {
                isLoading = true;
                getParentList().add(progressItem);
                rvItems.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyParentDataSetChanged(false);
                    }
                });
            }
        }
        else {
            if (getParentList().size() > 0) {
                isLoading = false;
                if(getItem(getParentList().size() - 1) == progressItem) {
                    getParentList().remove(getParentList().size() - 1);
                    notifyParentRemoved(getParentList().size());
                }
            }
        }
    }

    protected class ProgressViewHolder extends ParentViewHolder {
        public ProgressViewHolder(View v) {
            super(v);
        }
    }

}
