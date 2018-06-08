package com.example.patricklr7.tcu501_2images1word;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.patricklr7.tcu501_2images1word.Adapter.GridViewAnswerAdapter;
import com.example.patricklr7.tcu501_2images1word.Adapter.GridViewLettersAdapter;
import com.example.patricklr7.tcu501_2images1word.Common.Common;
import com.example.patricklr7.tcu501_2images1word.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
            R.drawable.videogames2
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
            "videogames"
    };

    public char[] answer;

    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inoutdoor);


        initView();

    }

    private void initView() {
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
                    result += String.valueOf(Common.userSubmitAnswer);
                }
                if(result.equals(correct_answer)){
                    Toast.makeText(getApplicationContext(), "Nice, you guessed the word " + result + " correctly!", Toast.LENGTH_LONG).show();

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
                    Toast.makeText(InOutDoorActivity.this, "Inténtalo de nuevo!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupList() {
        //Random images
        Random random = new Random();
        int aux = random.nextInt(image_list1.length);
        int imageSelected = image_list1[aux];
        imgViewQuestion.setImageResource(imageSelected);
        imageSelected = image_list2[aux];
        imgViewQuestion2.setImageResource(imageSelected);

        correct_answer = answerList[aux];
        answer = correct_answer.toCharArray();

        Common.userSubmitAnswer = new char[answer.length];

        //Add answer character to list
        lettersSource.clear();
        for(char item:answer){
            //Agrega la palabra a la lista.
            lettersSource.add(String.valueOf(item));
        }

        //Add random characters to letters suggestion list.
        for (int i = answer.length; i<answer.length*2; i++){
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


    }

    private char[] setupEmptyList() {
        char result[] = new char[answer.length];
        for(int i = 0; i < answer.length; i++){
            result[i] = ' ';
        }
        return result;
    }
}
