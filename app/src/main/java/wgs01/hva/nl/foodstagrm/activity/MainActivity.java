package wgs01.hva.nl.foodstagrm.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.fragment.AgendaFragment;
import wgs01.hva.nl.foodstagrm.fragment.ExploreFragment;
import wgs01.hva.nl.foodstagrm.fragment.ProfileFragment;
import wgs01.hva.nl.foodstagrm.fragment.TrendingFragment;
import wgs01.hva.nl.foodstagrm.fragment.WishListFragment;
import wgs01.hva.nl.foodstagrm.listener.AuthenticationListener;
import wgs01.hva.nl.foodstagrm.model.User;
import wgs01.hva.nl.foodstagrm.network.InstagramUserResponse;
import wgs01.hva.nl.foodstagrm.rest.RestClient;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class MainActivity extends BaseActivity implements AuthenticationListener {

    private final AuthenticationListener listener = this;
    boolean authComplete = false;

    private WebView webViewSplash;

    private BottomNavigationView navigation;

    private String id;
    private String username;
    private String fullName;
    private String profilePhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if(getAccess() == null){
            navigation.setVisibility(View.INVISIBLE);
            initializeWebView();
        }
        else if(user == null){
            initUserObj();
        }
        else{
            openFragment(new TrendingFragment(), null);
            Log.d("OPENS M_URL AUTH_COOK: ", String.valueOf(CookieManager.getInstance().getCookie(API_URL)));
            Log.d("OPENS ACCESS: ", access_token);
        }
    }

    @Override
    public void onCodeReceived(String auth_token) {
        if (auth_token == null)
            showToastMessage("No Access", TOAST_SHORT);
        else
            access_token = auth_token;
    }

    private void init(){
        mainFragmentFrame = findViewById(R.id.main_fragment);
        spinner = findViewById(R.id.progressBar_main);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void initializeWebView() {
        webViewSplash = findViewById(R.id.webView_main);
        webViewSplash.setVisibility(View.INVISIBLE);

        webViewSplash.loadUrl(API_URL);
        webViewSplash.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showToastMessage("Getting access_token...", TOAST_SHORT);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (url.contains("#access_token=") && !authComplete) {

                    Uri uri = Uri.parse(url);
                    access_token = uri.getEncodedFragment();

                    // get the whole token after the '=' sign
                    access_token = access_token.substring(access_token.lastIndexOf("=")+1);
                    Log.i("", "OPENS ACCESS CODE : " + access_token);

                    authComplete = true;
                    listener.onCodeReceived(access_token);
                    initUserObj();

                    showToastMessage("Access is: " + access_token, TOAST_SHORT);
                    Log.d("OPENS MAIN AUTH: ", String.valueOf(CookieManager.getInstance().getCookie(API_URL)));


                } else if (url.contains("?error")) {
                    showToastMessage("Error Occured", TOAST_SHORT);
                    return;
                }
            }
        });
    }

    private void initUserObj() {
        Call<InstagramUserResponse> call = RestClient.getRetrofitService().getUser(access_token);
        showSpinner();

        call.enqueue(new Callback<InstagramUserResponse>() {
            @Override
            public void onResponse(Call<InstagramUserResponse> call, Response<InstagramUserResponse> response) {
                if (response.body() != null) {
                    id = response.body().getData().getId();
                    username = response.body().getData().getUsername();
                    fullName = response.body().getData().getFullName();
                    profilePhotoUrl = response.body().getData().getProfile_picture();

                    // TODO: make user-object and put in BaseActivity
                    /*
                        Make navigation visible after user-object is initialized, else some pages may crash
                       (because user-object is null)
                    */
                    user = new User(id, username, fullName, profilePhotoUrl);
                    Constants.foodstagramDB.child("students").child(user.getId()).setValue(user);
                    navigation.setVisibility(View.VISIBLE);
                    openFragment(new TrendingFragment(), null);
                }
            }

            @Override
            public void onFailure(Call<InstagramUserResponse> call, Throwable t) {
                showToastMessage(t.toString(), TOAST_SHORT);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_trending:
                    openFragment(new TrendingFragment(),Constants.TITLE_TRENDING);
                    break;
                case R.id.navigation_explore:
                    openFragment(new ExploreFragment(),Constants.TITLE_EXPLORE);
                    break;
                case R.id.navigation_agenda:
                    openFragment(new AgendaFragment(),Constants.TITLE_AGENDA);
//                    openActivity(getBaseContext(),AgendaActivity.class );
                    break;
                case R.id.navigation_wishlist:
                    openFragment(new WishListFragment(),Constants.TITLE_WISHLIST);
//                    openActivity(getBaseContext(),WishlistActivity.class );
                    break;
                case R.id.navigation_profile:
                    openFragment(new ProfileFragment(), Constants.TITLE_MY_PROFILE);
                     break;
            }
            return true;
        }
    };
}
