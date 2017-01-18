package br.com.examples.caique.examplesproject.viewexamples.lists.headerList;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.controller.Constants;
import br.com.examples.caique.examplesproject.controller.annotations.EventBusHookSuccess;
import br.com.examples.caique.examplesproject.controller.network.MyStringRequest;
import br.com.examples.caique.examplesproject.controller.network.WebRequestQueue;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericViewHolder;
import br.com.examples.caique.examplesproject.viewexamples.lists.headerList.adapter.HeaderListAdapter;
import br.com.examples.caique.examplesproject.viewexamples.lists.headerList.listener.HeaderRecyclerViewScrollListener;
import br.com.examples.caique.examplesproject.viewexamples.lists.headerList.model.BodyItem;
import br.com.examples.caique.examplesproject.viewexamples.lists.headerList.model.HeaderItem;
import br.com.examples.caique.examplesproject.viewexamples.lists.headerList.model.ListItem;
import br.com.examples.caique.examplesproject.view.activity.GenericActivity;

public class HeaderListActivity extends GenericActivity implements GenericViewHolder.ItemClickListener<ListItem> {

    private static final String TAG = HeaderListActivity.class.getSimpleName();
    private RecyclerView rvItems;
    private SwipeRefreshLayout srlRv;
    private HeaderListAdapter adapter;

    private ImageView ivHeader;
    private TextView tvHeader;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        EventBus.getDefault().register(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        loadUI();
        initListeners();
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
        ivHeader = (ImageView) findViewById(R.id.ivItem);
        tvHeader = (TextView) findViewById(R.id.tvItem);
    }

    private void loadUI() {
        srlRv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
        srlRv.setRefreshing(true);

        adapter = new HeaderListAdapter(this, this);
        mLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(mLayoutManager);
        rvItems.setAdapter(adapter);

        callWebService();
    }

    private void initListeners() {
        rvItems.addOnScrollListener(new HeaderRecyclerViewScrollListener(rvItems, mLayoutManager, ListItem.VT_HEADER) {
            @Override
            public void headerOnTop(int position) {
                ListItem item = adapter.getItem(position);
                if(item instanceof HeaderItem) {
                    HeaderItem hItem = (HeaderItem) item;
                    tvHeader.setText(hItem.getName());
                }
            }
        });
    }

    void onItemsLoadComplete(List<BodyItem> items) {
        srlRv.setRefreshing(false);
        List<ListItem> var = (List<ListItem>)(List<?>) items;

        HeaderItem headerItem = new HeaderItem();
        headerItem.setName("HEADER");
        adapter.addItem(headerItem);
        adapter.addItems(var);

        headerItem = new HeaderItem();
        headerItem.setName("HEADER 2");
        adapter.addItem(headerItem);
        adapter.addItems(var);

        headerItem = new HeaderItem();
        headerItem.setName("HEADER 3");
        adapter.addItem(headerItem);
        adapter.addItems(var);

        headerItem = new HeaderItem();
        headerItem.setName("HEADER 4");
        adapter.addItem(headerItem);
        adapter.addItems(var);


        tvHeader.setText("Header");
    }

    void refreshItems() {
        WebRequestQueue.getInstance(this).cancelAll(TAG);
        adapter.clearItems();

        callWebService();
    }

    private void callWebService() {
        new MyStringRequest<>(this, TAG, BodyItem.class, true).get(Constants.URL_FAKE_ARRAY);
    }

    @Subscribe
    public void onEvent(List<BodyItem> parentItems) {
        if (parentItems != null && parentItems.size() > 0) {
            onItemsLoadComplete(parentItems);
        }
    }

    @Subscribe
    public void onEvent(VolleyError error) {
        srlRv.setRefreshing(false);
    }

    @Override
    public void onItemListClick(View v, int position, ListItem item) {
        if(item instanceof HeaderItem) {
            HeaderItem hItem = (HeaderItem) item;
            Toast.makeText(this, "Header: " + hItem.getName(), Toast.LENGTH_LONG).show();
        }
        else if (item instanceof BodyItem) {
            BodyItem bItem = (BodyItem) item;
            Toast.makeText(this, "Body: " + bItem.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
