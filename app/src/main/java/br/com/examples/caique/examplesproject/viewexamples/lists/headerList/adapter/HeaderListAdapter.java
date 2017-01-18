package br.com.examples.caique.examplesproject.viewexamples.lists.headerList.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericAdapter;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericViewHolder;
import br.com.examples.caique.examplesproject.viewexamples.lists.headerList.model.BodyItem;
import br.com.examples.caique.examplesproject.viewexamples.lists.headerList.model.HeaderItem;
import br.com.examples.caique.examplesproject.viewexamples.lists.headerList.model.ListItem;

public class HeaderListAdapter extends GenericAdapter<GenericViewHolder<ListItem>, ListItem> {

    private final Context ctx;
    private GenericViewHolder.ItemClickListener<ListItem> listener;

    public HeaderListAdapter(Context ctx, GenericViewHolder.ItemClickListener<ListItem> listener) {
        this.ctx= ctx;
        this.listener = listener;
    }

    @Override
    public GenericViewHolder<ListItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListItem.VT_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new HeaderViewHolder(itemView, listener);
        }
        else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new ItemViewHolder(itemView, listener);
        }
    }

    @Override
    public void onBindViewHolder(final GenericViewHolder<ListItem> holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    private class HeaderViewHolder extends GenericViewHolder<ListItem>{

        private ImageView iv;
        private TextView tv;

        HeaderViewHolder(View v, ItemClickListener<ListItem> listener) {
            super(v, listener);
            iv = (ImageView) v.findViewById(R.id.ivItem);
            tv = (TextView) v.findViewById(R.id.tvItem);
        }

        @Override
        public void bind(ListItem item) {
            super.item = item;
            HeaderItem hItem = (HeaderItem) item;
            tv.setText(hItem.getName());
            Picasso.with(ctx)
                    .load(hItem.getImage())
                    .fit()
                    .placeholder(R.drawable.ic_close_light)
                    .into(iv);
        }

    }

    private class ItemViewHolder extends GenericViewHolder<ListItem>{

        private TextView tv;

        ItemViewHolder(View v, ItemClickListener<ListItem> listener) {
            super(v, listener);
            tv = (TextView) v.findViewById(R.id.tvItem);
        }

        @Override
        public void bind(ListItem item) {
            super.item = item;
            BodyItem bodyItem = (BodyItem) item;
            tv.setText(bodyItem.getName());
        }

    }

}
