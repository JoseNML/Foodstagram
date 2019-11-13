package wgs01.hva.nl.foodstagrm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.activity.ReplicaRecipeActivity;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.Constants;
import wgs01.hva.nl.foodstagrm.viewholder.SinglePostViewHolder;

public class SinglePostListAdapter extends RecyclerView.Adapter<SinglePostViewHolder>{

    private Context context;
    private List<Recipe> recipeList;

    public SinglePostListAdapter(Context pContext, List<Recipe> itemList){
        this.recipeList = itemList;
        this.context = pContext;
    }

    @NonNull
    @Override
    public SinglePostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_singlepost_item, parent, false);
        SinglePostViewHolder singlePostViewHolder = new SinglePostViewHolder(view);

        return singlePostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SinglePostViewHolder holder, int position) {

        final String pic_url = recipeList.get(position).getImageLink();
        final String pic_id = recipeList.get(position).getImageId();
        final String pic_parent_id = recipeList.get(position).getParentImageId();
        final String username = recipeList.get(position).getFull_name();
        final String likes = String.valueOf(recipeList.get(position).getLikes());
        final String dislikes = String.valueOf(recipeList.get(position).getDislikes());
        final String userId = recipeList.get(position).getUserId();
        final String caption = recipeList.get(position).getDescription();
        recipeList.get(position).getUserId();

        Picasso.get()
                .load(pic_url)
                .into(holder.singlePostItemImage);

        holder.singlePostItemUsername.setText(username);
        holder.singlePostItemLikes.setText(likes);
        holder.singlePostItemDislikes.setText(dislikes);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open another activity on item click
                Intent intent = new Intent(context, ReplicaRecipeActivity.class);
                intent.putExtra(Constants.IMG_PARAM, pic_url);
                intent.putExtra(Constants.IMG_ID, pic_id);
                intent.putExtra(Constants.IMG_PARENT_ID, pic_parent_id);
                intent.putExtra("username", username);
                intent.putExtra("likes", likes);
                intent.putExtra("dislikes", dislikes);
                intent.putExtra(Constants.IMG_USER_CAPTION,caption );
                intent.putExtra("user_id",userId );
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void clearRcView() {
        recipeList.clear();
    }
}
