package wgs01.hva.nl.foodstagrm.util;

import java.util.Comparator;

import wgs01.hva.nl.foodstagrm.model.Recipe;

public class TrendingComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe, Recipe t1) {
        return t1.getReplies() - recipe.getReplies();
    }
}
