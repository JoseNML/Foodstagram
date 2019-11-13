package wgs01.hva.nl.foodstagrm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.BaseFragment;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.activity.AgendaActivity;
import wgs01.hva.nl.foodstagrm.activity.WishlistActivity;
import wgs01.hva.nl.foodstagrm.adapter.ProfileListAdapter;
import wgs01.hva.nl.foodstagrm.listener.ProfilePostListener;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class ProfileFragment extends BaseFragment {

    private List<Recipe> recipes = new ArrayList<>();
    private RecyclerView profilePostList;
    private ProfileListAdapter profilePostListAdapter;

    private ImageView imgView_profilePhoto, popupMenu;
    private TextView txtView_profileName, txtView_profileUsername;
    private PopupMenu popup;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        init();

        initilizeProfilePicture();
        profilePostList.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),3));
        initializeAdapter();

        initializeList();
        return view;
    }

    private void init(){
        profilePostList = view.findViewById(R.id.profile_post_list);

        imgView_profilePhoto = view.findViewById(R.id.profile_image);
        popupMenu = view.findViewById(R.id.popupmenu_profile);

        txtView_profileName = view.findViewById(R.id.profile_name);
        txtView_profileUsername = view.findViewById(R.id.profile_username);

        //Creating the instance of PopupMenu
        popup = new PopupMenu(getContext(), popupMenu);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_profile, popup.getMenu());

        popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_logout:
                                // logout
                                BaseActivity.getInstance().logout(getActivity());
                                return true;
                            default:
                                //default intent
                                return true;
                        }
                    }
                });
                popup.show();//showing popup menu
            }
        });
    }

    private void initializeList() {
        txtView_profileName.setText(verifyString(BaseActivity.getUser().getFullName()));
        txtView_profileUsername.setText(verifyString(BaseActivity.getUser().getUsername()));
        Query profileQuery =  Constants.foodstagramDB.child(Constants.CHILD_SHARED_DISH).child(BaseActivity.getUser().getId());
        profileQuery.addChildEventListener(new ProfilePostListener(profilePostListAdapter , recipes));
    }

    private void initializeAdapter(){
        profilePostListAdapter = new ProfileListAdapter(recipes);
        profilePostList.setAdapter(profilePostListAdapter);
    }

    private void initilizeProfilePicture(){
        Picasso.get()
                .load(BaseActivity.getUser().getProfilePicture())
                .error(R.drawable.salm)
                .into(imgView_profilePhoto);
    }

    private String verifyString(String pString){
        if(pString == null || pString.isEmpty()){
            return "-";
        }
        else
            return pString;
    }
}
