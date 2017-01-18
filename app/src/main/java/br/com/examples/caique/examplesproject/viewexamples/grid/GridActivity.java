package br.com.examples.caique.examplesproject.viewexamples.grid;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericViewHolder;
import br.com.examples.caique.examplesproject.viewexamples.grid.adapter.GridAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class GridActivity extends GenericActivity implements GenericViewHolder.ItemClickListener<CustomItem> {

    private static final String TAG = GridActivity.class.getSimpleName();
    private RecyclerView rvItems;
    private SwipeRefreshLayout srlRv;
    private GridAdapter adapter;

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
        adapter = new GridAdapter(this, this);
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

        GridLayoutManager lm = new GridLayoutManager(this, 3);
        rvItems.setLayoutManager(lm);
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
        ImageView iv = (ImageView) v.findViewById(R.id.ivItem);
        TextView tv = (TextView) v.findViewById(R.id.tvItem);

        ViewCompat.setTransitionName(iv, "image");
        ViewCompat.setTransitionName(tv, "text");
        Pair<View, String> p1 = Pair.create((View) iv, "image");
        Pair<View, String> p2 = Pair.create((View) tv, "text");

        Intent intent = new Intent(this, ImageFullActivity.class);
        iv.buildDrawingCache();
        intent.putExtra(ImageFullActivity.EXTRA_IMAGE, iv.getDrawingCache());
        intent.putExtra(ImageFullActivity.EXTRA_TEXT, tv.getText());


        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
}
