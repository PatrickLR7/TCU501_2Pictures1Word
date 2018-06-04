package com.example.patricklr7.tcu501_2images1word.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.patricklr7.tcu501_2images1word.InOutDoorActivity;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
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
                button1.setTextColor(context.getResources().getColor(R.color.colorOrangeDark));
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                    }
                });
            }
        } else{
            button1 = (Button)view;
        }
        return button1;
    }
}
