package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int darkCounter, brightCounter;
    int darkColor, brightColor;
    View[][] tiles = new View[4][4];
    TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources r = getResources();
        darkColor = r.getColor(R.color.dark);
        brightColor = r.getColor(R.color.bright);
        answer = findViewById(R.id.answer);
        tiles[0][0] = findViewById(R.id.t00);
        tiles[0][1] = findViewById(R.id.t01);
        tiles[0][2] = findViewById(R.id.t02);
        tiles[0][3] = findViewById(R.id.t03);
        tiles[1][0] = findViewById(R.id.t10);
        tiles[1][1] = findViewById(R.id.t11);
        tiles[1][2] = findViewById(R.id.t12);
        tiles[1][3] = findViewById(R.id.t13);
        tiles[2][0] = findViewById(R.id.t20);
        tiles[2][1] = findViewById(R.id.t21);
        tiles[2][2] = findViewById(R.id.t22);
        tiles[2][3] = findViewById(R.id.t23);
        tiles[3][0] = findViewById(R.id.t30);
        tiles[3][1] = findViewById(R.id.t31);
        tiles[3][2] = findViewById(R.id.t32);
        tiles[3][3] = findViewById(R.id.t33);

        brightCounter = 16;
        darkCounter = 0;
        generate();

    }

    public void changeColor(View v) {
        ColorDrawable d = (ColorDrawable) v.getBackground();
        if (d.getColor() == brightColor) {
            v.setBackgroundColor(darkColor);
            darkCounter++;
            brightCounter--;
        } else {
            v.setBackgroundColor(brightColor);
            brightCounter++;
            darkCounter--;
        }
    }

    public void generateOnClick(View v){
        generate();
    }

    public void generate() {
        Random rnd = new Random();
        for (int i = 0; i < 4; i++) {
            for(int j =0; j< 4; j++){
                boolean number = rnd.nextBoolean();
                if(number){
                    changeColor(tiles[i][j]);
                }
            }
        }
        if(brightCounter == 16){
            int x = rnd.nextInt(4);
            int y = rnd.nextInt(4);
            changeColor(tiles[x][y]);
        }
    }

    int getBit(int n, int k) {
        return (n >> k) & 1;
    }

    public void solve(String type){
        boolean exit = false;
        for(int i = 0; i < 65536; i++){
            String ans = "";
            int counter = 0;
            for(int j = 0; j < 4; j++){
                for(int k = 0; k < 4; k++){
                    int bit = getBit(i, counter);
                    if(counter % 4 == 0){
                        ans = ans + " ";
                    }
                    counter++;
                    ans = ans + bit;
                    if(bit == 1) {
                        tileClick(tiles[j][k]);
                    }
                }
            }
            if((type == "dark" && darkCounter == 16) || (type == "bright" && brightCounter == 16)){
                answer.setText("ans is" + ans);
                exit = true;
            }
            counter = 0;
            for(int j = 0; j < 4; j++){
                for(int k = 0; k < 4; k++){
                    int bit = getBit(i, counter);
                    counter++;
                    if(bit == 1) {
                        tileClick(tiles[j][k]);
                    }
                }
            }
            if(exit){
                return;
            }
        }
    }

    public void solveOnClick(View v){
        if(v == findViewById(R.id.dark)) {
            solve("dark");
        }
        if(v == findViewById(R.id.bright)) {
            solve("bright");
        }
    }

    public void tileClick(View tile){
        String tag = tile.getTag().toString();
        int x = tag.charAt(0) - '0', y = tag.charAt(1) - '0';

        changeColor(tile);
        for (int i = 0; i < 4; i++) {
            changeColor(tiles[x][i]);
            changeColor(tiles[i][y]);
        }
    }

    public void onClick(View v) {
        String tag = v.getTag().toString();
        int x = tag.charAt(0) - '0', y = tag.charAt(1) - '0';

        tileClick(v);

        if(brightCounter == 16 || darkCounter == 16){
            Toast toast = Toast.makeText(this, "You've won!",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
