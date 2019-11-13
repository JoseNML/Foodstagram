package wgs01.hva.nl.foodstagrm.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.activity.SinglePostActivity;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class TrendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

     public ImageView trendingImage;
     public TextView trendingCaption;

     public Recipe recipe;

    public TrendingViewHolder(View itemView) {
        super(itemView);
        trendingImage = itemView.findViewById(R.id.trending_image);
        trendingCaption = itemView.findViewById(R.id.trending_caption);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        Toast.makeText(view.getContext(), "clicked positiion: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(view.getContext(), SinglePostActivity.class);

        intent.putExtra(Constants.IMG_ID, recipe.getImageId());
        intent.putExtra(Constants.IMG_PARAM, recipe.getImageLink());
        intent.putExtra(Constants.IMG_USER_CAPTION, (recipe.getDescription()));
        intent.putExtra(Constants.IMG_USER_ID, (recipe.getUserId()));

        view.getContext().startActivity(intent);
    }
}
