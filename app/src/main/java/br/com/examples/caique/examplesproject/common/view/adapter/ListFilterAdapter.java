package br.com.examples.caique.examplesproject.common.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.common.model.CustomItem;
import br.com.examples.caique.examplesproject.common.view.adapter.generic.GenericFilterAdapter;
import br.com.examples.caique.examplesproject.common.view.adapter.generic.GenericViewHolder;

import static android.R.id.list;

public class ListFilterAdapter extends GenericFilterAdapter<ListFilterAdapter.ItemViewHolder, CustomItem> {

    private final Context ctx;
    private final Listener listener;

    public ListFilterAdapter(Context ctx, Listener listener) {
        super(ctx);
        this.ctx= ctx;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        CustomItem item = mList.get(position);
        holder.tv.setText(item.getName());
        Picasso.with(ctx)
                .load(item.getImage())
                .fit()
                .placeholder(R.drawable.ic_close_light)
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ItemViewHolder extends GenericViewHolder implements View.OnClickListener{

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
