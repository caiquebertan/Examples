package br.com.examples.caique.examplesproject.viewexamples.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.controller.Constants;
import br.com.examples.caique.examplesproject.controller.annotations.EventBusHookSuccess;
import br.com.examples.caique.examplesproject.controller.network.MyStringRequest;
import br.com.examples.caique.examplesproject.controller.network.WebRequestQueue;
import br.com.examples.caique.examplesproject.databinding.DataBindingActivityBinding;
import br.com.examples.caique.examplesproject.model.CustomItem;
import br.com.examples.caique.examplesproject.model.ItemsResult;
import br.com.examples.caique.examplesproject.view.activity.GenericActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class DataBindingActivity extends GenericActivity {

    private static final String TAG = DataBindingActivity.class.getSimpleName();
    private CustomItem item;
    private DataBindingActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.data_binding_activity);
//        EventBus.getDefault().register(this);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        initUI();
//        loadUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        WebRequestQueue.getInstance(this).cancelAll(TAG);
        EventBus.getDefault().unregister(this);
    }

    private void initUI() {
    }

    private void loadUI() {
        callWebService();
    }

    private void callWebService() {
        new MyStringRequest<>(this, TAG, ItemsResult.class).get(Constants.URL_FAKE_OBJECT_RESULT);
    }

    @Subscribe
    public void onEvent(ItemsResult result) {
        item = result.getItems().get(0);

        binding.setItem(item);
    }

    @Override
    public void onEvent(VolleyError error) {

    }
}
