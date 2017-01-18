package br.com.examples.caique.examplesproject.viewexamples.lists.expandableList.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericExpandableAdapter;
import br.com.examples.caique.examplesproject.viewexamples.lists.expandableList.model.CustomParentItem;

/**
 * Created by CBertan on 21/11/2016.
 */
public class ExpandableAdapter extends GenericExpandableAdapter<CustomParentItem, CustomParentItem, ExpandableAdapter.MyParentViewHolder, ExpandableAdapter.MyChildViewHolder> {

    private Context ctx;
    private Listener listener;

    public ExpandableAdapter(Context ctx, Listener listener, @NonNull List<CustomParentItem> parentList) {
        super(parentList);
        this.listener = listener;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView = LayoutInflater.from(parentViewGroup.getContext()).inflate(R.layout.item_list, parentViewGroup, false);
        return new MyParentViewHolder(recipeView);
    }

    @NonNull
    @Override
    public MyChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView = LayoutInflater.from(childViewGroup.getContext()).inflate(R.layout.item_list, childViewGroup, false);
        return new MyChildViewHolder(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull MyParentViewHolder parentViewHolder, int position, @NonNull CustomParentItem parentListItem) {
        parentViewHolder.bind(parentListItem);
    }

    @Override
    public void onBindChildViewHolder(@NonNull MyChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull CustomParentItem child) {
        childViewHolder.bind(child);
    }


    class MyParentViewHolder extends ParentViewHolder {

        private TextView tv;
        private ImageView iv;

        MyParentViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tvItem);
            iv = (ImageView) itemView.findViewById(R.id.ivItem);
        }

        public void bind(CustomParentItem recipe) {
            tv.setText(recipe.getName());
            Picasso.with(ctx)
                    .load(recipe.getImage())
                    .fit()
                    .placeholder(R.drawable.ic_close_light)
                    .into(iv);
        }

    }

    class MyChildViewHolder extends ChildViewHolder implements View.OnClickListener {

        private CustomParentItem child;
        private TextView tv;

        MyChildViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv = (TextView) itemView.findViewById(R.id.tvItem);
        }

        public void bind(CustomParentItem child) {
            this.child = child;
            tv.setText(child.getName());
        }

        @Override
        public void onClick(View view) {
            listener.onItemListClick(view, child);
        }
    }

    public interface Listener {
        void onItemListClick(View v, CustomParentItem child);
    }
}