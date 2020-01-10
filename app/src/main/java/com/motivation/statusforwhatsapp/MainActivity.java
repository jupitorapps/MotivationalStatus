package com.motivation.statusforwhatsapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.motivation.statusforwhatsapp.Adapters.QuoteAdapter;
import com.motivation.statusforwhatsapp.Database.FavQuote;
import com.motivation.statusforwhatsapp.Database.FavQuoteViewModel;
import com.motivation.statusforwhatsapp.Utils.ApiClient;
import com.motivation.statusforwhatsapp.Utils.ApiService;
import com.motivation.statusforwhatsapp.Utils.AppUtilities;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private final String TAG = "TAGG";

    private AppBarConfiguration mAppBarConfiguration;

    private TextToSpeech tts;
    private ArrayList<FavQuote> quoteArrayList = new ArrayList<>();
    private InterstitialAd interstitialAd;

    private FirebaseAnalytics firebaseAnalytics;

    AppUtilities appUtilities = AppUtilities.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_sports, R.id.nav_business,
                R.id.nav_health, R.id.nav_exam, R.id.nav_fav, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        tts = new TextToSpeech(this, this);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.admob_ad_unit_interstitial));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Bundle bundle = new Bundle();
                bundle.putString("ad_click",getString(R.string.ad_clicked));
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.GENERATE_LEAD,bundle);
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });

    }

    public void showAdmobInterstitialAd() {
        if (interstitialAd.isLoaded() && interstitialAd != null) {
            interstitialAd.show();
            interstitialAd.loadAd(new AdRequest.Builder().build());

        } else {
            Log.d("TAG", getString(R.string.ad_not_loaded));
            interstitialAd.loadAd(new AdRequest.Builder().build());
        }


    }


    public void loadQuotesFromNetwork(int catId, RecyclerView recyclerView, QuoteAdapter quoteAdapter, ProgressBar progressBar) {

        if (!progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
        }

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        //category_id = 3 to get Sports Quotes
        Call<ArrayList<FavQuote>> call = apiService.getQuote(catId);
        call.enqueue(new Callback<ArrayList<FavQuote>>() {

            @Override
            public void onResponse(Call<ArrayList<FavQuote>> call, Response<ArrayList<FavQuote>> response) {

                progressBar.setVisibility(View.GONE);
                quoteArrayList = response.body();
                recyclerView.hasFixedSize();
                quoteAdapter.loadQuotes(quoteArrayList);
                appUtilities.setFavQuoteArrayList(quoteArrayList);



            }

            @Override
            public void onFailure(Call<ArrayList<FavQuote>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Log.d(TAG, "onFailure: " + t.toString());

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        updateAllWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_about) {
            Intent intent = new Intent(this, AboutAppActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void updateAllWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, QuoteAppWidgetProvider.class));
        if (appWidgetIds.length > 0) {
            new QuoteAppWidgetProvider().onUpdate(this, appWidgetManager, appWidgetIds);
        }
    }


    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), R.string.language_not_supported, Toast.LENGTH_SHORT).show();
            }  //   button.setEnabled(true);


        } else {
            Toast.makeText(getApplicationContext(), R.string.init_failed, Toast.LENGTH_SHORT).show();

        }

    }

    public void speakQuote(String quoteToSpeak) {

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

            @Override
            public void onStart(String utteranceId) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });

            }

            @Override
            public void onDone(String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //   Toast.makeText(getApplicationContext(), "Done ", Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void onError(String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), R.string.error_in_speaking, Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(quoteToSpeak, TextToSpeech.QUEUE_FLUSH, params, getString(R.string.error_in_speaking));
        }


    }


    public void savePositions(int rvPosition){
        appUtilities.setRecyclerviewposition(rvPosition);
    }

}
