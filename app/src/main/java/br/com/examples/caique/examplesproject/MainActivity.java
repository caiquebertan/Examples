package br.com.examples.caique.examplesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;

import br.com.examples.caique.examplesproject.viewexamples.bottom.BottomSheetActivity;
import br.com.examples.caique.examplesproject.viewexamples.databinding.DataBindingActivity;
import br.com.examples.caique.examplesproject.viewexamples.grid.GridActivity;
import br.com.examples.caique.examplesproject.viewexamples.lists.endlessList.EndlessScrollerActivity;
import br.com.examples.caique.examplesproject.viewexamples.lists.endlessList.EndlessScrollerWithFilterActivity;
import br.com.examples.caique.examplesproject.viewexamples.lists.expandableEndlessList.ExpandableEndlessListActivity;
import br.com.examples.caique.examplesproject.viewexamples.lists.expandableList.ExpandableListActivity;
import br.com.examples.caique.examplesproject.viewexamples.lists.headerList.HeaderListActivity;
import br.com.examples.caique.examplesproject.viewexamples.lists.simpleList.ListActivity;
import br.com.examples.caique.examplesproject.viewexamples.lists.simpleList.ListWithBottomActivity;
import br.com.examples.caique.examplesproject.viewexamples.lists.simpleList.ListWithFilterActivity;
import br.com.examples.caique.examplesproject.view.activity.GenericActivity;
import br.com.examples.caique.examplesproject.viewexamples.progressCustom.CustomProgressActivity;
import br.com.examples.caique.examplesproject.viewexamples.tabs.TabbedActivity;


public class MainActivity extends GenericActivity implements View.OnClickListener {

    private Button btList, btListWithFilter, btListWithBottom, btEndlessList, btEndlessListWithFilter, btExpandableList, btExpandableEndlessList, btHeaderList,
            btDataBinding, btBottom, btGrid, btTabbed, btCustomProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btList = (Button) findViewById(R.id.btList);
        btListWithFilter = (Button) findViewById(R.id.btListWithFilter);
        btListWithBottom = (Button) findViewById(R.id.btListWithBottom);
        btEndlessList = (Button) findViewById(R.id.btEndlessList);
        btEndlessListWithFilter = (Button) findViewById(R.id.btEndlessListWithFilter);
        btExpandableList = (Button) findViewById(R.id.btExpandableList);
        btExpandableEndlessList = (Button) findViewById(R.id.btExpandableEndlessList);
        btHeaderList = (Button) findViewById(R.id.btHeaderList);
        btDataBinding = (Button) findViewById(R.id.btDataBinding);
        btBottom = (Button) findViewById(R.id.btBottom);
        btGrid = (Button) findViewById(R.id.btGrid);
        btTabbed = (Button) findViewById(R.id.btTabbed);
        btCustomProgress = (Button) findViewById(R.id.btCustomProgress);

        btList.setOnClickListener(this);
        btListWithFilter.setOnClickListener(this);
        btListWithBottom.setOnClickListener(this);
        btEndlessList.setOnClickListener(this);
        btEndlessListWithFilter.setOnClickListener(this);
        btExpandableList.setOnClickListener(this);
        btExpandableEndlessList.setOnClickListener(this);
        btHeaderList.setOnClickListener(this);
        btDataBinding.setOnClickListener(this);
        btBottom.setOnClickListener(this);
        btGrid.setOnClickListener(this);
        btTabbed.setOnClickListener(this);
        btCustomProgress.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.equals(btList)) {
            startActivity(new Intent(this, ListActivity.class));
        }
        if(view.equals(btListWithFilter)) {
            startActivity(new Intent(this, ListWithFilterActivity.class));
        }
        if(view.equals(btListWithBottom)) {
            startActivity(new Intent(this, ListWithBottomActivity.class));
        }
        if(view.equals(btEndlessList)) {
            startActivity(new Intent(this, EndlessScrollerActivity.class));
        }
        if(view.equals(btEndlessListWithFilter)) {
            startActivity(new Intent(this, EndlessScrollerWithFilterActivity.class));
        }
        if(view.equals(btExpandableList)) {
            startActivity(new Intent(this, ExpandableListActivity.class));
        }
        if(view.equals(btExpandableEndlessList)) {
            startActivity(new Intent(this, ExpandableEndlessListActivity.class));
        }
        if(view.equals(btHeaderList)) {
            startActivity(new Intent(this, HeaderListActivity.class));
        }
        if(view.equals(btDataBinding)) {
            startActivity(new Intent(this, DataBindingActivity.class));
        }
        if(view.equals(btBottom)) {
            startActivity(new Intent(this, BottomSheetActivity.class));
        }
        if(view.equals(btGrid)) {
            startActivity(new Intent(this, GridActivity.class));
        }
        if(view.equals(btTabbed)) {
            startActivity(new Intent(this, TabbedActivity.class));
        }
        if(view.equals(btCustomProgress)) {
            startActivity(new Intent(this, CustomProgressActivity.class));
        }
    }

    @Override
    public void onEvent(VolleyError error) {

    }
}
