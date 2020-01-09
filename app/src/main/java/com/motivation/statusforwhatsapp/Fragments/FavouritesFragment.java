package com.motivation.statusforwhatsapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.motivation.statusforwhatsapp.Adapters.QuoteAdapter;
import com.motivation.statusforwhatsapp.Database.FavQuote;
import com.motivation.statusforwhatsapp.Database.FavQuoteViewModel;
import com.motivation.statusforwhatsapp.R;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {

    private ArrayList<FavQuote> quotesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private QuoteAdapter quoteAdapter;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favourites, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView = root.findViewById(R.id.fragment_recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        quoteAdapter = new QuoteAdapter(getContext());
        recyclerView.setAdapter(quoteAdapter);

        progressBar = root.findViewById(R.id.progres_bar);

        loadDataFromRoomDatabase();
        return root;
    }

    private void loadDataFromRoomDatabase() {

        FavQuoteViewModel favQuoteViewModel = ViewModelProviders.of(this).get(FavQuoteViewModel.class);
        favQuoteViewModel.getAllFavQuotes().observe(this, new Observer<List<FavQuote>>() {
            @Override
            public void onChanged(List<FavQuote> favQuotes) {
                progressBar.setVisibility(View.GONE);
                quotesList = (ArrayList<FavQuote>) favQuotes;
                recyclerView.hasFixedSize();
                quoteAdapter.loadQuotes(quotesList);


            }
        });



    }
}