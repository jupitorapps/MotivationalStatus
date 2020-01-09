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

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private QuoteAdapter quoteAdapter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        ((MainActivity) Objects.requireNonNull(getContext())).loadQuotesFromNetwork(0, recyclerView, quoteAdapter, progressBar);

        return root;
    }


}