package br.com.examples.caique.examplesproject.viewexamples.lists.expandableList;

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
import br.com.examples.caique.examplesproject.viewexamples.lists.expandableList.adapter.ExpandableAdapter;
import br.com.examples.caique.examplesproject.viewexamples.lists.expandableList.model.CustomParentItem;
import br.com.examples.caique.examplesproject.view.activity.GenericActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ExpandableListActivity extends GenericActivity implements ExpandableAdapter.Listener {

    private static final String TAG = ExpandableListActivity.class.getSimpleName();
    private RecyclerView rvItems;
    private SwipeRefreshLayout srlRv;
    private ExpandableAdapter adapter;

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
        adapter = new ExpandableAdapter(this, this, new ArrayList<CustomParentItem>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
        rvItems.setAdapter(adapter);
        callWebService();
    }

    void onItemsLoadComplete(List<CustomParentItem> items) {
        srlRv.setRefreshing(false);
        adapter.addItems(items);
//        adapter = new ExpandableAdapter(this, this, items);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rvItems.setLayoutManager(linearLayoutManager);
//        rvItems.setAdapter(adapter);
    }

    void refreshItems() {
        WebRequestQueue.getInstance(this).cancelAll(TAG);
        adapter.clearItems();

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
