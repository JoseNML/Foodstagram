package wgs01.hva.nl.foodstagrm.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import wgs01.hva.nl.foodstagrm.network.InstagramResponse;
import wgs01.hva.nl.foodstagrm.network.InstagramUserResponse;

public interface RetrofitService {
    @GET("v1/tags/{tag_name}/media/recent")
    Call<InstagramResponse> getTagPhotos(@Path("tag_name") String tag_name,
                                         @Query("access_token") String access_token);

    @GET("v1/users/self/media/recent/?count=1")
    Call<InstagramResponse> getRecentPhoto(@Query("access_token") String access_token);

    @GET("v1/users/self")
    Call<InstagramUserResponse> getUser(@Query("access_token") String access_token);
}
