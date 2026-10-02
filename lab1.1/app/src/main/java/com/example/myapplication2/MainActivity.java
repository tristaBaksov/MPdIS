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

    // 1. Размеры текста (4 варианта)
    private final float[] fontSizes = {20f, 28f, 36f, 48f};
    private int fontSizeIdx = 0;

    // 2. Цвета фона (4 варианта)
    private final int[] bgColors = {
            Color.WHITE,
            Color.parseColor("#C8E6C9"), // Светло-зеленый
            Color.parseColor("#BBDEFB"), // Светло-синий
            Color.parseColor("#FFF9C4")  // Светло-желтый
    };
    private int bgColorIdx = 0;

    // 3. Типы шрифтов (4 варианта)
    private final Typeface[] fontFamilies = {
            Typeface.SERIF,      // С засечками
            Typeface.MONOSPACE,  // Моноширинный
            Typeface.DEFAULT     // Стандартный
    };
    private int fontIdx = 0;

    private boolean isBoldItalic = false;
    private int caseState = 0;
    private boolean isColored = false;

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

        // 1. Переключение цвета фона экрана (4 варианта)
        Button btnBgColor = findViewById(R.id.btnBgColor);
        btnBgColor.setOnClickListener(v -> {
            bgColorIdx = (bgColorIdx + 1) % bgColors.length;
            mainLayout.setBackgroundColor(bgColors[bgColorIdx]);
        });

        // 2. Переключение размера шрифта (4 варианта: 20sp -> 28sp -> 36sp -> 48sp)
        Button btnFontSize = findViewById(R.id.btnFontSize);
        btnFontSize.setOnClickListener(v -> {
            fontSizeIdx = (fontSizeIdx + 1) % fontSizes.length;
            tv.setTextSize(fontSizes[fontSizeIdx]);
        });

        // Изменение жирности и начертания (Normal / Bold_Italic)
        Button btnStyle = findViewById(R.id.btnStyle);
        btnStyle.setOnClickListener(v -> {
            if (isBoldItalic) {
                tv.setTypeface(tv.getTypeface(), Typeface.NORMAL);
            } else {
                tv.setTypeface(tv.getTypeface(), Typeface.BOLD_ITALIC);
            }
            isBoldItalic = !isBoldItalic;
        });

        // 3. Переключение типа шрифта (4 варианта: Sans-serif -> Serif -> Monospace -> Default)
        Button btnFont = findViewById(R.id.btnFont);
        btnFont.setOnClickListener(v -> {
            fontIdx = (fontIdx + 1) % fontFamilies.length;
            int currentStyle = (tv.getTypeface() != null) ? tv.getTypeface().getStyle() : Typeface.NORMAL;
            tv.setTypeface(fontFamilies[fontIdx], currentStyle);
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

        // Кнопка с картинкой
        Button btnWithImage = findViewById(R.id.btnWithImage);
        btnWithImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isColored) {
                    btnWithImage.setText("Цвет изменен!");
                    btnWithImage.setBackgroundColor(getResources().getColor(colorVals[idx]));
                } else {
                    btnWithImage.setBackgroundColor(Color.parseColor("#D0BCFF"));
                    btnWithImage.setText("Кнопка с картинкой");
                }
                idx = (idx + 1) % colorVals.length;
                isColored = !isColored;
            }
        });
    }
}