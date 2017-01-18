package br.com.examples.caique.examplesproject.viewexamples.lists.headerList.model;

import java.io.Serializable;

import br.com.examples.caique.examplesproject.model.generic.GenericEntity;

/**
 * Created by CBertan on 14/11/2016.
 */

public class BodyItem extends GenericEntity implements Serializable, ListItem {

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

    @Override
    public int getType() {
        return VT_ITEM;
    }
}
