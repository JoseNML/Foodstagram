package wgs01.hva.nl.foodstagrm.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Constants {

    // Instagram API
    public static final String CLIENT_ID = "7f66d800d4d64dc2a311b164912e94ac";
    public static final String CLIENT_SECRET = "86cbbf1e2a2c4baf8a4e0686912e19d4 ";
    public static final String BASE_URL = "https://api.instagram.com/";
    public static final String REDIRECT_URI = "https://pad-wgs1.weebly.com/";

    public static final String PHOTO_SEARCH_TAG = "FeedMyPost";
    public static final String CAPTION_TAG = "#feedmypost";

    // ActionBar Titles
    public static final String TITLE_HOME = "Home";
    public static final String TITLE_EXPLORE = "Verkennen";
    public static final String TITLE_AGENDA = "Agenda";
    public static final String TITLE_WISHLIST = "Wishlist";
    public static final String TITLE_TRENDING = "Trending";
    public static final String TITLE_RANKING = "Ranking";
    public static final String TITLE_MY_PROFILE = "Mijn Profiel";

    // Intent Parameters
    public static final String STRING_PARAM = "key_string";
    public static final String INT_PARAM = "key_int";
    public static final String IMG_PARAM = "image";
    public static final String IMG_ID = "image_id";
    public static final String IMG_PARENT_ID = "image_parent_id";
    public static final String IMG_USER_ID = "image_user_id";
    public static final String TITLE_PARAM = "title";
    public static final String RECIPE_URL_PARAM = "recipe_url";
    public static final String IMG_USER_CAPTION = "user_caption";


    // Firebase
    public static final DatabaseReference foodstagramDB = FirebaseDatabase.getInstance().getReference("data");

    public static final String CHILD_RECIPE = "recipe";
    public static final String CHILD_WISHLIST = "wish_list";
    public static final String CHILD_SHARED_DISH = "shared_dish";

    public static final String CHILD_IMAGE_ID = "imageId";
    public static final String CHILD_IMAGE_PARENT_ID = "parentImageId";
    public static final String CHILD_USER_ID = "userId";

    public static final String CHILD_CALENDAR = "calendar";

}
