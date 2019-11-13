package wgs01.hva.nl.foodstagrm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.listener.DislikeListener;
import wgs01.hva.nl.foodstagrm.listener.LikeListener;
import wgs01.hva.nl.foodstagrm.model.User;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class ReplicaRecipeActivity extends BaseActivity {

    private ImageView replicaImage, btn_likes, btn_dislikes, replicaUserProfile;
    private TextView username, likes, dislikes, caption ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replicarecipe);

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void init() {
        replicaImage = findViewById(R.id.replicarecipe_image);
        btn_likes = findViewById(R.id.replicarecipe_btn_like);
        btn_dislikes = findViewById(R.id.replicarecipe_btn_dislike);
        replicaUserProfile = findViewById(R.id.replicarecipe_profile_image);

        caption = findViewById(R.id.replicarecipe_caption);
        username = findViewById(R.id.replicarecipe_username);
        likes = findViewById(R.id.replicarecipe_likes);
        dislikes = findViewById(R.id.replicarecipe_dislikes);

        Intent intent = getIntent(); // get Intent which was set from adapter of Previous Activity

        Picasso.get()
                .load(intent.getStringExtra(Constants.IMG_PARAM))
                .into(replicaImage);

        setUserProfileImage(intent.getStringExtra("user_id"));

        username.setText(intent.getStringExtra("username"));
        likes.setText(intent.getStringExtra("likes"));
        dislikes.setText(intent.getStringExtra("dislikes"));
        caption.setText(intent.getStringExtra(Constants.IMG_USER_CAPTION));

        btn_likes.setOnClickListener(new LikeListener(likes, intent.getStringExtra(Constants.IMG_ID), intent.getStringExtra(Constants.IMG_PARENT_ID)));
        btn_dislikes.setOnClickListener(new DislikeListener(dislikes, intent.getStringExtra(Constants.IMG_ID), intent.getStringExtra(Constants.IMG_PARENT_ID)));
    }

    private void setUserProfileImage(String userId){
        Query profileImageQuery = Constants.foodstagramDB.child("students").child(userId);
        profileImageQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User student = dataSnapshot.getValue(User.class);
                Picasso.get()
                        .load(student.getProfilePicture())
                        .into(replicaUserProfile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
