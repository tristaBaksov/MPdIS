package com.example.myapplication2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int idx = 0;
    TextView tv;
    EditText ed;
    int colorVals[] = {R.color.start, R.color.mid, R.color.last};

    private boolean isLarge = false;
    private boolean isBoldItalic = false;
    private boolean isSerif = false;
    private boolean isGreenBg = false;
    private int caseState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.modtext);
        ed = findViewById(R.id.edtext);
        LinearLayout mainLayout = findViewById(R.id.mainLayout);

        // Изменение цвета текста по очереди из массива
        Button chf = findViewById(R.id.change);
        chf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                tv.setTextColor(getResources().getColor(colorVals[idx]));
                idx = (idx + 1) % colorVals.length;
            }
        });

        // Перенос текста из EditText в TextView
        Button mvb = findViewById(R.id.move);
        mvb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String str = ed.getText().toString();
                tv.setText(str);
            }
        });

        // Изменение цвета фона экрана
        Button btnBgColor = findViewById(R.id.btnBgColor);
        btnBgColor.setOnClickListener(v -> {
            if (isGreenBg) {
                mainLayout.setBackgroundColor(Color.WHITE);
            } else {
                mainLayout.setBackgroundColor(Color.parseColor("#C8E6C9"));
            }
            isGreenBg = !isGreenBg;
        });

        // Изменение размера шрифта
        Button btnFontSize = findViewById(R.id.btnFontSize);
        btnFontSize.setOnClickListener(v -> {
            if (isLarge) {
                tv.setTextSize(32);
            } else {
                tv.setTextSize(48);
            }
            isLarge = !isLarge;
        });

        // Изменение жирности и начертания (Жирный / Наклонный)
        Button btnStyle = findViewById(R.id.btnStyle);
        btnStyle.setOnClickListener(v -> {
            if (isBoldItalic) {
                tv.setTypeface(null, Typeface.NORMAL);
            } else {
                tv.setTypeface(null, Typeface.BOLD_ITALIC);
            }
            isBoldItalic = !isBoldItalic;
        });

        // Изменение типа шрифта (Sans-serif / Serif)
        Button btnFont = findViewById(R.id.btnFont);
        btnFont.setOnClickListener(v -> {
            if (isSerif) {
                tv.setTypeface(Typeface.DEFAULT, tv.getTypeface() != null ? tv.getTypeface().getStyle() : Typeface.NORMAL);
            } else {
                tv.setTypeface(Typeface.SERIF, tv.getTypeface() != null ? tv.getTypeface().getStyle() : Typeface.NORMAL);
            }
            isSerif = !isSerif;
        });

        // Изменение регистра текста
        Button btnVertical1 = findViewById(R.id.btnVertical1);
        btnVertical1.setOnClickListener(v -> {
            String currentText = tv.getText().toString();
            if (caseState == 0) {
                tv.setText(currentText.toUpperCase());
                caseState = 1;
            } else if (caseState == 1) {
                tv.setText(currentText.toLowerCase());
                caseState = 0;
            }
        });
    }
}