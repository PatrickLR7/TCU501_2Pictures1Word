package com.example.patricklr7.tcu501_2images1word;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patricklr7.tcu501_2images1word.Adapter.GridViewAnswerAdapter;
import com.example.patricklr7.tcu501_2images1word.Adapter.GridViewLettersAdapter;
import com.example.patricklr7.tcu501_2images1word.Common.Common;
import com.example.patricklr7.tcu501_2images1word.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Patrick on 2/6/2018.
 */

public class InOutDoorActivity extends AppCompatActivity {

    public List<String> lettersSource = new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public GridViewLettersAdapter lettersAdapter;
    public Button btnReview;
    public GridView gridViewAnswer, gridViewLetters;
    public ImageView imgViewQuestion;
    public ImageView imgViewQuestion2;
    //public ArrayList<Integer> listRand;
    public HashSet<Integer> randUsed;
    public int wordsCount;
    public TextToSpeech audio1;
    public int answerPos;

    public int[] image_list1 = {
            R.drawable.basket1,
            R.drawable.boardgame1,
            R.drawable.cards1,
            R.drawable.dancing1,
            R.drawable.fishing1,
            R.drawable.kayaking1,
            R.drawable.reading1,
            R.drawable.running1,
            R.drawable.skateboarding1,
            R.drawable.soccer1,
            R.drawable.videogames1,
            R.drawable.painting1
    };

    public int[] image_list2 = {
            R.drawable.basket2,
            R.drawable.boardgame2,
            R.drawable.cards2,
            R.drawable.dancing2,
            R.drawable.fishing2,
            R.drawable.kayaking2,
            R.drawable.reading2,
            R.drawable.running2,
            R.drawable.skateboarding2,
            R.drawable.soccer2,
            R.drawable.videogames2,
            R.drawable.painting2
    };

    public String[] answerList = {
            "basketball",
            "boardgame",
            "cards",
            "dancing",
            "fishing",
            "kayaking",
            "reading",
            "running",
            "skateboarding",
            "soccer",
            "videogames",
            "painting"
    };

    public String[] hints = {
            "We met at a school basketball game.",
            "I love to play the monopoly board game.",
            "My father used to play cards with my uncle.",
            "The kids started taking dancing classes and they had a lot of fun",
            "I like to go fishing with my grandfather, it’s his favorite hobby!",
            "Kayaking is a water sport practiced in rivers.",
            "Rachel likes reading Harry Potter books.",
            "Running is a great exercise to maintain a good health.",
            "While skateboarding you have to use safety equipment, like a helmet.",
            "Alex and Jonathan play soccer in the park every day.",
            "Video games are awesome, but you should probably take a break after two hours.",
            "Anthony likes painting pictures of the mountains."
    };

    public char[] answer;

    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inoutdoor);

        randUsed = new HashSet<Integer>();
        wordsCount = 0;
        answerPos = -1;

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

                    GridViewLettersAdapter lettersAdapter = new GridViewLettersAdapter(lettersSource, getApplicationContext(), InOutDoorActivity.this);
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
            answerPos = aux;

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
            AlertDialog.Builder builder = new AlertDialog.Builder(InOutDoorActivity.this);
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
        //String toSpeak = correct_answer;
        String toSpeak = "";
        if(answerPos != -1){
            toSpeak = hints[answerPos];
        }
        if(toSpeak != null) {
            //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
            if (!audio1.isSpeaking()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    audio1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    audio1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
    }
}
