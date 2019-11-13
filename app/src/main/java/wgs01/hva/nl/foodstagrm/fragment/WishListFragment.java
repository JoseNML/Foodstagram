package wgs01.hva.nl.foodstagrm.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.BaseFragment;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.adapter.WishListAdapter;
import wgs01.hva.nl.foodstagrm.listener.WishListListener;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class WishListFragment extends BaseFragment {

    private RecyclerView wishlist;
    private WishListAdapter wishListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        wishlist = view.findViewById(R.id.rcv_wishlist);

        initWishListItems();
        init();
        return view;
    }

    private void init(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        wishlist.setLayoutManager(layoutManager);
        wishlist.setAdapter(wishListAdapter);
    }

    private void initWishListItems(){
        wishListAdapter = new WishListAdapter(getContext(), recipe);

        // TODO: get wishlist-items from FireBase
        Query rankingQuery = Constants.foodstagramDB.child(Constants.CHILD_WISHLIST).orderByChild("wishedBy").equalTo(BaseActivity.getUser().getId());
        rankingQuery.addChildEventListener(new WishListListener(wishListAdapter, recipe));

//        Query rankingQuery = Constants.foodstagramDB.child(Constants.CHILD_RECIPE).orderByChild("likes").limitToFirst(10);
        //        recipe.add(new Recipe("dsadas342", "Vegan recipe","12356789","https://scontent-ams3-1.cdninstagram.com/vp/0c7823703cec817f6a6affbe94f9ab69/5BA9A17A/t51.2885-15/e35/33711892_1967459439931493_5411019152070541312_n.jpg", "" ));
    }
}
