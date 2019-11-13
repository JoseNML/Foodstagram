package wgs01.hva.nl.foodstagrm.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wgs01.hva.nl.foodstagrm.util.Constants;

public class RestClient {

    public static RetrofitService getRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService.class);
    }
}
