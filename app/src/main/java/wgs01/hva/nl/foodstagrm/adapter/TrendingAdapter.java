package wgs01.hva.nl.foodstagrm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.viewholder.TrendingViewHolder;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingViewHolder> {

    private List<Recipe> recipes;

    public TrendingAdapter(List<Recipe> recipes){
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_trending_item, parent, false);
        TrendingViewHolder trendingViewHolder = new TrendingViewHolder(view);

        return trendingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, int position) {

        Picasso.get()
                .load(recipes.get(position).getImageLink())
                .into(holder.trendingImage);

        holder.trendingCaption.setText(recipes.get(position).getDescription());
        holder.recipe = recipes.get(position);
    }

    @Override
    public int getItemCount() {
     return recipes.size();
    }
}
