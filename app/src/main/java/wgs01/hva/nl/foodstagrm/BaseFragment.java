package wgs01.hva.nl.foodstagrm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgs01.hva.nl.foodstagrm.adapter.LatestAdapter;
import wgs01.hva.nl.foodstagrm.adapter.SuggestionListViewAdapter;
import wgs01.hva.nl.foodstagrm.dialog.AuthenticationDialog;
import wgs01.hva.nl.foodstagrm.listener.AuthenticationListener;
import wgs01.hva.nl.foodstagrm.listener.FeedListEventListener;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.network.InstagramResponse;
import wgs01.hva.nl.foodstagrm.network.instagramObject.Data;
import wgs01.hva.nl.foodstagrm.util.Constants;
import wgs01.hva.nl.foodstagrm.rest.RestClient;

public class BaseFragment extends Fragment implements AuthenticationListener {

    protected static final int TOAST_SHORT = 0;
    protected static final int TOAST_LONG = 1;

    protected View view;

    protected ArrayList<Data> data = new ArrayList<>();
    protected ArrayList<Recipe> recipe = new ArrayList<>();

    protected FragmentTransaction ft;
    protected static String access_token;

    private AuthenticationDialog auth_dialog;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCodeReceived(String auth_token) {
        if (access_token == null) {
            auth_dialog.dismiss();
            showToastMessage("No Access", TOAST_SHORT);
        }
        setAccess(access_token);
    }

    protected void initAuth(){
        // If accesstoken of BaseActivity is empty/null, TODO; need to reset accesstoken through Activity (curently goes through fragment)
        if (getAccess() == null) {
            initAuthDialog();
        }

        // Get the access_token from the intent extra
        access_token = getAccess();
    }

    private void initAuthDialog(){
        auth_dialog = new AuthenticationDialog(getContext(), this);
        auth_dialog.setCancelable(true);
        auth_dialog.show();
    }

    /*
     *  GETTERS / SETTERS
     */
    protected void setAccess(String pAccess){
        this.access_token = pAccess;
    }

    public static String getAccess(){
        return BaseActivity.getAccess();
    }

    protected FrameLayout getMainFrame(){
        return BaseActivity.getMainFragmentFrame();
    }


    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param pMessage An string representing a message to be shown.
     */
    protected void showToastMessage(String pMessage, int pLength) {
        Toast.makeText(getActivity(), pMessage, pLength).show();
    }

    public void openPage(Fragment pFragment, String pTitleAppBar){
        if(pTitleAppBar != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(pTitleAppBar);
        }
        else ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);

        getFragmentManager().beginTransaction().replace(getMainFrame().getId(), pFragment).commit();
    }

    public void openActivity(Class<?> cls){
        BaseActivity.openActivity(getContext(), cls);
    }

    public void fetchDataFromInstagram(String tag, final LatestAdapter recyclerViewAdapter, final SuggestionListViewAdapter listViewAdapter) {
        Call<InstagramResponse> call = RestClient.getRetrofitService().getTagPhotos(tag, access_token);
        BaseActivity.showSpinner();
        call.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {

                if (response.body() != null) {
                    for(int i = 0; i < response.body().getData().length; i++){
                        data.add(response.body().getData()[i]);
                    }
                    if(recyclerViewAdapter == null){
                        listViewAdapter.notifyDataSetChanged();
                    }
                    else{
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
                BaseActivity.hideSpinner();
            }

            @Override
            public void onFailure(Call<InstagramResponse> call, Throwable t) {
                //Handle failure
                BaseActivity.hideSpinner();
                showToastMessage(t.toString(), TOAST_SHORT);
            }
        });
    }

    public void fetchDataFromFirebase(String tag, final RecyclerView.Adapter<?> recyclerViewAdapter, final SuggestionListViewAdapter listViewAdapter) {
        BaseActivity.showSpinner();
        Constants.foodstagramDB.child(Constants.CHILD_RECIPE).addChildEventListener(new FeedListEventListener(recyclerViewAdapter,listViewAdapter, recipe));
        BaseActivity.hideSpinner();
    }



}
