package wgs01.hva.nl.foodstagrm.listener;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Collections;
import java.util.List;

import wgs01.hva.nl.foodstagrm.adapter.ProfileListAdapter;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.TimelineComparator;

public class ProfilePostListener  implements ChildEventListener {

    private ProfileListAdapter profileListAdapter;
    private List<Recipe> recipes;


    public ProfilePostListener(ProfileListAdapter profileListAdapter, List<Recipe> recipes) {
        this.profileListAdapter = profileListAdapter;
        this.recipes = recipes;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        recipes.add(recipe);
        Collections.sort(recipes,new TimelineComparator());
        profileListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        for(int i = 0; i < recipes.size(); i++){
            if(recipes.get(i).getImageId().equals(recipe.getImageId())){
                recipes.set(i, recipe);
            }
        }
        profileListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        for(int i = 0; i < recipes.size(); i++){
            if(recipes.get(i).getImageId().equals(recipe.getImageId())){
                recipes.remove(i);
            }
        }
        profileListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
