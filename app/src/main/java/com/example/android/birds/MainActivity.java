package com.example.android.birds;

import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static android.R.attr.animation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener{

    //TODO(1) Define fields
    final int NUMBER_OF_BURDS = 10;
    final int MINIMUM_DURATION = 500;
    final int MAXIMUM_ADDITIONAL_DURATION = 1000;
    Random random = new Random();
    RelativeLayout layout;
    int displayWidth, displayheight;
    int countShown = 0, countClicked = 0 ;


    /* Activity lifecycle methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Find the layout

        layout = (RelativeLayout) findViewById(R.id.relativeLayout);
        
        //Get the size of the device's screen

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayWidth = size.x;
        displayheight = size.y;
    }
    
    @Override
    public void onResume(){
        super.onResume();
        startPlaying();
    }

    /* Game play methods */
    private void startPlaying() {
        //Set this run's score (countClicked) to zero
        countClicked = countShown = 0;
        //Remove any images from the previous game
        layout.removeAllViews();
        showABurd();
    }

    void showABurd(){
        //Add a burd in some random place
        long duration = MINIMUM_DURATION + random.nextInt(MAXIMUM_ADDITIONAL_DURATION);

        ActionBar.LayoutParams params = new
                ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.leftMargin = random.nextInt(displayWidth) * 3 / 4;
        params.topMargin = random.nextInt(displayheight) * 5 /8;

        ImageView burd = new ImageView(this);
        burd.setImageResource(R.drawable.burd);
        burd.setLayoutParams(params);
        burd.setOnClickListener(this);
        burd.setVisibility(View.INVISIBLE);

        layout.addView(burd);

        AlphaAnimation animation = new AlphaAnimation(0.0F, 1.0F);
        animation.setDuration(duration);
        animation.setAnimationListener((this));

        //At first, the Burd is invisible
        //create an AlphaAnimation to make the Burd
        // fade in (from invisible to full visible)

        burd.startAnimation(animation);
    }

    /* OnClickListener Method*/

    public void onClick(View view){
        countClicked++;
        //Change the image of the Bird with a cheeseburger
        (ImageView) view).setImageResource(R.drawable.burd_burger);
        view.setVisibility(View.VISIBLE);
    }

    /* AnimationListener methods */

    @Override
    public void onAnimationStart(Animation animation) {

    }

    public void onAnimationEnd(Animation animation) {
        if(++countShown < NUMBER_OF_BURDS){
            showABurd(); //Again!
        } else {
            // Display the "Game Over" message
            Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    /* Menu methods */

    public boolean onCreateOptionsMenu(Menu menu){
        //Make the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        //Show the scores or start a new game
        switch (item.getItemId()) {
            case R.id.show_scores:
                showScores();
                return true;
            case R.id.play_again:
                startPlaying();
                return true;
    }
        return super.onOptionsItemSelected(item);
    }

    public void showScores(){
        //Get high score from shared preferences
        //If this score is great than the high score, update SharedPreferences
        //Display high score and this run's score
    }

}
