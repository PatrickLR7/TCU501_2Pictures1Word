package com.example.patricklr7.tcu501_2images1word;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.patricklr7.tcu501_2images1word.Adapter.GridViewAnswerAdapter;
import com.example.patricklr7.tcu501_2images1word.Adapter.GridViewLettersAdapter;
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
    public Button btnSubmit;
    public GridView gridViewAnswer, gridViewLetters;
    public ImageView imgViewQuestion;
    public int [] image_list = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inoutdoor);
    }
}
