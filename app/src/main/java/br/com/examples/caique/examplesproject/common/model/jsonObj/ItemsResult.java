package br.com.examples.caique.examplesproject.common.model.jsonObj;

import java.util.List;

import br.com.examples.caique.examplesproject.common.controller.GsonHelper;
import br.com.examples.caique.examplesproject.common.model.CustomItem;
import br.com.examples.caique.examplesproject.common.model.generic.GenericResult;

/**
 * Created by CBertan on 14/11/2016.
 */

public class ItemsResult extends GenericResult<ItemsResult> {

    private List<CustomItem> items;

    public List<CustomItem> getItems() {
        return items;
    }

    public void setItems(List<CustomItem> items) {
        this.items = items;
    }

    @Override
    public ItemsResult parseResult(String json) {
        ItemsResult result = new GsonHelper().getGson().fromJson(json, ItemsResult.class);
        return result.getResult();
    }
}
