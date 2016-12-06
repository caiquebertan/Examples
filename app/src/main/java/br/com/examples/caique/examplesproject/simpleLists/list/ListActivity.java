package br.com.examples.caique.examplesproject.simpleLists.list;

import android.os.Bundle;
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
import br.com.examples.caique.examplesproject.common.view.adapter.ListSimpleAdapter;
import de.greenrobot.event.EventBus;

public class ListActivity extends GenericActivity implements ListSimpleAdapter.Listener {

    private static final String TAG = ListActivity.class.getSimpleName();
    private RecyclerView rvItems;
    private SwipeRefreshLayout srlRv;
    private ListSimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_activity);
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
        adapter = new ListSimpleAdapter(this, this);
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

        srlRv.setRefreshing(true);
        new MyStringRequest(this, TAG, new ItemsResult()).makeRequest(Constants.URL_2S);
    }

    void onItemsLoadComplete(List<CustomItem> items) {
        adapter.addItems(items);
        srlRv.setRefreshing(false);
    }

    void refreshItems() {
        WebRequestQueue.getInstance(this).cancelAll(TAG);
        adapter.clearItems();

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
