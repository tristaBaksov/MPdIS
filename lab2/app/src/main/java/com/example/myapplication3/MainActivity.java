package com.example.myapplication3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private ImageView wv1;
    private Button buttonStop;
    private MediaPlayer mediaPlayer; // Плеер для музыки

    // Массив уникальных аудиоресурсов, строго по порядку элементов списка (от 0 до 7)
    private final int[] soundResources = new int[] {
            R.raw.sound1, // Belgium (позиция 0)
            R.raw.sound2, // Canada (позиция 1)
            R.raw.sound3, // Denmark (позиция 2)
            R.raw.sound4, // England (позиция 3)
            R.raw.sound5, // Germany (позиция 4)
            R.raw.sound6, // Ireland (позиция 5)
            R.raw.sound7, // Korea South (позиция 6)
            R.raw.sound8  // Netherlands (позиция 7)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv1 = (ImageView) findViewById(R.id.imageView1);
        listView = (ListView) findViewById(R.id.list);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        String[] valuesq = new String[] {
                "Belgium",
                "Canada",
                "Denmark",
                "England",
                "Germany",
                "Ireland",
                "Korea South",
                "Netherlands"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                valuesq
        );

        listView.setAdapter(adapter);

        // Обработка нажатия на элементы списка
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int itemPosition = arg2;
                String itemValue = (String) listView.getItemAtPosition(arg2);

                // 1. Управление музыкой: освобождаем старый плеер и запускаем уникальный трек по индексу позиции
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, soundResources[itemPosition]);
                mediaPlayer.start();

                // 2. Запуск анимаций (вращение флага + уникальная анимация строки списка)
                switch (itemPosition) {
                    case 0: // Belgium
                        wv1.setImageResource(R.drawable.image1);
                        wv1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate));
                        arg1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_scale));
                        break;
                    case 1: // Canada
                        wv1.setImageResource(R.drawable.image2);
                        wv1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate));
                        arg1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_from_left));
                        break;
                    case 2: // Denmark
                        wv1.setImageResource(R.drawable.image3);
                        wv1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate));
                        arg1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_from_right));
                        break;
                    case 3: // England
                        wv1.setImageResource(R.drawable.image4);
                        wv1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate));
                        arg1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_from_top));
                        break;
                    case 4: // Germany
                        wv1.setImageResource(R.drawable.image5);
                        wv1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate));
                        arg1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_from_bottom));
                        break;
                    case 5: // Ireland
                        wv1.setImageResource(R.drawable.image6);
                        wv1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate));
                        arg1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_left_rotate));
                        break;
                    case 6: // Korea South
                        wv1.setImageResource(R.drawable.image7);
                        wv1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate));
                        arg1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_right_rotate));
                        break;
                    case 7: // Netherlands
                        wv1.setImageResource(R.drawable.image8);
                        wv1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate));
                        arg1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_scale_from_left));
                        break;
                }

                // Вывод сообщения
                Toast.makeText(
                        getApplicationContext(),
                        "Country: " + itemValue + " | Position: " + itemPosition,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // 3. Обработка нажатия на кнопку остановки музыки
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.release();
                    mediaPlayer = null;
                    Toast.makeText(getApplicationContext(), "Музыка остановлена", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Музыка и так не играет", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Безопасное освобождение ресурсов плеера при уничтожении активности
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}