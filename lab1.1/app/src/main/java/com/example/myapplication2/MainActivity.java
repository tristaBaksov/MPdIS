package com.example.myapplication2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

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
            Typeface.SANS_SERIF, // Без засечек
            Typeface.SERIF,      // С засечками
            Typeface.MONOSPACE,  // Моноширинный
            Typeface.DEFAULT     // Стандартный
    };
    private int fontIdx = 0;

    private boolean isBoldItalic = false;
    private int caseState = 0;
    private boolean isColored = false;

    // Массив смайликов для замены текста
    private final String[] emojis = {"😀", "😎", "🚀", "🔥", "🎉", "🌟", "🐱", "🍕", "🤖", "💎"};

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
            public void onClick(View v) {
                tv.setTextColor(getResources().getColor(colorVals[idx]));
                idx = (idx + 1) % colorVals.length;
            }
        });

        // Перенос текста из EditText в TextView
        Button mvb = findViewById(R.id.move);
        mvb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ed.getText().toString();
                tv.setText(str);
            }
        });

        // 1. Изменение цвета фона экрана (4 варианта)
        Button btnBgColor = findViewById(R.id.btnBgColor);
        btnBgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgColorIdx = (bgColorIdx + 1) % bgColors.length;
                mainLayout.setBackgroundColor(bgColors[bgColorIdx]);
            }
        });

        // 2. Изменение размера шрифта (4 варианта)
        Button btnFontSize = findViewById(R.id.btnFontSize);
        btnFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontSizeIdx = (fontSizeIdx + 1) % fontSizes.length;
                tv.setTextSize(fontSizes[fontSizeIdx]);
            }
        });

        // Изменение жирности и начертания через Typeface.create
        Button btnStyle = findViewById(R.id.btnStyle);
        btnStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int targetStyle = isBoldItalic ? Typeface.NORMAL : Typeface.BOLD_ITALIC;
                tv.setTypeface(Typeface.create(fontFamilies[fontIdx], targetStyle));
                isBoldItalic = !isBoldItalic;
            }
        });

        // 3. Изменение типа шрифта (4 варианта)
        Button btnFont = findViewById(R.id.btnFont);
        btnFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontIdx = (fontIdx + 1) % fontFamilies.length;
                int currentStyle = isBoldItalic ? Typeface.BOLD_ITALIC : Typeface.NORMAL;
                tv.setTypeface(fontFamilies[fontIdx], currentStyle);
            }
        });

        // Изменение регистра текста
        Button btnVertical1 = findViewById(R.id.btnVertical1);
        btnVertical1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = tv.getText().toString();
                if (caseState == 0) {
                    tv.setText(currentText.toUpperCase());
                    caseState = 1;
                } else if (caseState == 1) {
                    tv.setText(currentText.toLowerCase());
                    caseState = 0;
                }
            }
        });

        // Кнопка с картинкой: меняет свой цвет и раскрашивает символы в TextView
        Button btnWithImage = findViewById(R.id.btnWithImage);
        btnWithImage.setOnClickListener(new View.OnClickListener() {
            @Override
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

                // Разноцветные буквы в TextView
                CharSequence text = tv.getText();
                SpannableString spannable = new SpannableString(text);
                Random random = new Random();

                for (int i = 0; i < text.length(); i++) {
                    int randomColor = Color.rgb(
                            random.nextInt(256),
                            random.nextInt(256),
                            random.nextInt(256)
                    );
                    spannable.setSpan(
                            new ForegroundColorSpan(randomColor),
                            i,
                            i + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }
                tv.setText(spannable);
            }
        });

        // Кнопка смайликов: замена букв чисто через String
        Button btnEmoji = findViewById(R.id.btnEmoji);
        btnEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = tv.getText().toString();
                String resultText = "";
                Random random = new Random();

                int i = 0;
                while (i < currentText.length()) {
                    int codePoint = currentText.codePointAt(i);

                    if (Character.isWhitespace(codePoint)) {
                        resultText += (char) codePoint;
                    } else {
                        resultText += emojis[random.nextInt(emojis.length)];
                    }

                    i += Character.charCount(codePoint);
                }

                tv.setText(resultText);
            }
        });
    }
}