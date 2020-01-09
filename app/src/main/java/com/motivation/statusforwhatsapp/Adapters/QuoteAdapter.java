package com.motivation.statusforwhatsapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.motivation.statusforwhatsapp.Database.FavQuote;
import com.motivation.statusforwhatsapp.Database.FavQuoteViewModel;
import com.motivation.statusforwhatsapp.MainActivity;
import com.motivation.statusforwhatsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuotesViewHolder> {

    private final String TAG = "TAGG";

    private Context context;
    private ArrayList<FavQuote> quotesList = new ArrayList<>();
    private FavQuote currentQuote;

    private FavQuoteViewModel favQuoteViewModel;

    private int[] fav_quote_id;

    public QuoteAdapter(Context context) {
        this.context = context;

    }

    public void loadQuotes(ArrayList<FavQuote> data) {
        if (this.quotesList != null && data != null) {
            this.quotesList.addAll(data);
            notifyDataSetChanged();
        }


    }

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        favQuoteViewModel = ViewModelProviders.of((MainActivity) context).get(FavQuoteViewModel.class);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.quote_item, parent, false);

        return new QuotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder holder, int position) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int deviceheight = displaymetrics.heightPixels;

        currentQuote = quotesList.get(position);
        holder.quotesText.setText(currentQuote.getQuote());
        holder.quotesAuthor.setText(currentQuote.getAuthor());
        holder.layoutForBgImage.getLayoutParams().height = (int) (deviceheight * 0.65);
        holder.quote_category.setText(currentQuote.getCategory_name());

        //check for favourite

        fav_quote_id = new int[]{favQuoteViewModel.getFavQuote(quotesList.get(position).getId())};

        if (fav_quote_id[0] == 0) {
            holder.favIcon.setImageResource(R.drawable.fav_white);
        } else {
            holder.favIcon.setImageResource(R.drawable.fav_icon_red);
        }


        Picasso.get()
                .load(currentQuote.getImage_link())
                .placeholder(R.drawable.bg3)
                .error(R.drawable.bg3)
                .into(holder.layoutForBgImage);

        holder.setStatusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)context).showAdmobInterstitialAd();

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");

                whatsappIntent.putExtra(Intent.EXTRA_TEXT, quotesList.get(position).getQuote() + "\n\n" + quotesList.get(position).getAuthor());

                try {
                    context.startActivity(whatsappIntent);

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, context.getString(R.string.whatsapp_not_installed), Toast.LENGTH_LONG).show();
                }
            }
        });


        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity)context).showAdmobInterstitialAd();
                //  Log.d(TAG, "onClick: Fav Icon Clicked");

             //   Log.d(TAG, "onClick: Input ID: " + quotesList.get(position).getId());
                currentQuote = quotesList.get(position);

             //   Log.d(TAG, "onClick: Fav Quote ID: " + fav_quote_id[0]);
                fav_quote_id = new int[]{favQuoteViewModel.getFavQuote(quotesList.get(position).getId())};


                String currentTime = Calendar.getInstance().getTime().toString();

                if (fav_quote_id[0] == 0) { //not in db as favourite
                   // Log.d(TAG, "onClick: Code Came here at 145");
                    favQuoteViewModel.insert(new FavQuote(currentQuote.getId(),currentQuote.getCategory_id(),currentQuote.getQuote(),currentQuote.getAuthor(),currentQuote.getLanguage(),currentQuote.getFavourites(),currentQuote.getShares(),currentQuote.getListens(),currentQuote.getStatus_sets(),currentQuote.getViews(),currentQuote.getImage_link(),currentQuote.getCategory_name(),currentTime));

                    fav_quote_id = new int[]{favQuoteViewModel.getFavQuote(quotesList.get(position).getId())};

                    holder.favIcon.setImageResource(R.drawable.fav_icon_red);
                   // Log.d(TAG, "onClick: Fav QUote ID: "+fav_quote_id[0]);

                } else {
                    //remove from favourite from db
                  //  Log.d(TAG, "onClick: Code Came here at 150");

                    favQuoteViewModel.remove(new FavQuote(currentQuote.getId(),currentQuote.getCategory_id(),currentQuote.getQuote(),currentQuote.getAuthor(),currentQuote.getLanguage(),currentQuote.getFavourites(),currentQuote.getShares(),currentQuote.getListens(),currentQuote.getStatus_sets(),currentQuote.getViews(),currentQuote.getImage_link(),currentQuote.getCategory_name(),currentTime));
                    fav_quote_id[0] = 0;
                    holder.favIcon.setImageResource(R.drawable.fav_white);
                   // Log.d(TAG, "onClick: Fav QUote ID: "+fav_quote_id[0]);
                }


            }
        });

        holder.listenIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)context).speakQuote(quotesList.get(position).getQuote()+"     "+quotesList.get(position).getAuthor());


            }
        });
//General Share button click
        holder.shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, quotesList.get(position).getQuote() + "\n\n" + quotesList.get(position).getAuthor());
                try {
                    context.startActivity(Intent.createChooser(intent, context.getString(R.string.select_an_action)));

                } catch (android.content.ActivityNotFoundException ex) {
                    // (handle error)
                    Log.d(TAG, context.getString(R.string.error_in_sharing) + ex);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }

    public class QuotesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView quotesCard;
        @BindView(R.id.layout_for_card_bg_image)
        ImageView layoutForBgImage;
        @BindView(R.id.quote_text)
        TextView quotesText;
        @BindView(R.id.quote_author)
        TextView quotesAuthor;
        @BindView(R.id.whatsapp_icon)
        ImageView setStatusIcon;
        @BindView(R.id.share_icon)
        ImageView shareIcon;
        @BindView(R.id.fav_icon)
        ImageView favIcon;
        @BindView(R.id.listen_icon)
        ImageView listenIcon;
        @BindView(R.id.quote_category)
        TextView quote_category;


        QuotesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
