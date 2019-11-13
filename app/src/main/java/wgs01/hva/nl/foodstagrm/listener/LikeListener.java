package wgs01.hva.nl.foodstagrm.listener;

import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.util.Constants;


public class LikeListener implements View.OnClickListener {

    private TextView tv_likecounter;
    private String postId;
    private String userId;
    private String parentId;

    public LikeListener(TextView tv_likecounter, String postId, String parentId) {
        this.tv_likecounter = tv_likecounter;
        this.postId = postId;
        this.parentId = parentId;
        userId = BaseActivity.getUser().getId();
    }

    @Override
    public void onClick(final View view) {
        Query likeQuery =  Constants.foodstagramDB.child(Constants.CHILD_RECIPE).child(parentId).child(Constants.CHILD_SHARED_DISH).child(postId).child("ratings").orderByValue().equalTo(true);
        likeQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Toast.makeText(view.getContext(), "AANTAL RATINGS!  = "+ dataSnapshot.getChildrenCount()  , Toast.LENGTH_SHORT).show();

                Constants.foodstagramDB.child(Constants.CHILD_RECIPE).child(parentId).child(Constants.CHILD_SHARED_DISH).child(postId).child("likes").setValue(dataSnapshot.getChildrenCount());
                tv_likecounter.setText(dataSnapshot.getChildrenCount() + "");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Constants.foodstagramDB.child(Constants.CHILD_RECIPE).child(parentId).child(Constants.CHILD_SHARED_DISH).child(postId).child("ratings").child(userId).setValue(true);



    }


}

