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

public class AdapterSentenceAll extends RecyclerView.Adapter<AdapterSentenceAll.MyViewHolder> {

    Context context;
    ArrayList<ModelSentenceAll> modelSentencesAll;
    public TextToSpeech tts;

    public AdapterSentenceAll(Context context, ArrayList<ModelSentenceAll> modelSentencesAll, TextToSpeech tts) {
        this.context = context;
        this.modelSentencesAll = modelSentencesAll;
        this.tts = tts;
    }

    @NonNull
    @Override
    public AdapterSentenceAll.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_sentences, parent, false);
        return new AdapterSentenceAll.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSentenceAll.MyViewHolder holder, int position) {
        ModelSentenceAll model = modelSentencesAll.get(position);
        String eng_name = model.EnglishSentence;
        String hindi_name = model.HindiSentence;
        holder.ModelEnglish.setText(eng_name);
        holder.ModelHindi.setText(hindi_name);
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
        return modelSentencesAll.size();
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
