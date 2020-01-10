package com.motivation.statusforwhatsapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.motivation.statusforwhatsapp.Adapters.QuoteAdapter;
import com.motivation.statusforwhatsapp.Database.FavQuote;
import com.motivation.statusforwhatsapp.MainActivity;
import com.motivation.statusforwhatsapp.R;
import com.motivation.statusforwhatsapp.Utils.AppUtilities;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private final String TAG = "TAGG";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private QuoteAdapter quoteAdapter;
    AppUtilities appUtilities;
    private ArrayList<FavQuote> favQuoteArrayList = new ArrayList<>();

    private int recyclerViewScrollPosition;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Fragment");

        appUtilities = AppUtilities.getInstance();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = root.findViewById(R.id.fragment_recyclerview);
        ProgressBar progressBar = root.findViewById(R.id.progres_bar);

        recyclerView.setLayoutManager(linearLayoutManager);
        quoteAdapter = new QuoteAdapter(getContext());
        recyclerView.setAdapter(quoteAdapter);

        if (savedInstanceState == null) {

            ((MainActivity) Objects.requireNonNull(getContext())).loadQuotesFromNetwork(0, recyclerView, quoteAdapter, progressBar);

        }

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getContext()).savePositions(((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition());

    }

    @Override
    public void onResume() {
        super.onResume();

        if (appUtilities.getFavQuoteArrayList() != null && appUtilities.getRecyclerviewposition() != 0) {
            recyclerView.getLayoutManager().scrollToPosition(appUtilities.getRecyclerviewposition());
            recyclerViewScrollPosition = appUtilities.getRecyclerviewposition();

            favQuoteArrayList = appUtilities.getFavQuoteArrayList();

            quoteAdapter.loadQuotes(favQuoteArrayList);
            quoteAdapter.notifyDataSetChanged();

        }


    }
}