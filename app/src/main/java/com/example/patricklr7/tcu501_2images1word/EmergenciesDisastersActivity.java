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

public class EmergenciesDisastersActivity extends AppCompatActivity {

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
            R.drawable.avalanche1,
            R.drawable.caraccident1,
            R.drawable.drought1,
            R.drawable.earthquake1,
            R.drawable.eruption1,
            R.drawable.flood1,
            R.drawable.forestfire1,
            R.drawable.hurricane1,
            R.drawable.landslide1,
            R.drawable.thunderstorm1,
            R.drawable.tornado1,
            R.drawable.tsunami1
    };

    public int[] image_list2 = {
            R.drawable.avalanche2,
            R.drawable.caraccident2,
            R.drawable.drought2,
            R.drawable.earthquake2,
            R.drawable.eruption2,
            R.drawable.flood2,
            R.drawable.forestfire2,
            R.drawable.hurricane2,
            R.drawable.landslide2,
            R.drawable.thunderstorm2,
            R.drawable.tornado2,
            R.drawable.tsunami2
    };

    public String[] answerList = {
            "avalanche",
            "accident",
            "drought",
            "earthquake",
            "eruption",
            "flood",
            "forestfire",
            "hurricane",
            "landslide",
            "thunderstorm",
            "tornado",
            "tsunami"
    };

    public String[] hints = {
            "An avalanche occurs when a layer of snow collapses and slides downhill.",
            "Michael didn’t stop his car at the semaphore and he had an accident.",
            "Droughts occur when the is a lack of rain over an extended period of time.",
            "During an earthquake, you have to take cover under something sturdy.",
            "Last year the Turrialba volcano had several eruptions.",
            "A flood happens when an overflowing water submerges a dry land.",
            "The firemen came with several fire trucks to stop the forest fire.",
            "A hurricane is an intense tropical storm with powerful winds.",
            "A landslide is the movement of a mass of rock or earth down a slope.",
            "A thunderstorm is a storm with lightning and thunder.",
            "There are tornados powerful enough to destroy building’s ceilings.",
            "Tsunamis are huge waves of water that are usually caused by earthquakes."
    };

    public char[] answer;

    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencies_disasters);

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

                    GridViewLettersAdapter lettersAdapter = new GridViewLettersAdapter(lettersSource, getApplicationContext(), EmergenciesDisastersActivity.this);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(EmergenciesDisastersActivity.this);
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
