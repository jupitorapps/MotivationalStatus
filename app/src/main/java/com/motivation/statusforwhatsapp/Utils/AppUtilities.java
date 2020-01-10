package com.motivation.statusforwhatsapp.Utils;


import com.motivation.statusforwhatsapp.Database.FavQuote;

import java.util.ArrayList;

public class AppUtilities {

    private static AppUtilities _instance;

    private int recyclerviewposition;
    private ArrayList<FavQuote> favQuoteArrayList;


    public AppUtilities() {
    }

    public synchronized static AppUtilities getInstance() {
        if (_instance == null) {
            {
                _instance = new AppUtilities();
            }


        }
        return _instance;
    }



    public int getRecyclerviewposition() {
        return recyclerviewposition;
    }

    public void setRecyclerviewposition(int recyclerviewposition) {
        this.recyclerviewposition = recyclerviewposition;
    }

    public ArrayList<FavQuote> getFavQuoteArrayList() {
        return favQuoteArrayList;
    }

    public void setFavQuoteArrayList(ArrayList<FavQuote> favQuoteArrayList) {
        this.favQuoteArrayList = favQuoteArrayList;
    }
}
