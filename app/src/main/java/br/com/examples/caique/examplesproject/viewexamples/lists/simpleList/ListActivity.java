package br.com.examples.caique.examplesproject.viewexamples.lists.simpleList;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.controller.Constants;
import br.com.examples.caique.examplesproject.controller.annotations.EventBusHookSuccess;
import br.com.examples.caique.examplesproject.controller.network.MyStringRequest;
import br.com.examples.caique.examplesproject.controller.network.WebRequestQueue;
import br.com.examples.caique.examplesproject.model.CustomItem;
import br.com.examples.caique.examplesproject.model.ItemsResult;
import br.com.examples.caique.examplesproject.view.activity.GenericActivity;
import br.com.examples.caique.examplesproject.view.adapter.ListAdapter;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericViewHolder;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ListActivity extends GenericActivity implements GenericViewHolder.ItemClickListener<CustomItem> {

    private static final String TAG = ListActivity.class.getSimpleName();
    private RecyclerView rvItems;
    private SwipeRefreshLayout srlRv;
    private ListAdapter adapter;

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
        adapter = new ListAdapter(this, this);
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
        callWebService();
    }

    void onItemsLoadComplete(List<CustomItem> items) {
        adapter.addItems(items);
        srlRv.setRefreshing(false);
    }

    void refreshItems() {
        WebRequestQueue.getInstance(this).cancelAll(TAG);
        adapter.clearItems();

        callWebService();
    }

    private void callWebService() {
        new MyStringRequest<>(this, TAG, ItemsResult.class).get(Constants.URL_FAKE_OBJECT_RESULT);
    }

    @Subscribe
    public void onEvent(ItemsResult result) {
        if (result != null && result.getItems() != null && result.getItems().size() > 0) {
            onItemsLoadComplete(result.getItems());
        }
    }

    @Subscribe
    public void onEvent(VolleyError error) {
        srlRv.setRefreshing(false);
    }

    @Override
    public void onItemListClick(View v, int position, CustomItem item) {
        Toast.makeText(this, item.getName(), Toast.LENGTH_LONG).show();
    }
}
