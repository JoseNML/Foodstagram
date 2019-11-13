package wgs01.hva.nl.foodstagrm.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.listener.AuthenticationListener;

/*
    TODO: check if user is in our database, if not than add user
    Need to check which userId we will use (from @apiUrl, see tokens.txt) to check if user exist in our database

    Which user details will be stored in our database?
        > id (need to decide which one)
        > username
        > ...
 */
public class LoginActivity extends BaseActivity implements AuthenticationListener, View.OnClickListener{

    private final String ACCESS_USER_ONE = "7822580805.7f66d80.3cb5c3abeefd4580ad396d2f1326f36f";
    private final String ACCESS_USER_TWO = "7770631706.7f66d80.88c499742dcd473ca0221db4251bb6a0";
    private final String ACCESS_USER_THREE = "495537171.7f66d80.029240f9f4af4c778683739e3a16f437";

    private final String COOKIE_STRING = "ig_cb=1";

    private final AuthenticationListener LISTENER = this;
    private boolean authComplete = false;

    private WebView webViewLogin;
    private Button userOne, userTwo, userThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeWebViewLogin();
//        init();
    }

    @Override
    public void onCodeReceived(String auth_token) {
        if (auth_token == null)
            showToastMessage("No Access", TOAST_SHORT);
        else
            setAccess(auth_token);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_user1:
                access_token = ACCESS_USER_ONE;
                openActivityFinish(LoginActivity.this, MainActivity.class);
                break;
            case R.id.btn_user2:
                access_token = ACCESS_USER_TWO;
                openActivityFinish(LoginActivity.this, MainActivity.class);
                break;
            case R.id.btn_user3:
                access_token = ACCESS_USER_THREE;
                openActivityFinish(LoginActivity.this, MainActivity.class);
                break;
        }
    }

    private void init(){
        userOne = findViewById(R.id.btn_user1);
        userTwo = findViewById(R.id.btn_user2);
        userThree = findViewById(R.id.btn_user3);

        userOne.setOnClickListener(this);
        userTwo.setOnClickListener(this);
        userThree.setOnClickListener(this);
    }

    private void initializeWebViewLogin() {
        // Check if cookies of instagram contain "ig_accessToken" because of new Instagram privacy policy
        if(INSTA_COOKIES == null || !INSTA_COOKIES.contains(COOKIE_STRING)){
            getCookieManager().setCookie(INSTA_URL, COOKIE_STRING);
        }

        spinner = findViewById(R.id.progressBar_login);
        webViewLogin = findViewById(R.id.webView_login);
        webViewLogin.getSettings().setJavaScriptEnabled(true);
        webViewLogin.getSettings().setDomStorageEnabled(true);
        webViewLogin.loadUrl("https://api.instagram.com/oauth/authorize/?client_id=7f66d800d4d64dc2a311b164912e94ac&redirect_uri=https://pad-wgs1.weebly.com/&response_type=token&display=touch&scope=public_content");
        webViewLogin.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // Here hide spinner
                hideSpinner();

                if (url.contains("#access_token=") && !authComplete) {
                    Uri uri = Uri.parse(url);

                    access_token = uri.getEncodedFragment();

                    // get the whole token after the '=' sign and add to cookies
                    access_token = access_token.substring(access_token.lastIndexOf("=")+1);
                    getCookieManager().setCookie(INSTA_URL, "ig_accessToken=" + access_token);

                    Log.i("", "CODE : " + access_token);
                    authComplete = true;
                    LISTENER.onCodeReceived(access_token);

                    openActivityFinish(LoginActivity.this, MainActivity.class);

                } else if (url.contains("?error")) {
                    showToastMessage("Error Occured", TOAST_SHORT);
                }
            }
        });
        }
}
