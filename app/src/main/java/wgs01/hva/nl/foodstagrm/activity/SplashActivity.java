package wgs01.hva.nl.foodstagrm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.CookieManager;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.R;

public class SplashActivity extends BaseActivity {

    private final int DURATION_SPLASH = 850;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // TODO: SEARCH FOR OTHER METHOD
        Thread timer= new Thread(){
            public void run(){

                try{
                    sleep(DURATION_SPLASH);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    init();
                }
            }
        };

        timer.start();
    }

    private void init(){
        // If cookies with auth exist
        Log.d("OPENS URL HAS COOKIE: ", String.valueOf(CookieManager.getInstance().hasCookies()));
        Log.d("OPENS URL AUTH_COOKIE: ", String.valueOf(CookieManager.getInstance().getCookie(API_URL)));

        if(CookieManager.getInstance().hasCookies() && INSTA_COOKIES != null && (INSTA_COOKIES.contains("ig_accessToken"))){
            Log.d("OPENS:", "MAIN");
            openActivityFinish(this, MainActivity.class);
        }
        else {
            Log.d("OPENS:", "LOGIN");
            openActivityFinish(this, LoginActivity.class);
        }
    }
}
