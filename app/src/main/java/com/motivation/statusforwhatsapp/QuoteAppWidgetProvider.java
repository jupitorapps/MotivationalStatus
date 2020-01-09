package com.motivation.statusforwhatsapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.motivation.statusforwhatsapp.Database.FavQuote;
import com.motivation.statusforwhatsapp.Database.FavQuoteDatabase;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of App Widget functionality.
 */
public class QuoteAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quote_app_widget);

        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.quote_text_widget,pendingIntent);
        views.setOnClickPendingIntent(R.id.quote_author_widget,pendingIntent);


        views.setOnClickPendingIntent(R.id.quote_category_widget,pendingIntent);


        loadWidgetWithData(views,context);

        // Instruct the widget manager to update the widget
        
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quote_app_widget);

            loadWidgetWithData(views,context);

            updateAppWidget(context, appWidgetManager, appWidgetId);
            
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

    }
    
    private static class GetQuoteForWidget extends AsyncTask<Context, Void, List<FavQuote>> {

        @Override
        protected List<FavQuote> doInBackground(Context... contexts) {
            List<FavQuote> list = null;

            FavQuoteDatabase favQuoteDatabase = FavQuoteDatabase.getInstance(contexts[0]);
            list = favQuoteDatabase.favQuoteDao().getFavQuotesForWidget();

            return list;
        }
    }

    private static void loadWidgetWithData(RemoteViews views,Context context) {


        try {
            List<FavQuote> list = new GetQuoteForWidget().execute(context).get();
            if (list.isEmpty()){

           
                views.setTextViewText(R.id.quote_text_widget,context.getString(R.string.make_a_favourite));
                views.setTextViewText(R.id.quote_category_widget,context.getString(R.string.category));
                views.setTextViewText(R.id.quote_author_widget,context.getString(R.string.author));

            } else {

                Random random =  new Random();
                int randomNumber = random.nextInt(list.size());

                views.setTextViewText(R.id.quote_text_widget, list.get(randomNumber).getQuote());
                views.setTextViewText(R.id.quote_author_widget,list.get(randomNumber).getAuthor());
                views.setTextViewText(R.id.quote_category_widget,list.get(randomNumber).getCategory_name());

            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();

        }

    }

}