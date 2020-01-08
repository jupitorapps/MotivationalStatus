package com.motivation.statusforwhatsapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = FavQuote.class, version = 1, exportSchema = false)
public abstract class FavQuoteDatabase extends RoomDatabase {
    private static FavQuoteDatabase instance;

    public abstract FavQuoteDao favQuoteDao();

    public static synchronized FavQuoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavQuoteDatabase.class,
                    "fav_quote_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynchTask(instance).execute();
        }
    };


    private static class PopulateDbAsynchTask extends AsyncTask<Void, Void, Void>{
        private FavQuoteDao favQuoteDao;

        PopulateDbAsynchTask(FavQuoteDatabase db){
            favQuoteDao = db.favQuoteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //insert dummy data
          //  favQuoteDao.insert(new FavQuote(1,"Dummy Quote","Pradeep","Business",1,"English","http://dostishayari.co.in/motivational_quotes/bg/bg1.png","2020-01-04"));
            return null;
        }
    }


}
