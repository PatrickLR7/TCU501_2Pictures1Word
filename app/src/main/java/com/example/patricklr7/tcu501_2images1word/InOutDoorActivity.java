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
import java.util.List;

/**
 * Created by Patrick on 2/6/2018.
 */

public class InOutDoorActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public GridViewLettersAdapter lettersAdapter;
    public Button btnReview;
    public GridView gridViewAnswer, gridViewLetters;
    public ImageView imgViewQuestion;
    public ImageView imgViewQuestion2;
    public int [] image_list = {
            R.drawable.basket1,
            R.drawable.basket2,
            R.drawable.boardgame1,
            R.drawable.boardgame2,
            R.drawable.cards1,
            R.drawable.cards2,
            R.drawable.dancing1,
            R.drawable.dancing2,
            R.drawable.fishing1,
            R.drawable.fishing2,
            R.drawable.kayaking1,
            R.drawable.kayaking2,
            R.drawable.reading1,
            R.drawable.reading2,
            R.drawable.running1,
            R.drawable.running2,
            R.drawable.skateboarding1,
            R.drawable.skateboarding2,
            R.drawable.soccer1,
            R.drawable.soccer2,
            R.drawable.videogames1,
            R.drawable.videogames2
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

        btnReview = (Button) findViewById(R.id.btnReview);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                for(int i = 0; i < Common.userSubmitAnswer.length; i++) {
                    result += String.valueOf(Common.userSubmitAnswer);
                }
                if(result.equals(correct_answer)){
                    Toast.makeText(getApplicationContext(), "Nice, you guessed the word " + result + " correctly!", Toast.LENGTH_LONG);
                }
            }
        });
    }
}
