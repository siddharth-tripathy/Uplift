package com.example.learnenglish;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSilentWord extends RecyclerView.Adapter<AdapterSilentWord.MyViewHolder> {

    Context context;
    ArrayList<ModelSilentWords> modelSilentWords;
    public TextToSpeech tts;

    public AdapterSilentWord(Context context, ArrayList<ModelSilentWords> modelSilentWords, TextToSpeech tts) {
        this.context = context;
        this.modelSilentWords = modelSilentWords;
        this.tts = tts;
    }

    @NonNull
    @Override
    public AdapterSilentWord.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_silent_words, parent, false);
        return new AdapterSilentWord.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSilentWord.MyViewHolder holder, int position) {
        ModelSilentWords model = modelSilentWords.get(position);
        String eng_name = model.EnglishWord;
        String hindi_name = model.HindiWord;
        String silent_name = model.SilentLetter;
        holder.ModelEnglish.setText(eng_name);
        holder.ModelHindi.setText(hindi_name);
        holder.ModelSilent.setText("मूक अक्षर: " + silent_name);
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
        return modelSilentWords.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ModelEnglish;
        TextView ModelHindi;
        TextView ModelSilent;
        ImageView play;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ModelEnglish = itemView.findViewById(R.id.english_word);
            ModelHindi = itemView.findViewById(R.id.hindi_word);
            ModelSilent = itemView.findViewById(R.id.silent_word);
            play = itemView.findViewById(R.id.play);
        }
    }
}
