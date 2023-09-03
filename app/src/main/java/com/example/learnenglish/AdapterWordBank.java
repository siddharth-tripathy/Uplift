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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/learn-english-293fe.appspot.com/o/images%2Fcutting_machine.jfif?alt=media&token=bb229cc6-493e-4911-a9ec-dbe6e764ee54")
                .into(holder.item);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();

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
        ImageView play, item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ModelEnglish = itemView.findViewById(R.id.english_word);
            ModelHindi = itemView.findViewById(R.id.hindi_word);
            play = itemView.findViewById(R.id.play);
            item = itemView.findViewById(R.id.item);
        }
    }
}