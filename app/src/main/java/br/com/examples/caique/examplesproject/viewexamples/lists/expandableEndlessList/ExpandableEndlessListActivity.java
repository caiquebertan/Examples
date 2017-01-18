package br.com.examples.caique.examplesproject.viewexamples.lists.expandableEndlessList;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.controller.Constants;
import br.com.examples.caique.examplesproject.controller.annotations.EventBusHookSuccess;
import br.com.examples.caique.examplesproject.controller.network.MyStringRequest;
import br.com.examples.caique.examplesproject.controller.network.WebRequestQueue;
import br.com.examples.caique.examplesproject.view.activity.GenericActivity;
import br.com.examples.caique.examplesproject.view.adapter.ListScrollerAdapter;
import br.com.examples.caique.examplesproject.viewexamples.lists.endlessList.listener.EndlessRecyclerViewScrollListener;
import br.com.examples.caique.examplesproject.viewexamples.lists.expandableEndlessList.adapter.ExpandableEndlessAdapter;
import br.com.examples.caique.examplesproject.viewexamples.lists.expandableEndlessList.model.CustomParentItem;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ExpandableEndlessListActivity extends GenericActivity implements ExpandableEndlessAdapter.Listener {

    private static final String TAG = ExpandableEndlessListActivity.class.getSimpleName();
    private RecyclerView rvItems;
    private SwipeRefreshLayout srlRv;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ExpandableEndlessAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        EventBus.getDefault().register(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        adapter = new ExpandableEndlessAdapter(this, this, new ArrayList<CustomParentItem>());
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

        srlRv.setRefreshing(true);
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

        callWebService();
    }

    private void loadNextDataFromApi(int page) {
        adapter.setLoading(true, rvItems);
        callWebService();
    }

    void onItemsLoadComplete(List<CustomParentItem> items) {
        adapter.setLoading(false, rvItems);
        adapter.addItems(items);
        srlRv.setRefreshing(false);
    }

    void refreshItems() {
        WebRequestQueue.getInstance(this).cancelAll(TAG);
        adapter.clearItems();
        scrollListener.resetState();

        callWebService();
    }

    private void callWebService() {
        new MyStringRequest<>(this, TAG, CustomParentItem.class, true).get(Constants.URL_FAKE_ARRAY_EXPANDABLE);
    }

    @Subscribe
    public void onEvent(List<CustomParentItem> parentItems) {
        if (parentItems != null && parentItems.size() > 0) {
            onItemsLoadComplete(parentItems);
        }
    }

    @Subscribe
    public void onEvent(VolleyError error) {
        srlRv.setRefreshing(false);
    }

    @Override
    public void onItemListClick(View v, CustomParentItem child) {
        Toast.makeText(this, child.getName(), Toast.LENGTH_LONG).show();
    }
}
