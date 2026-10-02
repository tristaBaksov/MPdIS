package com.example.myapplication3;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.webkit.*;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView imageView;

    // Массив изображений
    private final int[] imageResources = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView1);
        listView = findViewById(R.id.list);

        // Список стран
        String[] countries = new String[] {
                "Belgium",
                "Canada",
                "Denmark",
                "England",
                "Germany",
                "Ireland",
                "Korea South",
                "Netherlands"
        };

        // Создаем и привязываем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                countries
        );
        listView.setAdapter(adapter);

        // Загружаем анимации из папки res/anim
        final Animation combinedAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_and_scale);
        final Animation listAnim = AnimationUtils.loadAnimation(this, R.anim.list_anim);

        // Анимируем весь ListView при старте экрана
        listView.startAnimation(listAnim);

        // Обработчик клика по элементам списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listView.getItemAtPosition(position);

                // Анимируем выбранный элемент списка при нажатии
                view.startAnimation(listAnim);

                // Меняем картинку согласно позиции
                if (position >= 0 && position < imageResources.length) {
                    imageView.setImageResource(imageResources[position]);
                    // Запускаем объединенный эффект (вращение + увеличение)
                    imageView.startAnimation(combinedAnim);
                }

                Toast.makeText(
                        getApplicationContext(),
                        "Position : " + position + " ListItem: " + itemValue,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}