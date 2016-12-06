package br.com.examples.caique.examplesproject.common.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.common.model.CustomItem;
import br.com.examples.caique.examplesproject.common.model.generic.GenericEntity;
import br.com.examples.caique.examplesproject.common.view.adapter.generic.GenericAdapter;
import br.com.examples.caique.examplesproject.common.view.adapter.generic.GenericViewHolder;
import br.com.examples.caique.examplesproject.simpleLists.endlessList.EndlessScrollerActivity;

public class ListSimpleScrollerAdapter extends GenericAdapter<GenericViewHolder, CustomItem> {

    private final int VT_ITEM = 1;
    private final Context ctx;
    private final Listener listener;

    public ListSimpleScrollerAdapter(Context ctx, Listener listener) {
        this.ctx= ctx;
        this.listener = listener;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType== VT_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item, parent, false);
            return new ItemViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar, parent, false);
            return new ProgressViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final GenericViewHolder holder, int position) {
        CustomItem item = mList.get(position);
        if(holder instanceof ItemViewHolder) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.tv.setText(item.getName());
            Picasso.with(ctx)
                    .load(item.getImage())
                    .fit()
                    .placeholder(R.drawable.ic_close_light)
                    .into(itemHolder.iv);
        }
        else if(holder instanceof GenericAdapter.ProgressViewHolder) {
            GenericAdapter.ProgressViewHolder progressHolder = (GenericAdapter.ProgressViewHolder) holder;
            progressHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? VT_PROGRESS : VT_ITEM;
    }

    private class ItemViewHolder extends GenericViewHolder implements View.OnClickListener{

        private ImageView iv;
        private TextView tv;

        ItemViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            iv = (ImageView) v.findViewById(R.id.ivItem);
            tv = (TextView) v.findViewById(R.id.tvItem);
        }

        @Override
        public void onClick(View view) {
            listener.onItemListClick(view, getAdapterPosition());
        }
    }

    public interface Listener {
        void onItemListClick(View v, int position);
    }

}
