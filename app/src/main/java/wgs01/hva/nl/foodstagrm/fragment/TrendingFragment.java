package wgs01.hva.nl.foodstagrm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.Query;

import java.util.ArrayList;

import wgs01.hva.nl.foodstagrm.BaseFragment;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.adapter.TrendingAdapter;
import wgs01.hva.nl.foodstagrm.listener.TrendingListener;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class TrendingFragment extends BaseFragment {

    private final int NUM_COLUMNS = 2;

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private RecyclerView trendingList;
    private TrendingAdapter trendingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trending, container, false);
        trendingList = view.findViewById(R.id.rv_trending);
        initAuth();
        initAdapter();
        initList();

        return view;
    }

    private void initList(){
        Query trendingQuery = Constants.foodstagramDB.child(Constants.CHILD_RECIPE).orderByChild("replies");
        trendingQuery.addChildEventListener(new TrendingListener(trendingAdapter , recipes));
    }

    private void initAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        trendingList.setLayoutManager(layoutManager);
        trendingAdapter = new TrendingAdapter(recipes);
        trendingList.setAdapter(trendingAdapter);
    }
}
