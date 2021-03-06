package br.com.examples.caique.examplesproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.model.CustomItem;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericFilterAdapter;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericViewHolder;

import static br.com.examples.caique.examplesproject.viewexamples.lists.headerList.model.ListItem.VT_ITEM;

public class ListFilterScrollerAdapter extends GenericFilterAdapter<GenericViewHolder<CustomItem>, CustomItem> {

    private final Context ctx;
    private final GenericViewHolder.ItemClickListener<CustomItem> listener;

    public ListFilterScrollerAdapter(Context ctx, GenericViewHolder.ItemClickListener<CustomItem> listener) {
        super(ctx);
        this.ctx= ctx;
        this.listener = listener;
    }

    @Override
    public GenericViewHolder<CustomItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VT_PROGRESS) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_progressbar, parent, false);
            //noinspection unchecked
            return new ProgressViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new ItemViewHolder(v, listener);
        }
    }

    @Override
    public void onBindViewHolder(final GenericViewHolder<CustomItem> holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? VT_PROGRESS : super.getItemViewType(position);
    }

    private class ItemViewHolder extends GenericViewHolder<CustomItem> {

        private ImageView iv;
        private TextView tv;

        ItemViewHolder(View v, ItemClickListener<CustomItem> listener) {
            super(v, listener);
            v.setOnClickListener(this);
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
