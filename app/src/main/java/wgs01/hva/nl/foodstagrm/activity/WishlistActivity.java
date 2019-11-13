package wgs01.hva.nl.foodstagrm.activity;

import android.os.Bundle;
import android.view.MenuItem;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.R;

public class WishlistActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wishlist);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    private void init(){

    }
}
