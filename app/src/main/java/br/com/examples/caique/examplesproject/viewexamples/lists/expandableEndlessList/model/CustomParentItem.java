package br.com.examples.caique.examplesproject.viewexamples.lists.expandableEndlessList.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.io.Serializable;
import java.util.List;

import br.com.examples.caique.examplesproject.model.generic.GenericEntity;

/**
 * Created by CBertan on 14/11/2016.
 */

public class CustomParentItem extends GenericEntity implements Serializable, Parent<CustomParentItem> {

    private String name;
    private String image;
    private List<CustomParentItem> childItems;

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
    public List<CustomParentItem> getChildList() {
        return childItems;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
