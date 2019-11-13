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
import wgs01.hva.nl.foodstagrm.viewholder.ProfileViewHolder;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileViewHolder> {

    private List<Recipe> recipes;

    public ProfileListAdapter(List<Recipe> recipes){
        this.recipes = recipes;
    }
    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_profile_item,parent, false);
        ProfileViewHolder pvHolder = new ProfileViewHolder(view);
        return pvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        Picasso.get()
                .load(recipes.get(position).getImageLink())
                .into(holder.postImage);
        holder.recipe = recipes.get(position);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
