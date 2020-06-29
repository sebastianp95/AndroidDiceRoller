package com.zybooks.diceroller;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int MAX_DICE = 3;
    private static final String TAG = "DiceRoller";
    private final static int CHOOSER_CODE = 101;
    private TextView result;
    private ImageView image1, image2, image3;

    private int mVisibleDice;
    private Dice[] mDice;
    private ImageView[] mDiceImageViews;
    private Menu mMenu;
    private CountDownTimer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        result = findViewById(R.id.tvResult);
        image1 = findViewById(R.id.dice1);
        image2 = findViewById(R.id.dice2);
        image3 = findViewById(R.id.dice3);

        // Create an array of Dice
        mDice = new Dice[MAX_DICE];
        for (int i = 0; i < MAX_DICE; i++) {
            mDice[i] = new Dice(i + 1);
        }

        // Create an array of ImageViews
        mDiceImageViews = new ImageView[MAX_DICE];
        mDiceImageViews[0] = findViewById(R.id.dice1);
        mDiceImageViews[1] = findViewById(R.id.dice2);
        mDiceImageViews[2] = findViewById(R.id.dice3);

        // All dice are initially visible
        mVisibleDice = MAX_DICE;

        showDice();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case R.id.action_one:
                changeDiceVisibility(1);
                showDice();
                return true;

            case R.id.action_two:
                changeDiceVisibility(2);
                showDice();
                return true;

            case R.id.action_three:
                changeDiceVisibility(3);
                showDice();
                return true;

            case R.id.action_color:
                Intent intent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivityForResult(intent, CHOOSER_CODE);
                return true;
            case R.id.action_about:
                Intent intent2 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent2);
                return true;

            case R.id.action_stop:
                mTimer.cancel();
                item.setVisible(false);
                mMenu.findItem(R.id.action_roll).setVisible(true);
                return true;

            case R.id.action_roll:
                rollDice();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void rollDice() {


        mMenu.findItem(R.id.action_stop).setVisible(true);
        mMenu.findItem(R.id.action_roll).setVisible(false);
        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new CountDownTimer(2000, 100) {
            public void onTick(long millisUntilFinished) {
                for (int i = 0; i < mVisibleDice; i++) {
                    mDice[i].roll();

                }
                showDice();
            }

            public void onFinish() {

//                Log.v(TAG, log.toString());
                if (mVisibleDice == 1)
                    Log.v(TAG, mDice[0].getNumber() + "");
                if (mVisibleDice == 2)
                    Log.v(TAG, mDice[0].getNumber() + " " + mDice[1].getNumber());
                if (mVisibleDice == 3)
                    Log.v(TAG, mDice[0].getNumber() + " " + mDice[1].getNumber() + " " + mDice[2].getNumber());
                mMenu.findItem(R.id.action_stop).setVisible(false);
                mMenu.findItem(R.id.action_roll).setVisible(true);
            }
        }.start();
    }


    private void changeDiceVisibility(int numVisible) {
        mVisibleDice = numVisible;

        // Make dice visible
        for (int i = 0; i < numVisible; i++) {
            mDiceImageViews[i].setVisibility(View.VISIBLE);
        }

        // Hide remaining dice
        for (int i = numVisible; i < MAX_DICE; i++) {
            mDiceImageViews[i].setVisibility(View.GONE);
        }

    }

    private void showDice() {
        // Display only the number of dice visible
        for (int i = 0; i < mVisibleDice; i++) {
            Drawable diceDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                diceDrawable = getResources().getDrawable(mDice[i].getImageId(),
                        getApplicationContext().getTheme());

            } else {
                diceDrawable = getResources().getDrawable(mDice[i].getImageId());

            }

            mDiceImageViews[i].setImageDrawable(diceDrawable);
            mDiceImageViews[i].setContentDescription(Integer.toString(mDice[i].getNumber()));
//            Log.v(TAG, String.valueOf(mDice[i].getNumber()));

        }

    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "Just got back from chooser!");

        if (requestCode == CHOOSER_CODE && resultCode == RESULT_OK) {
            int color = data.getExtras().getInt("custom_color");

            String msg = data.getExtras().getString("message");
            int mag = data.getExtras().getInt("magic");

            Log.v(TAG, "message=" + msg + ", magic=" + mag);


//            result.setText("" + Color.red(color)); // use red b/c its always gray
//            result.setTextColor(Color.RED);
//            result.setBackgroundColor(color);

            image1.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            image2.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            image3.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        }
    }


}
