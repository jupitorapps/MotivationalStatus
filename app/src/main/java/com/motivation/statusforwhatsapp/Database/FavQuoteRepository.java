package com.motivation.statusforwhatsapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavQuoteRepository {
    private FavQuoteDao favQuoteDao;
    private LiveData<List<FavQuote>> allQuotes;
    private int fav_id_stored;

    public FavQuoteRepository(Application application) {
        FavQuoteDatabase database = FavQuoteDatabase.getInstance(application);
        favQuoteDao = database.favQuoteDao();
        allQuotes = favQuoteDao.getAllFavQuotes();

    }

    public void insert(FavQuote favQuote) {
        new InsertFavQuoteAsyncTask(favQuoteDao).execute(favQuote);
    }

    public void update(FavQuote favQuote) {
        new UpdateFavQuoteAsyncTask(favQuoteDao).execute(favQuote);
    }

    public int getFavQuote(int favQuoteId) {
        try {
            fav_id_stored = new GetFavQuoteId(favQuoteDao).execute(favQuoteId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return fav_id_stored;

    }

    public void remove (FavQuote favQuote){
        new RemoveFavQuoteAsyncTask(favQuoteDao).execute(favQuote);
    }

    public void removeAllFavQuotes(){
        new RemoveAllFavQuoteAsyncTask(favQuoteDao).execute();
    }

    public LiveData<List<FavQuote>> getAllFavQuotes(){
        return allQuotes;
    }


    private static class InsertFavQuoteAsyncTask extends AsyncTask<FavQuote, Void, Void> {
        private FavQuoteDao favQuoteDao;

        private InsertFavQuoteAsyncTask(FavQuoteDao favQuoteDao) {
            this.favQuoteDao = favQuoteDao;
        }


        @Override
        protected Void doInBackground(FavQuote... favQuotes) {
            favQuoteDao.insert(favQuotes[0]);
            return null;
        }
    }

    private static class UpdateFavQuoteAsyncTask extends AsyncTask<FavQuote, Void, Void> {
        private FavQuoteDao favQuoteDao;

        private UpdateFavQuoteAsyncTask(FavQuoteDao favQuoteDao) {
            this.favQuoteDao = favQuoteDao;
        }


        @Override
        protected Void doInBackground(FavQuote... favQuotes) {
            favQuoteDao.update(favQuotes[0]);
            return null;
        }
    }


    private static class GetFavQuoteId extends AsyncTask<Integer, Void, Integer> {
        private FavQuoteDao favQuoteDao;

        private GetFavQuoteId(FavQuoteDao favQuoteDao) {
            this.favQuoteDao = favQuoteDao;
        }


        @Override
        protected Integer doInBackground(Integer... integers) {

            return favQuoteDao.getFavQuote(integers[0]);
        }
    }


    private static class RemoveFavQuoteAsyncTask extends AsyncTask<FavQuote, Void, Void> {
        private FavQuoteDao favQuoteDao;

        private RemoveFavQuoteAsyncTask(FavQuoteDao favQuoteDao) {
            this.favQuoteDao = favQuoteDao;
        }


        @Override
        protected Void doInBackground(FavQuote... favQuotes) {
            favQuoteDao.remove(favQuotes[0]);
            return null;
        }
    }

    private static class RemoveAllFavQuoteAsyncTask extends AsyncTask<FavQuote, Void, Void> {
        private FavQuoteDao favQuoteDao;

        private RemoveAllFavQuoteAsyncTask(FavQuoteDao favQuoteDao) {
            this.favQuoteDao = favQuoteDao;
        }


        @Override
        protected Void doInBackground(FavQuote... favQuotes) {
            favQuoteDao.removeAllFavourites();
            return null;
        }
    }


}


