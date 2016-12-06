package br.com.examples.caique.examplesproject.simpleLists.endlessList;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.common.controller.Constants;
import br.com.examples.caique.examplesproject.common.controller.annotations.EventBusHookSuccess;
import br.com.examples.caique.examplesproject.common.controller.network.MyStringRequest;
import br.com.examples.caique.examplesproject.common.controller.network.WebRequestQueue;
import br.com.examples.caique.examplesproject.common.model.CustomItem;
import br.com.examples.caique.examplesproject.common.model.jsonObj.ItemsResult;
import br.com.examples.caique.examplesproject.common.view.activity.GenericActivity;
import br.com.examples.caique.examplesproject.common.view.adapter.ListSimpleScrollerAdapter;
import br.com.examples.caique.examplesproject.simpleLists.endlessList.listener.EndlessRecyclerViewScrollListener;
import de.greenrobot.event.EventBus;

public class EndlessScrollerActivity extends GenericActivity implements ListSimpleScrollerAdapter.Listener {

    private static final String TAG = EndlessScrollerActivity.class.getSimpleName();
    private RecyclerView rvItems;
    private SwipeRefreshLayout srlRv;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ListSimpleScrollerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_activity);
        EventBus.getDefault().register(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initUI();
        loadUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        WebRequestQueue.getInstance(this).cancelAll(TAG);
        EventBus.getDefault().unregister(this);
    }

    private void initUI() {
        adapter = new ListSimpleScrollerAdapter(this, this);
        srlRv = (SwipeRefreshLayout) findViewById(R.id.srlRv);
        rvItems = (RecyclerView) findViewById(R.id.rvItems);
    }

    private void loadUI() {
        srlRv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
        rvItems.setAdapter(adapter);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }

        };
        rvItems.addOnScrollListener(scrollListener);

        srlRv.setRefreshing(true);
        new MyStringRequest(this, TAG, new ItemsResult()).makeRequest(Constants.URL_3S);
    }

    private void loadNextDataFromApi(int page) {
        adapter.setLoading(true, rvItems);
        new MyStringRequest(this, TAG, new ItemsResult()).makeRequest(Constants.URL_3S);
    }

    void onItemsLoadComplete(List<CustomItem> items) {
        adapter.setLoading(false, rvItems);
        adapter.addItems(items);
        srlRv.setRefreshing(false);
    }

    void refreshItems() {
        WebRequestQueue.getInstance(this).cancelAll(TAG);
        adapter.clearItems();
        scrollListener.resetState();

        new MyStringRequest(this, TAG, new ItemsResult()).makeRequest(Constants.URL_3S);
    }

    @EventBusHookSuccess
    public void onEvent(ItemsResult result) {
        if(result != null && result.getItems() != null && result.getItems().size() > 0) {
            onItemsLoadComplete(result.getItems());
        }
    }

    @Override
    public void onItemListClick(View v, int position) {
        Toast.makeText(this, adapter.getItem(position).getName(), Toast.LENGTH_LONG).show();
    }
}
