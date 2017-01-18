package br.com.examples.caique.examplesproject.model;

import java.io.Serializable;
import java.util.List;

import br.com.examples.caique.examplesproject.model.generic.GenericEntity;

/**
 * Created by CBertan on 14/11/2016.
 */

public class ItemsResult extends GenericEntity implements Serializable {

    private List<CustomItem> items;

    public List<CustomItem> getItems() {
        return items;
    }

    public void setItems(List<CustomItem> items) {
        this.items = items;
    }

}
