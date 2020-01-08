package com.motivation.statusforwhatsapp.Fragments;

import android.os.Bundle;
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
import com.motivation.statusforwhatsapp.MainActivity;
import com.motivation.statusforwhatsapp.R;

import java.util.Objects;

public class SportsFragment extends Fragment {

    private static final String TAG = "TAGG";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sports, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = root.findViewById(R.id.fragment_recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        QuoteAdapter quoteAdapter = new QuoteAdapter(getContext());
        recyclerView.setAdapter(quoteAdapter);

        ProgressBar progressBar = root.findViewById(R.id.progres_bar);

        ((MainActivity) Objects.requireNonNull(getContext())).loadQuotesFromNetwork(3, recyclerView, quoteAdapter, progressBar);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



}