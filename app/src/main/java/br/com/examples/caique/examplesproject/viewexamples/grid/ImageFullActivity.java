package br.com.examples.caique.examplesproject.viewexamples.grid;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.model.CustomItem;
import br.com.examples.caique.examplesproject.view.activity.GenericActivity;

public class ImageFullActivity extends GenericActivity {

    private static final String TAG = ImageFullActivity.class.getSimpleName();
    public static final String EXTRA_IMAGE = "extra_image";
    public static final String EXTRA_TEXT = "extra_text";
    private ImageView iv;
    private TextView tv;
    private Bitmap image;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            image = extras.getParcelable(EXTRA_IMAGE);
            text = extras.getString(EXTRA_TEXT);
        }

        initUI();
        loadUI();
    }

    private void initUI() {
        iv = (ImageView) findViewById(R.id.ivItem);
        tv = (TextView) findViewById(R.id.tvItem);
        ViewCompat.setTransitionName(iv, "image");
        ViewCompat.setTransitionName(tv, "text");
        iv.setImageBitmap(image);
        tv.setText(text);
    }

    private void loadUI() {

    }

    @Override
    public void onEvent(VolleyError error) {}
}
