package br.com.examples.caique.examplesproject.view.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;

import br.com.examples.caique.examplesproject.R;

/**
 * Created by CBertan on 14/11/2016.
 */

public abstract class GenericActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
            setSupportActionBar(toolbar);
        if(getSupportActionBar() != null && !isTaskRoot()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public abstract void onEvent(VolleyError error);


}
