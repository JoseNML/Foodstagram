package wgs01.hva.nl.foodstagrm.viewholder;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.activity.ReplicaRecipeActivity;
import wgs01.hva.nl.foodstagrm.activity.SinglePostActivity;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public CardView profile_cardview;
   public ImageView postImage;
   public Recipe recipe;

    public ProfileViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        profile_cardview = itemView.findViewById(R.id.profile_cardview);
        postImage = itemView.findViewById(R.id.profile_post_image);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ReplicaRecipeActivity.class);

        intent.putExtra("user_id", recipe.getUserId());
        intent.putExtra(Constants.IMG_PARAM, recipe.getImageLink());
        intent.putExtra(Constants.TITLE_PARAM, recipe.getDescription());
        intent.putExtra("username", recipe.getFull_name());
        intent.putExtra("likes", recipe.getLikes());
        intent.putExtra("dislikes", recipe.getDislikes());

        view.getContext().startActivity(intent);
    }
}
