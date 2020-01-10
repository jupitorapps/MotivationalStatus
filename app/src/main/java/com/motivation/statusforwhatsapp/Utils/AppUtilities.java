package com.motivation.statusforwhatsapp.Utils;


public class AppUtilities {

    private static AppUtilities _instance;

    private int recyclerviewposition;


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


}
