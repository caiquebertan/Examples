package br.com.examples.caique.examplesproject.viewexamples.bottom;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.controller.Constants;
import br.com.examples.caique.examplesproject.controller.annotations.EventBusHookSuccess;
import br.com.examples.caique.examplesproject.controller.network.MyStringRequest;
import br.com.examples.caique.examplesproject.controller.network.WebRequestQueue;
import br.com.examples.caique.examplesproject.model.CustomItem;
import br.com.examples.caique.examplesproject.model.ItemsResult;
import br.com.examples.caique.examplesproject.view.activity.GenericActivity;
import br.com.examples.caique.examplesproject.view.bottomsheet.BottomSheetBehaviorGoogleMapsLike;
import br.com.examples.caique.examplesproject.view.bottomsheet.MergedAppBarLayoutBehavior;

public class BottomSheetActivity extends GenericActivity implements View.OnClickListener {

    private static final String TAG = BottomSheetActivity.class.getSimpleName();
    private BottomSheetBehaviorGoogleMapsLike<View> mBottomSheetBehavior;
    private SwipeRefreshLayout srlRv;
    private ImageView ivAppBar;

    private Button btItem1;
    private TextView tvDetail;
    private View bottomSheetView;
    private TextView bottomSheetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
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
        ivAppBar = (ImageView) findViewById(R.id.ivAppBarDetail);
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        bottomSheetView = findViewById(R.id.nsvBottom);
        btItem1 = (Button) findViewById(R.id.btBottomItem1);

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
        btItem1.setOnClickListener(this);
        srlRv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
        srlRv.setRefreshing(true);

        callWebService();
    }

    void onItemsLoadComplete(List<CustomItem> items) {
        srlRv.setRefreshing(false);
    }

    void refreshItems() {
        WebRequestQueue.getInstance(this).cancelAll(TAG);
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

    @Override
    public void onClick(View view) {
        CustomItem item = new CustomItem();
        item.setName("Item 1");
        item.setImage("http://www.bloodygoodhorror.com/bgh/files/reviews/caps/vampires-kiss.jpg");
        tvDetail.setText(item.getName());
        Picasso.with(this).load(item.getImage()).into(ivAppBar);

        mBottomSheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT);
    }

    @Override
    public void onEvent(VolleyError error) {

    }
}
