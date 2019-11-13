package wgs01.hva.nl.foodstagrm.listener;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Collections;
import java.util.List;

import wgs01.hva.nl.foodstagrm.adapter.RankListAdapter;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.RankingComparator;

public class RankingListener implements ChildEventListener {

    private RankListAdapter rankListAdapter;
    private List<Recipe> recipes;

    public RankingListener(RankListAdapter rankListAdapter, List<Recipe> recipes) {
        this.rankListAdapter = rankListAdapter;
        this.recipes = recipes;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        String editedDescription = recipe.getDescription().replace("#feedmypost", "");
        recipe.setDescription(editedDescription);
        recipes.add(recipe);
        Collections.sort(recipes,new RankingComparator());
        rankListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        for(int i = 0; i < recipes.size(); i++){
            if(recipes.get(i).getImageId().equals(recipe.getImageId())){
                recipes.set(i, recipe);
            }
        }
        rankListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        for(int i = 0; i < recipes.size(); i++){
            if(recipes.get(i).getImageId().equals(recipe.getImageId())){
                recipes.remove(i);
            }
        }
        rankListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
