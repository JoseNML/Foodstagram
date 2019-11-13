package wgs01.hva.nl.foodstagrm.listener;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Collections;
import java.util.List;

import wgs01.hva.nl.foodstagrm.adapter.SuggestionListViewAdapter;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.TimelineComparator;

/**
 * @author José Niemel
 * Deze class koppelt de database met een lijst zodat als de lijst wordt aangepast
 * dat de data in de database ook aangepast wordt en vice versa
 */
public class FeedListEventListener implements ChildEventListener {

    private RecyclerView.Adapter<?> recyclerViewAdapter;
    private SuggestionListViewAdapter listViewAdapter;
    private List<Recipe> recipes;

    public FeedListEventListener(final RecyclerView.Adapter<?> recyclerViewAdapter, final SuggestionListViewAdapter listViewAdapter, List<Recipe> recipes) {
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.listViewAdapter = listViewAdapter;
        this.recipes = recipes;
    }

    /**
     * vernieuwt de lijst die wordt weergegeven op het scherm
     */
    public void refreshList(){
        if(recyclerViewAdapter == null){
            listViewAdapter.notifyDataSetChanged();
        }
        else{
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     *  Deze method zorgt ervoor dat als een item aan de database wordt toegevoegd
     *  dat die ook wordt weergegeven op het scherm
     * @param dataSnapshot object die terug komt uit de database
     * @param s
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);

        String editedDescription = recipe.getDescription().replace("#feedmypost", "");
        recipe.setDescription(editedDescription);
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

