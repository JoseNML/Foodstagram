package wgs01.hva.nl.foodstagrm.listener;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Collections;
import java.util.List;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.adapter.TrendingAdapter;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.TrendingComparator;

public class TrendingListener implements ChildEventListener {

    private TrendingAdapter trendingAdapter;
    private List<Recipe> recipes;

    public TrendingListener(TrendingAdapter trendingAdapter, List<Recipe> recipes) {
        this.trendingAdapter = trendingAdapter;
        this.recipes = recipes;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        recipes.add(recipe);
        Collections.sort(recipes,new TrendingComparator());
        trendingAdapter.notifyDataSetChanged();
        BaseActivity.hideSpinner();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        for(int i = 0; i < recipes.size(); i++){
            if(recipes.get(i).getImageId().equals(recipe.getImageId())){
                recipes.set(i, recipe);
            }
        }
        trendingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        for(int i = 0; i < recipes.size(); i++){
            if(recipes.get(i).getImageId().equals(recipe.getImageId())){
                recipes.remove(i);
            }
        }
        trendingAdapter.notifyDataSetChanged();

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
