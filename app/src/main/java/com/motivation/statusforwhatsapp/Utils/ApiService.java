package com.motivation.statusforwhatsapp.Utils;

import com.motivation.statusforwhatsapp.Database.FavQuote;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("getQuotes.php")
    Call<ArrayList<FavQuote>> getQuote(@Query("category_id") int category_id);


}
