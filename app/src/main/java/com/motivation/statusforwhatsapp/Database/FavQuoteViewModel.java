package com.motivation.statusforwhatsapp.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavQuoteViewModel extends AndroidViewModel {

    private FavQuoteRepository repository;
    private LiveData<List<FavQuote>> allFavQuotes;

    public FavQuoteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavQuoteRepository(application);
        allFavQuotes = repository.getAllFavQuotes();

    }


    public void insert (FavQuote favQuote){
        repository.insert(favQuote);
    }

    public void update (FavQuote favQuote){
        repository.update(favQuote);
    }

    public void remove (FavQuote favQuote){
        repository.remove(favQuote);
    }

    public void removeAllFavQuotes(){
        repository.removeAllFavQuotes();
    }

    public LiveData<List<FavQuote>> getAllFavQuotes(){
        return allFavQuotes;
    }

    public int getFavQuote(int id){
        return repository.getFavQuote(id);
    }




}
