package com.example.testrun;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    private EditText editTextRed, editTextGreen, editTextBlue;
    private Switch switchRed, switchGreen, switchBlue;
    private View colorDisplay;
    private Button resetButton;

    private float redValue = 1.0f, greenValue = 1.0f, blueValue = 1.0f; // Initial values
    private boolean redOn = true, greenOn = true, blueOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        seekBarRed = findViewById(R.id.seekBar_red);
        seekBarGreen = findViewById(R.id.seekBar_green);
        seekBarBlue = findViewById(R.id.seekBar_blue);
        editTextRed = findViewById(R.id.editText_red);
        editTextGreen = findViewById(R.id.editText_green);
        editTextBlue = findViewById(R.id.editText_blue);
        switchRed = findViewById(R.id.switch_red);
        switchGreen = findViewById(R.id.switch_green);
        switchBlue = findViewById(R.id.switch_blue);
        colorDisplay = findViewById(R.id.color_display);
        resetButton = findViewById(R.id.button_reset);

        // Set initial values
        updateUI();

        // Add listeners
        setupListeners();
    }

    private void setupListeners() {
        // Set up listeners for the red controls
        seekBarRed.setOnSeekBarChangeListener(new SimpleSeekBarListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                redValue = progress / 100f;
                editTextRed.setText(String.format(Locale.getDefault(), "%.2f", redValue));
                updateColorDisplay();
            }
        });

        editTextRed.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    redValue = Float.parseFloat(s.toString());
                    seekBarRed.setProgress((int) (redValue * 100));
                    updateColorDisplay();
                }
            }
        });

        switchRed.setOnCheckedChangeListener((buttonView, isChecked) -> {
            redOn = isChecked;
            seekBarRed.setEnabled(isChecked);
            editTextRed.setEnabled(isChecked);
            updateColorDisplay();
        });

        // Repeat for green and blue controls...

        resetButton.setOnClickListener(v -> {
            redValue = 1.0f;
            greenValue = 1.0f;
            blueValue = 1.0f;
            redOn = true;
            greenOn = true;
            blueOn = true;
            updateUI();
        });
    }

    private void updateUI() {
        seekBarRed.setProgress((int) (redValue * 100));
        seekBarGreen.setProgress((int) (greenValue * 100));
        seekBarBlue.setProgress((int) (blueValue * 100));
        editTextRed.setText(String.format(Locale.getDefault(), "%.2f", redValue));
        editTextGreen.setText(String.format(Locale.getDefault(), "%.2f", greenValue));
        editTextBlue.setText(String.format(Locale.getDefault(), "%.2f", blueValue));
        switchRed.setChecked(redOn);
        switchGreen.setChecked(greenOn);
        switchBlue.setChecked(blueOn);
        updateColorDisplay();
    }

    private void updateColorDisplay() {
        int color = Color.rgb(
                redOn ? (int) (redValue * 255) : 0,
                greenOn ? (int) (greenValue * 255) : 0,
                blueOn ? (int) (blueValue * 255) : 0
        );
        colorDisplay.setBackgroundColor(color);
    }
}