package wgs01.hva.nl.foodstagrm.listener;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Collections;
import java.util.List;

import wgs01.hva.nl.foodstagrm.adapter.WishListAdapter;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.TimelineComparator;

public class WishListListener implements ChildEventListener {

    private WishListAdapter wishListAdapter;
    private List<Recipe> recipes;

    public WishListListener(WishListAdapter pWishListAdapter, List<Recipe> pRecipes) {
        this.wishListAdapter = pWishListAdapter;
        this.recipes = pRecipes;
    }

    /**
     * vernieuwt de lijst die wordt weergegeven op het scherm
     */
    public void refreshList(){
        wishListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);

        if(recipe.getDescription() != null && !recipe.getDescription().isEmpty()) {
            String editedDescription = recipe.getDescription().replace("#feedmypost", "");

            recipe.setDescription(editedDescription);
        }
        recipes.add(recipe);

        Collections.sort(recipes, new TimelineComparator());
        refreshList();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        for(int i = 0; i < recipes.size(); i++){
            if(recipes.get(i).getImageId().equals(recipe.getImageId())){
                recipes.set(i, recipe);
            }
        }
        refreshList();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        for(int i = 0; i < recipes.size(); i++){
            if(recipes.get(i).getImageId().equals(recipe.getImageId())){
                recipes.remove(i);
            }
        }
        refreshList();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
