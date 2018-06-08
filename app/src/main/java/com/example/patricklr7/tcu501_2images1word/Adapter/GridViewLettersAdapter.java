package com.example.patricklr7.tcu501_2images1word.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.patricklr7.tcu501_2images1word.Common.Common;
import com.example.patricklr7.tcu501_2images1word.InOutDoorActivity;
import com.example.patricklr7.tcu501_2images1word.MainActivity;
import com.example.patricklr7.tcu501_2images1word.R;

import java.util.List;

/**
 * Created by Patrick on 3/6/2018.
 */

public class GridViewLettersAdapter extends BaseAdapter {

    private List<String> lSuggestSource;
    private Context context;
    private InOutDoorActivity inOutDoorActivity;

    public GridViewLettersAdapter(List<String> lSuggestSource, Context context, InOutDoorActivity inOutDoorActivity) {
        this.lSuggestSource = lSuggestSource;
        this.context = context;
        this.inOutDoorActivity = inOutDoorActivity;
    }

    @Override
    public int getCount() {
        return lSuggestSource.size();
    }

    @Override
    public Object getItem(int i) {
        return lSuggestSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Button button1;
        if(view == null){
            if(lSuggestSource.get(i).equals("null")){
                button1 = new Button(context);
                button1.setLayoutParams(new GridView.LayoutParams(85, 85));
                button1.setPadding(8, 8, 8,  8);
                button1.setBackgroundColor(Color.DKGRAY);
            }
            else{
                button1 = new Button(context);
                button1.setLayoutParams(new GridView.LayoutParams(85, 85));
                button1.setPadding(8, 8, 8,  8);
                button1.setBackgroundColor(Color.DKGRAY);
                button1.setTextColor(ContextCompat.getColor(context, R.color.colorOrangeDark));
                button1.setText(lSuggestSource.get(i));
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //If correct answer contains character user just clicked,
                        //add it into answer list.
                        if(String.valueOf(inOutDoorActivity.answer).contains(lSuggestSource.get(i))){
                            char compare = lSuggestSource.get(i).charAt(0);

                            for(int i = 0; i < inOutDoorActivity.answer.length; i++) {
                                if(compare == inOutDoorActivity.answer[i]){
                                    Common.userSubmitAnswer[i] = compare;
                                }
                            }

                            //Update Interface
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.userSubmitAnswer, context);
                            inOutDoorActivity.gridViewAnswer.setAdapter(answerAdapter);
                            answerAdapter.notifyDataSetChanged();

                            //Remove letter that was already used.
                            inOutDoorActivity.lettersSource.set(i, "null");
                            inOutDoorActivity.lettersAdapter = new GridViewLettersAdapter(inOutDoorActivity.lettersSource, context, inOutDoorActivity);
                            inOutDoorActivity.gridViewLetters.setAdapter(inOutDoorActivity.lettersAdapter);
                            inOutDoorActivity.lettersAdapter.notifyDataSetChanged();

                        } else{
                            //Remove letter that was already used.
                            inOutDoorActivity.lettersSource.set(i, "null");
                            inOutDoorActivity.lettersAdapter = new GridViewLettersAdapter(inOutDoorActivity.lettersSource, context, inOutDoorActivity);
                            inOutDoorActivity.gridViewLetters.setAdapter(inOutDoorActivity.lettersAdapter);
                            inOutDoorActivity.lettersAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        } else{
            button1 = (Button)view;
        }
        return button1;
    }
}
