package wgs01.hva.nl.foodstagrm.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.BaseFragment;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.network.InstagramResponse;
import wgs01.hva.nl.foodstagrm.rest.RestClient;
import wgs01.hva.nl.foodstagrm.util.Constants;
import wgs01.hva.nl.foodstagrm.util.DataConverter;

// TODO: CLEAN CODE
public class UploadActivity extends BaseActivity implements View.OnClickListener{

    private final String INSTAGRAM_PACKAGE = "com.instagram.android";
    private final String SHARE_INTENT_TITLE = "Delen naar (kies Feed)";
    private final String TYPE = "image/*";

    private final int REQUIRED_SIZE_IMAGE = 500;

    private RelativeLayout rltLayoutDottedBox;

    private ImageView imgThumbnail;
    private EditText edtRecipe;
    private LinearLayout btn_layout;
    private Button btnChoosePhoto;
    private Button btnNextStep;

    private Bitmap bitmap;
    private Uri selectedImageUri;

    private String urlLastPost, captionLastPost, parentPhotoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        intent = getIntent();
        parentPhotoId = intent.getStringExtra("ParentPhotoId");

        insertImageInImageView(data, requestCode);

        // IF IMAGEVIEW IS NOT EMPTY (if is filled with chosen image from camera or gallery)
        if(imgThumbnail.getDrawable() != null) {
            rltLayoutDottedBox.setVisibility(View.INVISIBLE);
            btn_layout.setVisibility(View.VISIBLE);
            edtRecipe.setVisibility(View.VISIBLE);
        }
        else {
            rltLayoutDottedBox.setVisibility(View.VISIBLE);
            btn_layout.setVisibility(View.INVISIBLE);
            edtRecipe.setVisibility(View.GONE);
        }

        // TODO: If photo is successfully shared with Instagram, then do something
        if (requestCode == SHARE) {
            getMostRecentPostFromInstagram();
            incrementSharedRecipes();
        }
    }

    private void incrementSharedRecipes(){
        Query sharedQuery =  Constants.foodstagramDB.child(Constants.CHILD_RECIPE).child(parentPhotoId).child("shared_dish");
        sharedQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Toast.makeText(getApplicationContext(), "AANTAL COMMENtS!  = "+ dataSnapshot.getChildrenCount()  , Toast.LENGTH_SHORT).show();

                Constants.foodstagramDB.child(Constants.CHILD_RECIPE).child(parentPhotoId).child("replies").setValue(dataSnapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    showToastMessage("GET_ACCOUNTS Denied", TOAST_SHORT);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_photoThumbnail:
                selectImage();
                break;
            case R.id.btn_choosePhoto:
                selectImage();
                break;
            case R.id.btn_nextStep:
                createInstagramIntent(TYPE, selectedImageUri);
                break;
        }
    }

    private void init(){
        rltLayoutDottedBox = findViewById(R.id.rltLayout_dottedBox);
        btn_layout = findViewById(R.id.btn_layout);
        imgThumbnail = findViewById(R.id.img_photoThumbnail);
        edtRecipe = findViewById(R.id.edt_recipe);
        btnChoosePhoto = findViewById(R.id.btn_choosePhoto);
        btnNextStep = findViewById(R.id.btn_nextStep);

        imgThumbnail.setOnClickListener(this);
        btnChoosePhoto.setOnClickListener(this);
        btnNextStep.setOnClickListener(this);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void insertImageInImageView(Intent pData, int pRequestCode){
        // IF IMAGE IS FROM CAMERA
        if(pData != null && pRequestCode == REQUEST_CAMERA){
            bitmap = (Bitmap) pData.getExtras().get("data");
            selectedImageUri = getImageUri(this, bitmap);

            imgThumbnail.setImageBitmap(getUploadUtil()
                    .decodeBitmapFromUri(selectedImageUri, REQUIRED_SIZE_IMAGE, REQUIRED_SIZE_IMAGE));
        }
        // IF IMAGE IS FROM GALLERY, get image and scale image (to avoid 'out memory' error)
        else if(pData != null && pRequestCode == SELECT_FILE){
            selectedImageUri = pData.getData();
            imgThumbnail.setImageBitmap(getUploadUtil()
                    .decodeBitmapFromUri(selectedImageUri, REQUIRED_SIZE_IMAGE, REQUIRED_SIZE_IMAGE));
        }
    }

    private void getMostRecentPostFromInstagram(){

        // TODO: GET ACCESS_TOKEN THROUGH ACTIVITY
        Call<InstagramResponse> call = RestClient.getRetrofitService().getRecentPhoto(BaseFragment.getAccess());

        call.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {
                if (response.body() != null) {
                    for(int i = 0; i < response.body().getData().length; i++){

                        if(response.body().getData()[i].getCaption() != null){
                            // TODO: Make captionLastPost.contains not uppercase sensitive (.contains is case sensitive)
                            captionLastPost = response.body().getData()[i].getCaption().getText().toLowerCase();
                        }

                        // Get url of last posted image in Instagram
                        urlLastPost = response.body().getData()[i].getImages().getStandard_resolution().getUrl();
                        Log.d("URL OF LAST POST",response.body().getData()[i].getImages().getStandard_resolution().getUrl());

                        /* TODO: Check if URL is still valid, if is valid then add in own Database (FireBase)
                            > With all other data: recipe, picture, likes, dislikes and user information
                         */

                        Recipe recipe = DataConverter.convertToPost(response.body().getData()[i]);
                        recipe.setCreatedTime(response.body().getData()[i].getCaption().getCreated_time());
                        recipe.setUserId(BaseActivity.getUser().getId());
                        recipe.setParentImageId(parentPhotoId);

                        if(isValidUrl(edtRecipe.getText().toString()))
                            recipe.setRecipeUrl(edtRecipe.getText().toString());

                        Constants.foodstagramDB.child(Constants.CHILD_RECIPE)
                                                .child(parentPhotoId)
                                                .child(Constants.CHILD_SHARED_DISH)
                                                .child(recipe.getImageId()).setValue(recipe);

                        Constants.foodstagramDB.child(Constants.CHILD_SHARED_DISH)
                                .child(BaseActivity.getUser().getId())
                                .child(recipe.getImageId()).setValue(recipe);
                    }
                }
                else showToastMessage("emptyy", TOAST_SHORT);
            }

            @Override
            public void onFailure(Call<InstagramResponse> call, Throwable t) {
                showToastMessage(t.toString(), TOAST_SHORT);
            }
        });
    }

    /**
     *
     * Open the share-box to Instagram
     */
    private void createInstagramIntent(String type, Uri media){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type (images in this case)
        share.setType(type);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, media);
        share.setPackage(INSTAGRAM_PACKAGE);

        /*
            If Instagram is installed on device, open Instagram with image which will be shared.
            Else open Play Store to install Instagram
            Else open Play Store to install Instagram
         */
        if(InstagramInstalledOnDevice()){
            // Broadcast the Intent.
            startActivityForResult(Intent.createChooser(share, SHARE_INTENT_TITLE), SHARE);
        }
        else{
            openInstagramInMarket();
        }
    }

    // TODO: MOVE BELOW METHODS TO APPROPRIATE CLASS

    // Open Play Store to install Instagram
    private void openInstagramInMarket(){
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Installeer Instagram")
                .setMessage("U heeft geen Instagram op uw apparaat, wilt u die installeren?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+INSTAGRAM_PACKAGE));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private boolean InstagramInstalledOnDevice(){
        intent = getPackageManager().getLaunchIntentForPackage(INSTAGRAM_PACKAGE);

        if(intent == null) {
            return false;
        }
        return true;
    }

    /* Returns true if url is valid */
    public static boolean isValidUrl(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}
