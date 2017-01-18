package br.com.examples.caique.examplesproject.viewexamples.grid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.model.CustomItem;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericAdapter;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericViewHolder;

public class GridAdapter extends GenericAdapter<GenericViewHolder<CustomItem>, CustomItem> {

    private final Context ctx;
    private final GenericViewHolder.ItemClickListener<CustomItem> listener;

    public GridAdapter(Context ctx, GenericViewHolder.ItemClickListener<CustomItem> listener) {
        this.ctx= ctx;
        this.listener = listener;
    }

    @Override
    public GenericViewHolder<CustomItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ItemViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(final GenericViewHolder<CustomItem> holder, int position) {
        holder.bind(mList.get(position));
    }

    private class ItemViewHolder extends GenericViewHolder<CustomItem> {
        private ImageView iv;
        private TextView tv;

        ItemViewHolder(View v, ItemClickListener<CustomItem> listener) {
            super(v, listener);
            iv = (ImageView) v.findViewById(R.id.ivItem);
            tv = (TextView) v.findViewById(R.id.tvItem);
        }

        @Override
        public void bind(CustomItem item) {
            super.item = item;
            tv.setText(item.getName());
            Picasso.with(ctx)
                    .load(item.getImage())
                    .fit()
                    .placeholder(R.drawable.ic_close_light)
                    .into(iv);
        }

    }

}
