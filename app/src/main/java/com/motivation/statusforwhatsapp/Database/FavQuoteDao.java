package com.motivation.statusforwhatsapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavQuoteDao {

    @Insert
    void insert(FavQuote favQuote);

    @Update
    void update(FavQuote favQuote);

    @Delete
    void remove(FavQuote favQuote);

    @Query("DELETE FROM fav_quotes")
    void removeAllFavourites();

    @Query("SELECT * FROM fav_quotes ORDER BY favDateTime DESC LIMIT 50")
    LiveData<List<FavQuote>> getAllFavQuotes();

    @Query(" SELECT id FROM fav_quotes WHERE id=:fav_id")
    int getFavQuote(int fav_id);

    @Query("SELECT * FROM fav_quotes ORDER BY favDateTime DESC")
    List<FavQuote> getFavQuotesForWidget();


}
