package br.com.examples.caique.examplesproject.viewexamples.lists.expandableEndlessList.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.examples.caique.examplesproject.R;
import br.com.examples.caique.examplesproject.view.adapter.generic.GenericExpandableAdapter;
import br.com.examples.caique.examplesproject.viewexamples.lists.expandableEndlessList.model.CustomParentItem;


/**
 * Created by CBertan on 21/11/2016.
 */
public class ExpandableEndlessAdapter extends GenericExpandableAdapter<CustomParentItem, CustomParentItem, ParentViewHolder, ChildViewHolder> {

    private Context ctx;
    private Listener listener;

    public ExpandableEndlessAdapter(Context ctx, Listener listener, @NonNull List<CustomParentItem> parentList) {
        super(parentList);
        super.progressItem = new ProgressItem();
        this.listener = listener;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VT_PROGRESS) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_progressbar, parent, false);
            //noinspection unchecked
            return new ProgressViewHolder(v);
        }
        else {
            View recipeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new MyParentViewHolder(recipeView);
        }
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView = LayoutInflater.from(childViewGroup.getContext()).inflate(R.layout.item_list, childViewGroup, false);
        return new MyChildViewHolder(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ParentViewHolder parentViewHolder, int position, @NonNull CustomParentItem parentListItem) {
        MyParentViewHolder vh = (MyParentViewHolder) parentViewHolder;
        vh.bind(parentListItem);
    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull CustomParentItem child) {
        MyChildViewHolder vh = (MyChildViewHolder) childViewHolder;
        vh.bind(child);
    }

    @Override
    public int getItemViewType(int position) {
        return getParentList().get(position) instanceof ProgressItem ? VT_PROGRESS : super.getItemViewType(position);
    }

    private class MyParentViewHolder extends ParentViewHolder {

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

    private class MyChildViewHolder extends ChildViewHolder implements View.OnClickListener {

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

    private class ProgressItem extends CustomParentItem {
        @Override
        public List getChildList() {
            return null;
        }

        @Override
        public boolean isInitiallyExpanded() {
            return false;
        }
    }
}