package com.zybooks.diceroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class ColorsActivity extends AppCompatActivity {

    private final static String TAG = "ColorChooser";
    private SeekBar seekbar;
    private SeekBar seekbar2;
    private SeekBar seekbar3;
    private ImageView image;
    private int red;
    private int green;
    private int blue;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        seekbar = findViewById(R.id.sbGrayScale);
        seekbar2 = findViewById(R.id.GScale);
        seekbar3 = findViewById(R.id.BScale);
        image = findViewById(R.id.square);


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub

                color = Color.argb(255, progress, green, blue);
                Intent intent = getIntent();
                intent.putExtra("custom_color", color);
                image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                red = seekbar.getProgress();
                green = seekbar2.getProgress();
                blue = seekbar3.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {

                color = Color.argb(255, red, progress, blue);
                Intent intent = getIntent();
                intent.putExtra("custom_color", color);
                image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBarr) {
                red = seekbar.getProgress();
                green = seekbar2.getProgress();
                blue = seekbar3.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {

                color = Color.argb(255, red, green, progress);
                Intent intent = getIntent();
                intent.putExtra("custom_color", color);
                image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBarr) {
                red = seekbar.getProgress();
                green = seekbar2.getProgress();
                blue = seekbar3.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: // the up arrow
                int gray = seekbar.getProgress();
                int green = seekbar2.getProgress();
                int blue = seekbar3.getProgress();
                color = Color.argb(255, gray, green, blue);
                Intent intent = getIntent();
                intent.putExtra("custom_color", color);

                intent.putExtra("message", "Go Grizzly!");
                intent.putExtra("magic", 3);
                image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

                setResult(RESULT_OK, intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

}
