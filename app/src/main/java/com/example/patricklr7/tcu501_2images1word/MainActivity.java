package com.example.patricklr7.tcu501_2images1word;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.patricklr7.tcu501_2images1word.Common.Common;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {


    private int idTheme;
    private Spinner sp;
    private GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idTheme = 1;
        gifImageView = (GifImageView) findViewById(R.id.imgViewGif);
        sp = (Spinner) findViewById(R.id.spinnerTheme);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Indoor and Outdoor Activities"))
                {
                    idTheme = 1;
                    Common.themeID = idTheme;
                    gifImageView.setImageResource(R.drawable.animatedsoccer);
                } else if (selectedItem.equals("Wildlife at the Park")){
                    idTheme = 2;
                    Common.themeID = idTheme;
                    gifImageView.setImageResource(R.drawable.animatedmonkey);
                } else if (selectedItem.equals("Emergencies and Natural Disasters")){
                    idTheme = 3;
                    Common.themeID = idTheme;
                    gifImageView.setImageResource(R.drawable.animatedvolcano);
                } else if (selectedItem.equals("Oh, the Places You Will Go")){
                    idTheme = 4;
                    Common.themeID = idTheme;
                    gifImageView.setImageResource(R.drawable.animatedbeach);
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
                //
            }
        });

    }

    /**
     * Metodo para crear el menu.
     * @param menu layout con el menu.
     * @return true si se crea correctamente.
     */
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



    /**
     * Metodo para realizar una accion al seleccionar items del menu.
     * @param item una opcion del menu.
     * @return true si realiza la accion correctamente.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(id){
            case R.id.menu_Ins:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
                builder.setTitle(getText(R.string.tIns));
                builder.setMessage(getText(R.string.instrucciones));
                builder.setIcon(R.drawable.tcu501logo);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();

                }
            });
                AlertDialog dialog = builder.show();
                // Must call show() prior to fetching text view
                TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
                messageView.setGravity(Gravity.CENTER);
        }
        return true;
    }


    public void btnPlay(View v){
        Intent intent;
        switch (idTheme) {
            case 1:
                intent = new Intent(this, InOutDoorActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, WildlifeActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, EmergenciesDisastersActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, PlacesActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

}
