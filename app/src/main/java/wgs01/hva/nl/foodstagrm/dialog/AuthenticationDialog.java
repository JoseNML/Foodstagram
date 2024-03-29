package wgs01.hva.nl.foodstagrm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.listener.AuthenticationListener;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class AuthenticationDialog extends Dialog {

    private final AuthenticationListener listener;
    private Context context;
    boolean authComplete = false;
    String access_token;

    private WebView web_view;

    private final String url = Constants.BASE_URL
            + "oauth/authorize/?client_id="
            + Constants.CLIENT_ID
            + "&redirect_uri="
            + Constants.REDIRECT_URI
            + "&response_type=token"
            + "&display=touch&scope=public_content";

    public AuthenticationDialog(@NonNull Context context, AuthenticationListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.auth_dialog);
        initializeWebView();
    }

    private void initializeWebView() {
        web_view =  findViewById(R.id.web_view);
        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (url.contains("#access_token=") && !authComplete) {

                    Uri uri = Uri.parse(url);
                    access_token = uri.getEncodedFragment();

                    // get the whole token after the '=' sign
                    access_token = access_token.substring(access_token.lastIndexOf("=")+1);
                    Log.i("", "CODE : " + access_token);
                    authComplete = true;
                    listener.onCodeReceived(access_token);
                    dismiss();

                } else if (url.contains("?error")) {
                    Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }
}
