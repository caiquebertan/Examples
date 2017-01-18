package br.com.examples.caique.examplesproject.viewexamples.progressCustom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.controller.CommonUtils;

public class CustomProgressActivity extends AppCompatActivity {

    private View llVProgresses;
    private View vProgress1;
    private View vProgress2;
    private View vProgress3;
    private TextView tvValue;

    private View llVProgresses2;
    private View vProgress12;
    private View vProgress22;
    private View vProgress32;
    private TextView tvValue2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress);

        initUI();

        loadProgressAnimation(tvValue, vProgress1, vProgress2, vProgress3, 200, 600, 300, 2200, 2500);

        loadProgressAnimation(tvValue2, vProgress12, vProgress22, vProgress32, 400, 1200, 600, 2200, 2500);
    }

    private void initUI() {
        llVProgresses = findViewById(R.id.llVProgresses);
        vProgress1 = findViewById(R.id.vProgress1);
        vProgress2 = findViewById(R.id.vProgress2);
        vProgress3 = findViewById(R.id.vProgress3);
        tvValue = (TextView) findViewById(R.id.tvProgressValue);

        llVProgresses2 = findViewById(R.id.llVProgresses2);
        vProgress12 = findViewById(R.id.vProgress12);
        vProgress22 = findViewById(R.id.vProgress22);
        vProgress32 = findViewById(R.id.vProgress32);
        tvValue2 = (TextView) findViewById(R.id.tvProgressValue2);
    }

    private void loadProgressAnimation(final TextView tvValue, View vProgress1, View vProgress2, View vProgress3, int value1, int value2, int value3, int anchorMax, int duration) {
        int totalValue = value1+value2+value3;
        int duration1 = Math.round(duration / (totalValue / value1));
        int duration2 = Math.round(duration / (totalValue / value2));
        int duration3 = Math.round(duration / (totalValue / value3));

        //Animate View
        //                view,       valueEnd, totalValue, anchorMax, delay,             duration
        loadViewAnimation(vProgress1, value1,   totalValue, anchorMax, 500,               duration1);
        loadViewAnimation(vProgress2, value2,   totalValue, anchorMax, duration1 + 250,   duration2);
        loadViewAnimation(vProgress3, value3,   totalValue, anchorMax, duration2 + 250,   duration3);

        //Animate Text
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, totalValue);
        animator.setDuration(duration + 500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tvValue.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setStartDelay(500);
        animator.start();

    }

    public void loadViewAnimation(final View vPb, int valueEnd, int totalValue, int anchorMax, int delay, int duration) {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - CommonUtils.dip2px(this, 100); //margin

        //adjust the value to be relative to the biggestvalue
        valueEnd = Math.round(valueEnd / (anchorMax / totalValue));

        //Adjust the value to fit screen width
        if(totalValue > width) {
            valueEnd = Math.round((float) valueEnd / ((float) totalValue / width));
        }

        ValueAnimator anim = ValueAnimator.ofInt(0, valueEnd);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = vPb.getLayoutParams();
                layoutParams.width = val;
                vPb.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(duration);
        anim.setStartDelay(delay);
        anim.start();
    }
}
