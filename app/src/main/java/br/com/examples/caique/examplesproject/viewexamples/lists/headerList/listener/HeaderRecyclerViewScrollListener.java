package br.com.examples.caique.examplesproject.viewexamples.lists.headerList.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class HeaderRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = HeaderRecyclerViewScrollListener.class.getSimpleName();
    private int mViewTypeHeader;

    private RecyclerView mRv;
    private LinearLayoutManager mLayoutManager;

    public HeaderRecyclerViewScrollListener(RecyclerView rv, LinearLayoutManager layoutManager, int viewTypeHeader) {
        this.mRv = rv;
        this.mLayoutManager = layoutManager;
        this.mViewTypeHeader = viewTypeHeader;
    }

    public HeaderRecyclerViewScrollListener(RecyclerView rv, GridLayoutManager layoutManager, int viewTypeHeader) {
        this.mRv = rv;
        this.mLayoutManager = layoutManager;
        this.mViewTypeHeader = viewTypeHeader;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        int itemViewType = mRv.getAdapter().getItemViewType(firstVisibleItemPosition);
        if(itemViewType == mViewTypeHeader) {
            headerOnTop(firstVisibleItemPosition);
        }
    }

    // Defines the process for actually loading more data based on page
    public abstract void headerOnTop(int position);
}