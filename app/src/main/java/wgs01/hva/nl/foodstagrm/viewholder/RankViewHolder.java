package wgs01.hva.nl.foodstagrm.viewholder;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.activity.SinglePostActivity;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.Constants;

/**
 * @author José Niemel
 * deze class initialiseert de de cardview die in de recyclerview hoort
 */
public class RankViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public CardView rank_item;
    public ImageView rank_entry_image;
    public TextView rank_entry_name;
    public TextView rank_entry_score;
    public TextView rank_entry_rank;
    public Recipe recipe;

    public RankViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        rank_item = itemView.findViewById(R.id.rank_item);
        rank_entry_image = itemView.findViewById(R.id.rank_entry_image);
        rank_entry_name = itemView.findViewById(R.id.rank_entry_name);
        rank_entry_score = itemView.findViewById(R.id.rank_entry_score);
        rank_entry_rank = itemView.findViewById(R.id.rank_entry_rank);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), SinglePostActivity.class);

        intent.putExtra(Constants.IMG_PARAM, recipe.getImageLink());
        intent.putExtra(Constants.TITLE_PARAM, recipe.getDescription());
        intent.putExtra("selected_username", recipe.getFull_name());
        intent.putExtra(Constants.RECIPE_URL_PARAM, recipe.getRecipeUrl());

        view.getContext().startActivity(intent);
    }
}
