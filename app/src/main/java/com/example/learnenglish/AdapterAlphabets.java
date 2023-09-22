package com.example.learnenglish;

import android.content.Context;
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

import java.util.ArrayList;

public class AdapterAlphabets extends RecyclerView.Adapter<AdapterAlphabets.MyViewHolder> {

    private Context context;
    private ArrayList<String> alphabet = new ArrayList<>();

    private ArrayList<String> alpha_pro_hindi = new ArrayList<>();

    TextToSpeech tts;

    public AdapterAlphabets(Context context, ArrayList<String> alphabet, ArrayList<String> alpha_pro_hindi, TextToSpeech tts) {
        this.context = context;
        this.alphabet = alphabet;
        this.alpha_pro_hindi = alpha_pro_hindi;
        this.tts = tts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_alphabet, parent, false);
        return new AdapterAlphabets.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.alpha.setText(alphabet.get(position));
        holder.hindiword.setText(alpha_pro_hindi.get(position));
        String a = holder.alpha.getText().toString();

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();

                tts.speak(a, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alphabet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView alpha, hindiword;
        ImageView play;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            alpha = itemView.findViewById(R.id.alpha);
            play = itemView.findViewById(R.id.play);
            hindiword = itemView.findViewById(R.id.hindiword);
        }
    }
}
