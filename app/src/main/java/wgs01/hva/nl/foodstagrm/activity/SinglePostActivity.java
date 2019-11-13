package wgs01.hva.nl.foodstagrm.activity;

import android.content.Intent;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.adapter.SinglePostListAdapter;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class SinglePostActivity extends BaseActivity implements View.OnClickListener{

    static boolean active = false;

    private Calendar myCalendar = Calendar.getInstance();
    private Context context = this;

    private Recipe recipe;
    private Query existQuery;
    private String photo_id, photo_createdTime, photo_url, photo_description, photo_user_id, photo_user_name, photo_recipe_url;

    private ImageView selectedImage, calendarBtn, addReplica, addWish;
    private RecyclerView singlePostList;
    private TextView txt, txt_label_recipes, txt_username, txt_recipe;
    private ScrollView scroll_recipe;

    private SinglePostListAdapter singlePostListAdapter;

    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepost);

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pin_agenda:
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_add_replica:
                openUpload();
                break;
            case R.id.btn_add_wish:
                addToWish();
                break;
        }
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

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }

    private void init() {
        selectedImage = findViewById(R.id.selectedImage);
        calendarBtn = findViewById(R.id.btn_pin_agenda);
        addReplica = findViewById(R.id.btn_add_replica);
        addWish = findViewById(R.id.btn_add_wish);

        txt = findViewById(R.id.txt_test);
        scroll_recipe = findViewById(R.id.scroll_recipe);
        txt_recipe = findViewById(R.id.txt_recipe);
        singlePostList = findViewById(R.id.rv_single_post);
        txt_label_recipes = findViewById(R.id.txt_label_recipes);

        calendarBtn.setOnClickListener(this);
        addReplica.setOnClickListener(this);
        addWish.setOnClickListener(this);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_recipe.setText(getIntent().getStringExtra(Constants.IMG_USER_CAPTION));
                scroll_recipe.setVisibility(View.VISIBLE);
                singlePostList.setVisibility(View.GONE);
                txt.setVisibility(View.GONE);
            }
        });
        /**
         *      Get Intent from LatestAdapter (imageLink and Description of recipe)
         *      Then set description as title and use imageLink in ImageView
         */
        intent = getIntent();

        photo_id = intent.getStringExtra(Constants.IMG_ID);
        photo_createdTime = "";
        photo_url = intent.getStringExtra(Constants.IMG_PARAM);
        photo_description = intent.getStringExtra(Constants.TITLE_PARAM);
        photo_user_id = intent.getStringExtra(Constants.IMG_USER_ID);
        photo_user_name = intent.getStringExtra("selected_username");
        photo_recipe_url = intent.getStringExtra(Constants. RECIPE_URL_PARAM);

        setTitle(intent.getStringExtra(photo_description));
        txt_recipe.setText(intent.getStringExtra(photo_description));
        Picasso.get()
                .load(photo_url)
                .into(selectedImage);

        // TODO: IF @photo_id is in Firebase then continue, else first insert in FireBase
        addToFireBase();
        // TODO: IF @photo_id is in Firebase (nagemaakte recepten-section) then show them, else show message "nog geen gerechten"
        hasReplicaRecipes();
    }

    private void updateDate(){
        String currentDate = sdf.format(myCalendar.getTime());
        showToastMessage(currentDate, TOAST_SHORT);
        addToCalendar(currentDate);
    }

    private void addToFireBase(){
        recipe = new Recipe(photo_user_name, photo_description, photo_id, photo_url, "");
        recipe.setUserId(photo_user_id);

        existQuery = Constants.foodstagramDB.child(Constants.CHILD_RECIPE).orderByChild(Constants.CHILD_IMAGE_ID).equalTo(photo_id);
        existQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists() && active){
                    showToastMessage("Bestaat niet in db", TOAST_SHORT);
                    // Add to FireBase into Recipe-section
                    Constants.foodstagramDB.child(Constants.CHILD_RECIPE).child(recipe.getImageId()).setValue(recipe);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addToCalendar(String currentDate){
        Constants.foodstagramDB.child(Constants.CHILD_CALENDAR).child(BaseActivity.getUser().getId()).child(recipe.getImageId()).setValue(recipe);
        Constants.foodstagramDB.child(Constants.CHILD_CALENDAR).child(BaseActivity.getUser().getId()).child(recipe.getImageId()).child("date").setValue(currentDate);
    }

    private void hasReplicaRecipes(){

        // Check if Recipe has Replica Recipes (Recipes which are shared based on Instagram photo)
        existQuery = Constants.foodstagramDB.child(Constants.CHILD_RECIPE)
                                            .child(photo_id)
                                            .child(Constants.CHILD_SHARED_DISH)
                                            .orderByChild(Constants.CHILD_IMAGE_PARENT_ID)
                                            .equalTo(photo_id);

        existQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // SHOW ALL NAGEMAAKTE RECEPTEN
                    if(singlePostList.getVisibility() == View.GONE)
                        displayReplicaRecipes();
                    txt.setVisibility(View.GONE);
                }
                else {
                    txt.setVisibility(View.VISIBLE);
                    txt.setText("Er zijn nog geen nagemaakte recepten");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayReplicaRecipes(){
        singlePostList.setVisibility(View.VISIBLE);
        txt_label_recipes.setVisibility(View.VISIBLE);
        txt.setVisibility(View.GONE);

        initAdapter();
    }

    public void initAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        singlePostList.setLayoutManager(layoutManager);

        singlePostListAdapter = new SinglePostListAdapter(this, recipes);
        fetchReplicaRecipeFromFireBase(photo_id, singlePostListAdapter, null);
        singlePostList.setAdapter(singlePostListAdapter);
    }

    private void openUpload(){
        Intent intent = new Intent(this, UploadActivity.class);
        intent.putExtra("ParentPhotoId", photo_id);
        startActivity(intent);
    }

    private void addToWish(){
        String unique_wishlist_child = getUser().getId() + "_" + photo_id;
        recipe.setWishedBy(BaseActivity.getUser().getId());

        Constants.foodstagramDB.child(Constants.CHILD_WISHLIST)
                .child(unique_wishlist_child).setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showToastMessage("Dit gerecht is aan je wish toegevoegd bro", TOAST_SHORT);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToastMessage("Oeps.. iets is misgegaan tijdens het toevoegen van dit gerecht", TOAST_SHORT);
            }
        });
    }
}
