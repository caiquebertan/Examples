package br.com.examples.caique.examplesproject.view.adapter.generic;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class GenericViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected T item;
    protected ItemClickListener<T> listener;

    public GenericViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public GenericViewHolder(View itemView, ItemClickListener<T> listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    public abstract void bind(T item);

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onItemListClick(view, getAdapterPosition(), item);
    }

    public interface ItemClickListener<T> {
        void onItemListClick(View v, int position, T item);
    }
}

