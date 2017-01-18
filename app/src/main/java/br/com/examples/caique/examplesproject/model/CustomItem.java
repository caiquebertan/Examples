package br.com.examples.caique.examplesproject.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.model.generic.GenericEntity;

/**
 * Created by CBertan on 14/11/2016.
 */

public class CustomItem extends GenericEntity implements Serializable {

    private String name;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return getName();
    }

    @BindingAdapter({"bind:image"})
    public static void loadImage(ImageView view, String image) {
        Picasso.with(view.getContext())
                .load(image)
                .placeholder(R.drawable.ic_close_light)
                .into(view);
    }

}
