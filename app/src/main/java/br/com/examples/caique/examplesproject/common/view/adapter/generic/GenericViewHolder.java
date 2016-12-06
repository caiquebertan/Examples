package br.com.examples.caique.examplesproject.common.view.adapter.generic;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by FBisca on 02/07/2015.
 */
public abstract class GenericViewHolder extends RecyclerView.ViewHolder {

    public GenericViewHolder(View itemView, int viewType) {
        super(itemView);
    }

    public GenericViewHolder(View itemView) {
        super(itemView);
    }


}
