package br.com.examples.caique.examplesproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.examples.caique.examplesproject.bottom.BottomSheetActivity;
import br.com.examples.caique.examplesproject.common.view.activity.GenericActivity;
import br.com.examples.caique.examplesproject.databinding.simple.DataBindingActivity;
import br.com.examples.caique.examplesproject.simpleLists.endlessList.EndlessScrollerActivity;
import br.com.examples.caique.examplesproject.simpleLists.endlessList.EndlessScrollerWithFilterActivity;
import br.com.examples.caique.examplesproject.simpleLists.list.ListActivity;
import br.com.examples.caique.examplesproject.simpleLists.list.ListWithBottomActivity;
import br.com.examples.caique.examplesproject.simpleLists.list.ListWithFilterActivity;


public class MainActivity extends GenericActivity implements View.OnClickListener {

    private Button btList, btListWithFilter, btListWithBottom, btEndlessList, btEndlessListWithFilter, btDataBinding, btBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btList = (Button) findViewById(R.id.btList);
        btListWithFilter = (Button) findViewById(R.id.btListWithFilter);
        btListWithBottom = (Button) findViewById(R.id.btListWithBottom);
        btEndlessList = (Button) findViewById(R.id.btEndlessList);
        btEndlessListWithFilter = (Button) findViewById(R.id.btEndlessListWithFilter);
        btDataBinding = (Button) findViewById(R.id.btDataBinding);
        btBottom = (Button) findViewById(R.id.btBottom);

        btList.setOnClickListener(this);
        btListWithFilter.setOnClickListener(this);
        btListWithBottom.setOnClickListener(this);
        btEndlessList.setOnClickListener(this);
        btEndlessListWithFilter.setOnClickListener(this);
        btDataBinding.setOnClickListener(this);
        btBottom.setOnClickListener(this);
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
        if(view.equals(btDataBinding)) {
            startActivity(new Intent(this, DataBindingActivity.class));
        }
        if(view.equals(btBottom)) {
            startActivity(new Intent(this, BottomSheetActivity.class));
        }
    }
}
