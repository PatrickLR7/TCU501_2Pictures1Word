package com.example.patricklr7.tcu501_2images1word.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.patricklr7.tcu501_2images1word.R;

/**
 * Created by Patrick on 3/6/2018.
 */

public class GridViewAnswerAdapter extends BaseAdapter{


    private char[] answerChars;
    private Context context;

    public GridViewAnswerAdapter(char[] answerChars, Context context) {
        this.answerChars = answerChars;
        this.context = context;
    }

    @Override
    public int getCount() {
        return answerChars.length;
    }

    @Override
    public Object getItem(int i) {
        return answerChars[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button button1;
        if(view == null){
            //Create a new button
            button1 = new Button(context);
            button1.setLayoutParams(new GridView.LayoutParams(85, 85));
            button1.setPadding(8, 8, 8,  8);
            button1.setBackgroundColor(Color.DKGRAY);
            button1.setTextColor(ContextCompat.getColor(context, R.color.colorOrangeLight));
            button1.setText(String.valueOf(answerChars[i]));
        } else {
            button1 = (Button)view;
        }
        return button1;
    }
}
