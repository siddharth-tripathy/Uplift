package com.example.learnenglish;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class AdapterWordBank extends RecyclerView.Adapter<AdapterWordBank.MyViewHolder> {

    Context context;

    public TextToSpeech tts;
    ArrayList<ModelWordBank> modelWordBanks;

    public AdapterWordBank(Context context, ArrayList<ModelWordBank> modelWordBanks, TextToSpeech mtts) {
        this.context = context;
        this.modelWordBanks = modelWordBanks;
        tts = mtts;
    }

    @NonNull
    @Override
    public AdapterWordBank.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_word_bank, parent, false);
        return new AdapterWordBank.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWordBank.MyViewHolder holder, int position) {
        ModelWordBank model = modelWordBanks.get(position);
        String eng_name = model.EnglishName;
        String hindi_name = model.HindiName;
//        String url = model.URL;

        holder.ModelEnglish.setText(eng_name);
        holder.ModelHindi.setText(hindi_name);
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.drawable.profile)
//                .into(holder.ModelReadDp);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(eng_name, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelWordBanks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ModelEnglish;
        TextView ModelHindi;
        ImageView play;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ModelEnglish = itemView.findViewById(R.id.english_word);
            ModelHindi = itemView.findViewById(R.id.hindi_word);
            play = itemView.findViewById(R.id.play);
        }
    }
}
