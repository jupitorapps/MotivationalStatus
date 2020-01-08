package com.motivation.statusforwhatsapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.motivation.statusforwhatsapp.R;

import java.util.Objects;

public class ShareFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_share, container, false);

        Button buttonShare = root.findViewById(R.id.button_share);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String appUrl = "https://play.google.com/store/apps/details?id=" + Objects.requireNonNull(getActivity()).getPackageName();
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, appUrl + getString(R.string.share_app_message));
                //  intent.putExtra(Intent.EXTRA_TEXT,"Enjoy the best app for Odia News, Install Now");

                try {
                    startActivity(Intent.createChooser(intent, "Select an action"));
                } catch (android.content.ActivityNotFoundException ex) {
                    // (handle error)
                }


            }
        });


        return root;
    }
}