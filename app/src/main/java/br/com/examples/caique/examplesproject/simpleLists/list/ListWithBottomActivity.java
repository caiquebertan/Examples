package br.com.examples.caique.examplesproject.simpleLists.list;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import br.com.examples.caique.examplesproject.common.view.bottomsheet.BottomSheetBehaviorGoogleMapsLike;
import br.com.examples.caique.examplesproject.common.view.bottomsheet.MergedAppBarLayoutBehavior;
import de.greenrobot.event.EventBus;


public class ListWithBottomActivity extends GenericActivity implements ListSimpleAdapter.Listener {

    private static final String TAG = ListWithBottomActivity.class.getSimpleName();
    private BottomSheetBehaviorGoogleMapsLike<View> mBottomSheetBehavior;
    private RecyclerView rvItems;
    private SwipeRefreshLayout srlRv;
    private ImageView ivAppBar;

    private ListSimpleAdapter adapter;
    private TextView tvDetail;
    private View bottomSheetView;
    private TextView bottomSheetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_with_bottom_activity);
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
        ivAppBar = (ImageView) findViewById(R.id.ivAppBarDetail);
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        bottomSheetView = findViewById(R.id.nsvBottom);

        mBottomSheetBehavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheetView);

        AppBarLayout mergedAppBarLayout = (AppBarLayout) findViewById(R.id.merged_appbarlayout);
        MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle("Title");
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
            }
        });

        bottomSheetTextView = (TextView) bottomSheetView.findViewById(R.id.tvDetail);
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

        new MyStringRequest(this, TAG, new ItemsResult()).makeRequest(Constants.URL_5S);
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

        CustomItem item = adapter.getItem(position);
        tvDetail.setText(item.getName());
        Picasso.with(this).load(item.getImage()).into(ivAppBar);

        mBottomSheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
        mBottomSheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT);
    }

}
