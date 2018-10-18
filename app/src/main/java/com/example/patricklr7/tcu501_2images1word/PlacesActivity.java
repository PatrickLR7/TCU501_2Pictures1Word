package com.example.patricklr7.tcu501_2images1word;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patricklr7.tcu501_2images1word.Adapter.GridViewAnswerAdapter;
import com.example.patricklr7.tcu501_2images1word.Adapter.GridViewLettersAdapter;
import com.example.patricklr7.tcu501_2images1word.Common.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PlacesActivity extends AppCompatActivity {

    public List<String> lettersSource = new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public GridViewLettersAdapter lettersAdapter;
    public Button btnReview;
    public GridView gridViewAnswer, gridViewLetters;
    public ImageView imgViewQuestion;
    public ImageView imgViewQuestion2;
    //public ArrayList<Integer> listRand;
    public HashSet<Integer> randUsed;
    int wordsCount;
    TextToSpeech audio1;

    public int[] image_list1 = {
            R.drawable.amusementpark1,
            R.drawable.aquarium1,
            R.drawable.beach1,
            R.drawable.cinema1,
            R.drawable.island1,
            R.drawable.mountain1,
            R.drawable.museum1,
            R.drawable.pool1,
            R.drawable.rainforest1,
            R.drawable.volcano1,
            R.drawable.waterfall1,
            R.drawable.zoo1
    };

    public int[] image_list2 = {
            R.drawable.amusementpark2,
            R.drawable.aquarium2,
            R.drawable.beach2,
            R.drawable.cinema2,
            R.drawable.island2,
            R.drawable.mountain2,
            R.drawable.museum2,
            R.drawable.pool2,
            R.drawable.rainforest2,
            R.drawable.volcano2,
            R.drawable.waterfall2,
            R.drawable.zoo2
    };

    public String[] answerList = {
            "amusementpark",
            "aquarium",
            "beach",
            "cinema",
            "island",
            "mountain",
            "museum",
            "pool",
            "rainforest",
            "volcano",
            "waterfall",
            "zoo"
    };

    public char[] answer;

    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        randUsed = new HashSet<Integer>();
        wordsCount = 0;

        initView();

        audio1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    audio1.setLanguage(Locale.US);
                }
            }
        });
    }

    public void initView() {
        gridViewAnswer = (GridView)findViewById(R.id.gridViewAnswer);
        gridViewLetters = (GridView)findViewById(R.id.gridViewLetters);

        imgViewQuestion = (ImageView)findViewById(R.id.imgWord1);
        imgViewQuestion2 = (ImageView)findViewById(R.id.imgWord2);

        setupList();

        btnReview = (Button) findViewById(R.id.btnReview);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                for(int i = 0; i < Common.userSubmitAnswer.length; i++) {
                    result = String.valueOf(Common.userSubmitAnswer);
                }
                if(result.equals(correct_answer)){
                    String texto = "Nice, you guessed the word \"" + result + "\" correctly!";
                    Toast toast1 = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT);
                    View customT = toast1.getView();
                    customT.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorOceanBlue));
                    TextView t = customT.findViewById(android.R.id.message);
                    t.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
                    toast1.show();

                    //Reset
                    Common.count = 0;
                    Common.userSubmitAnswer = new char[correct_answer.length()];

                    //Set Adapter
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupEmptyList(), getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);

                    GridViewLettersAdapter lettersAdapter = new GridViewLettersAdapter(lettersSource, getApplicationContext(), PlacesActivity.this);
                    gridViewLetters.setAdapter(lettersAdapter);
                    lettersAdapter.notifyDataSetChanged();

                    setupList();

                } else {
                    String texto = "Try again!";
                    Toast toast1 = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT);
                    View customT = toast1.getView();
                    customT.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorOceanBlue));
                    TextView t = customT.findViewById(android.R.id.message);
                    t.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
                    toast1.show();
                }
            }
        });
    }

    public void setupList() {
        //Random images
        Random random = new Random();
        if(wordsCount < image_list1.length-1) {
            int aux = random.nextInt(image_list1.length);
            while(randUsed.contains(aux)){
                aux = random.nextInt(image_list1.length);
            }
            randUsed.add(aux);
            wordsCount++;
            int imageSelected = image_list1[aux];
            imgViewQuestion.setImageResource(imageSelected);
            imageSelected = image_list2[aux];
            imgViewQuestion2.setImageResource(imageSelected);

            correct_answer = answerList[aux];
            answer = correct_answer.toCharArray();

            Common.userSubmitAnswer = new char[answer.length];

            //Add answer character to list
            lettersSource.clear();
            for (char item : answer) {
                //Agrega la palabra a la lista.
                lettersSource.add(String.valueOf(item));
            }

            //Add random characters to letters suggestion list.
            for (int i = answer.length; i < answer.length * 2; i++) {
                lettersSource.add(Common.alphabetChars[random.nextInt(Common.alphabetChars.length)]);
            }

            //Sort random
            Collections.shuffle(lettersSource);

            //Set GridViews
            answerAdapter = new GridViewAnswerAdapter(setupEmptyList(), this);
            lettersAdapter = new GridViewLettersAdapter(lettersSource, this, this);

            answerAdapter.notifyDataSetChanged();
            lettersAdapter.notifyDataSetChanged();

            gridViewLetters.setAdapter(lettersAdapter);
            gridViewAnswer.setAdapter(answerAdapter);


        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(PlacesActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Congratulations!!");
            builder.setMessage("You guessed all the words correctly, good job!" + "\n" +
                    "Do you want to try again? ");

            builder.setNegativeButton("Return to Main Menu", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    wordsCount = 0;
                    randUsed.clear();
                    initView();
                }
            });
            builder.show();
        }
    }

    public char[] setupEmptyList() {
        char result[] = new char[answer.length];
        for(int i = 0; i < answer.length; i++){
            result[i] = ' ';
        }
        return result;
    }

    public void audios(View v){
        String toSpeak = correct_answer;
        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        if(!audio1.isSpeaking()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                audio1.speak(correct_answer, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                audio1.speak(correct_answer, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

}
