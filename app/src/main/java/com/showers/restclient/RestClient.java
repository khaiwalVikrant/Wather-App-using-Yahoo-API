package com.showers.restclient;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.showers.restModel.Root;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by vikrant on 01/6/16.
 */
public class RestClient {
    private static String baseUrl = "https://query.yahooapis.com/v1/public/";

    public static RestInterface getClient(final Context context) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient ;

        okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit retroFit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
//            }
        RestInterface retrofit = retroFit.create(RestInterface.class);
        return retrofit;
    }
    public interface RestInterface {

        //GET query for All Weather Information
        // If you want to see any other place information find delhi in below api url and replace with your choice city(like mumbai etc.)

        @GET("yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22delhi%2C%20in%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
        Call<Root> getYahooWeather() ;

    }


}
