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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterHomophones extends RecyclerView.Adapter<AdapterHomophones.MyViewHolder> {

    Context context;
    public TextToSpeech tts;
    ArrayList<ModelHomophones> modelHomophones;

    public AdapterHomophones(Context context, TextToSpeech tts, ArrayList<ModelHomophones> modelHomophones) {
        this.context = context;
        this.tts = tts;
        this.modelHomophones = modelHomophones;
    }

    @NonNull
    @Override
    public AdapterHomophones.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_homophones, parent, false);
        return new AdapterHomophones.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomophones.MyViewHolder holder, int position) {
        ModelHomophones model = modelHomophones.get(position);
        String eng_name1 = model.EngWord1;
        String eng_name2 = model.EngWord2;
        String mean_name1 = model.Meaning1;
        String mean_name2 = model.Meaning2;

        holder.EngWord1.setText(eng_name1);
        holder.EngWord2.setText(eng_name2);
        holder.Meaning1.setText(mean_name1);
        holder.Meaning1.setText(mean_name2);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();

                tts.speak(eng_name1, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelHomophones.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView EngWord1, EngWord2;
        TextView Meaning1, Meaning2;
        ImageView play;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            EngWord1 = itemView.findViewById(R.id.english_word1);
            EngWord2 = itemView.findViewById(R.id.english_word2);
            Meaning2 = itemView.findViewById(R.id.meaning2);
            Meaning1 = itemView.findViewById(R.id.meaning1);
            play = itemView.findViewById(R.id.play);
        }
    }
}
