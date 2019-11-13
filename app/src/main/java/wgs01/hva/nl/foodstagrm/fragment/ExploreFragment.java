package wgs01.hva.nl.foodstagrm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wgs01.hva.nl.foodstagrm.BaseFragment;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.adapter.LatestAdapter;
import wgs01.hva.nl.foodstagrm.listener.FeedListEventListener;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.network.instagramObject.Data;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class ExploreFragment extends BaseFragment {

    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RecyclerView recyclerView;
    private LatestAdapter rcAdapter;
    private EditText etSearch;
    private List<Recipe> recipes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_explore, container, false);
        init();

        return view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.recycler_view);
        etSearch = view.findViewById(R.id.et_search);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        rcAdapter = new LatestAdapter(getContext(), recipes);

        // Get data from Database
       Constants.foodstagramDB.child(Constants.CHILD_RECIPE).addChildEventListener(new FeedListEventListener(rcAdapter,null,recipes));

        recyclerView.setAdapter(rcAdapter);

        // Set the listener for the "Done" button of the soft keyboard
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                // Don't search if the etSearch is emtpy when pressing the done button
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (etSearch.getText().length() <= 0) {
                        showToastMessage("Enter a search tag", TOAST_SHORT);
                    } else {
                        rcAdapter.clearRcView();
                                 fetchDataFromInstagram(etSearch.getText().toString(), rcAdapter, null);
                            etSearch.setText("");
                            etSearch.clearFocus();

                    }

                    // Close the soft keyboard
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

}
