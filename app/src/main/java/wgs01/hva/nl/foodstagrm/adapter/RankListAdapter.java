package wgs01.hva.nl.foodstagrm.adapter;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.viewholder.RankViewHolder;

/**
 * @author José Niemel
 */
public class RankListAdapter extends RecyclerView.Adapter<RankViewHolder> {

    private List<Recipe> recipes;

    public RankListAdapter(List<Recipe> recipes){
        this.recipes = recipes;
    }
    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_ranking_item,parent, false);
        RankViewHolder rvHolder = new RankViewHolder(view);
        return rvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {

        switch (position) {
            case 0:
                holder.rank_item.setBackgroundColor(Color.parseColor("#FFD700"));
                holder.rank_entry_rank.setText("Rank: "+1);
                break;
            case 1:
                holder.rank_item.setBackgroundColor(Color.parseColor("#C0C0C0"));
                holder.rank_entry_rank.setText("Rank: " + 2);
                break;
            case 2:
                holder.rank_item.setBackgroundColor(Color.parseColor("#cd7f32"));
                holder.rank_entry_rank.setText("Rank: " + 3);
                break;
            default:
                holder.rank_item.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.rank_entry_rank.setText("Rank: " + (position + 1) );
        }

        Picasso.get()
                .load(recipes.get(position).getImageLink())
                .into(holder.rank_entry_image);

        holder.rank_entry_name.setText(recipes.get(position).getFull_name());
        holder.rank_entry_score.setText("Score: "+ recipes.get(position).getLikes());
        holder.recipe = recipes.get(position);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

}

