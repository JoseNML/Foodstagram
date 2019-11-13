package wgs01.hva.nl.foodstagrm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.activity.SinglePostActivity;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.network.instagramObject.Data;
import wgs01.hva.nl.foodstagrm.viewholder.RecentViewHolder;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class LatestAdapter extends RecyclerView.Adapter<RecentViewHolder> {

    private List<Recipe> recipe;
    private Context context;

    public LatestAdapter(Context context, List<Recipe> objects) {
        this.context = context;
        this.recipe = objects;
    }

    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_explore_item, null);
        RecentViewHolder rcv = new RecentViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecentViewHolder holder, final int position) {

        Picasso.get()
                .load(recipe.get(position).getImageLink())
                .into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open another activity on item click
                Intent intent = new Intent(context, SinglePostActivity.class);

                intent.putExtra(Constants.IMG_ID, recipe.get(position).getImageId());
                intent.putExtra(Constants.IMG_PARAM, recipe.get(position).getImageLink());
                intent.putExtra(Constants.TITLE_PARAM, (recipe.get(position).getDescription()));
                intent.putExtra(Constants.IMG_USER_ID, (recipe.get(position).getUserId()));
                intent.putExtra(Constants.RECIPE_URL_PARAM, "");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.recipe.size();
    }

    public void clearRcView() {
        recipe.clear();
    }
}
