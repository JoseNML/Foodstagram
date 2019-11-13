package wgs01.hva.nl.foodstagrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;

import wgs01.hva.nl.foodstagrm.activity.LoginActivity;
import wgs01.hva.nl.foodstagrm.adapter.SuggestionListViewAdapter;
import wgs01.hva.nl.foodstagrm.listener.FeedListEventListener;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.model.User;
import wgs01.hva.nl.foodstagrm.network.instagramObject.Data;
import wgs01.hva.nl.foodstagrm.util.Constants;
import wgs01.hva.nl.foodstagrm.util.UploadUtil;

public class BaseActivity extends AppCompatActivity {

    private final String[] DIALOG_ITEMS = {"Camera","Galerij","Annuleer"};
    private final String INTENT_TYPE_IMAGES = "image/*";

    protected final String INSTA_URL = "https://www.instagram.com";
    protected final String INSTA_COOKIES = getCookieManager().getInstance().getCookie(INSTA_URL);

    protected final String API_URL = Constants.BASE_URL
                                    + "oauth/authorize/?client_id="
                                    + Constants.CLIENT_ID
                                    + "&redirect_uri="
                                    + Constants.REDIRECT_URI
                                    + "&response_type=token"
                                    + "&display=touch&scope=public_content";

    protected static final int TOAST_SHORT = 0;
    protected static final int TOAST_LONG = 1;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    protected final int REQUEST_CAMERA = 1;
    protected final int SELECT_FILE = 0;
    protected final int SHARE = 3;

    protected static User user;
    protected static String access_token;

    protected ArrayList<Data> data = new ArrayList<>();
    protected ArrayList<Recipe> recipes = new ArrayList<>();

    protected static FrameLayout mainFragmentFrame;
    protected static ProgressBar spinner;

    protected Intent intent;
    private UploadUtil uploadUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadUtil = new UploadUtil(this);
    }

    /*
     *  GETTERS / SETTERS
     */

    public static BaseActivity getInstance(){
        return new BaseActivity();
    }

    protected void setAccess(String pAccess){
        access_token = pAccess;
    }
    public static String getAccess(){return access_token;}
    public static User getUser(){return user;}
    public static FrameLayout getMainFragmentFrame(){
        return mainFragmentFrame;
    }
    protected CookieManager getCookieManager(){
        return CookieManager.getInstance();
    }
    protected UploadUtil getUploadUtil(){
        return uploadUtil;
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param pMessage An string representing a message to be shown.
     */
    protected void showToastMessage(String pMessage, int pLength) {
        Toast.makeText(this, pMessage, pLength).show();
    }

    protected void openFragment(Fragment pFragment, String pTitleAppBar){


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(mainFragmentFrame.getId(), pFragment);
        ft.commitAllowingStateLoss();
    }

    protected static void openActivity(Context pContext, Class<?> cls){
        //TODO
        Intent myIntent = new Intent(pContext, cls);
        pContext.startActivity(myIntent);
    }

    /**
     * Opens a new Activity using finish to close previous Activity.
     *
     * @param pActivity activities can use finish(), context can't.
     * @param pClass    name of Activity class that should open
     */
    protected void openActivityFinish(Activity pActivity, Class<?> pClass){
        Intent myIntent = new Intent(pActivity, pClass);
        pActivity.startActivity(myIntent);
        pActivity.finish();
    }

    protected void openActivity(Context pContext, Activity pActivity, String pStringIntent){
        //TODO
        Intent myIntent = new Intent(pContext, pActivity.getClass());
        myIntent.putExtra(Constants.STRING_PARAM, pStringIntent);
        pContext.startActivity(myIntent);
    }

    protected void openActivity(Context pContext, Activity pActivity, int pIntIntent){
        //TODO
        Intent myIntent = new Intent(this, pActivity.getClass());
        myIntent.putExtra(Constants.INT_PARAM, pIntIntent);
        pContext.startActivity(myIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean checkStoragePermission(){
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAGG","Permission is granted");
            //File write logic here
            return true;
        }

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
        return false;
    }

    protected void fetchReplicaRecipeFromFireBase(String idParentPhoto, final RecyclerView.Adapter<?> recyclerViewAdapter, final SuggestionListViewAdapter listViewAdapter){
        showSpinner();
        Constants.foodstagramDB.child(Constants.CHILD_RECIPE)
                .child(idParentPhoto)
                .child(Constants.CHILD_SHARED_DISH)
                .orderByChild(Constants.CHILD_IMAGE_PARENT_ID)
                .equalTo(idParentPhoto).addChildEventListener(new FeedListEventListener(recyclerViewAdapter,listViewAdapter, recipes));
        hideSpinner();
    }

    protected void selectImage(){
        if (uploadUtil.checkPermissionREAD_EXTERNAL_STORAGE()) {
            // do your stuff..
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle("Kies een optie");
            dialog.setItems(DIALOG_ITEMS, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(DIALOG_ITEMS[which].equals("Camera")){
                        cameraIntent();
                    }
                    else if(DIALOG_ITEMS[which].equals("Galerij")){
                        if(checkStoragePermission())
                            galleryIntent();
                    }
                    else if(DIALOG_ITEMS[which].equals("Annuleer")){
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        }
    }

    private void cameraIntent(){
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent(){
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType(INTENT_TYPE_IMAGES);
        startActivityForResult(intent, SELECT_FILE);
    }

    public static void showSpinner(){
        if (spinner.getVisibility() == View.GONE)
            spinner.setVisibility(View.VISIBLE);
    }
    public static void hideSpinner(){
        if (spinner.getVisibility() == View.VISIBLE)
            spinner.setVisibility(View.GONE);
    }

    public void logout(Activity pActivity){
        // TODO: Show dialog for confirmation (make dialogFactory?)
        getCookieManager().getInstance().removeAllCookies(null);
        user = null;
        access_token = null;

        openActivityFinish(pActivity, LoginActivity.class);
    }
}
