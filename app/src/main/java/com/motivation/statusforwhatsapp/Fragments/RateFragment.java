package com.motivation.statusforwhatsapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.motivation.statusforwhatsapp.R;

import java.util.Objects;

public class RateFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_rate, container, false);

        Button rate_btton = root.findViewById(R.id.button_rate_app);
        rate_btton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appUrl = getString(R.string.playstore_url) + Objects.requireNonNull(getActivity()).getPackageName();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(appUrl));
                //  intent.setPackage("com.android.vending");
                startActivity(intent);
            }
        });

        return root;
    }
}