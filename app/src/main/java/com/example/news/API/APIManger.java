package com.example.news.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManger {

    private static Retrofit retrofit;

    //c26a5b7fce024a58b55d2bbee6b5b836

    public static Retrofit getInstance(){
        if (retrofit==null){
            retrofit= new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory ( GsonConverterFactory.create () )
                    .build();

        }
        return retrofit;
    }

    public static WebService getApis(){
       return getInstance ().create ( WebService.class );
    }
}
