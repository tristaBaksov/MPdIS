package com.example.myapplication3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private List<String> countryList;

    private ImageView wv1;
    private Button buttonStop, buttonAnimate, buttonAdd;
    private EditText editTextNewItem;
    private MediaPlayer mediaPlayer;

    // Массивы ресурсов с циклической безопасностью по остатку от деления
    private final int[] soundResources = new int[] {
            R.raw.sound1, R.raw.sound2, R.raw.sound3, R.raw.sound4,
            R.raw.sound5, R.raw.sound6, R.raw.sound7, R.raw.sound8
    };

    private final int[] imageResources = new int[] {
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8
    };

    // Своя анимация для каждого флага, порядок совпадает с порядком стран в списке
    private final int[] flagAnimations = new int[] {
            R.anim.anim_flag_flip,   // Belgium
            R.anim.anim_flag_leaf,   // Canada
            R.anim.anim_flag_bounce, // Denmark
            R.anim.anim_flag_slide,  // England
            R.anim.anim_flag_pulse,  // Germany
            R.anim.anim_flag_shake,  // Ireland
            R.anim.anim_rotate,      // Korea South
            R.anim.anim_flag_swing   // Netherlands
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv1 = findViewById(R.id.imageView1);
        recyclerView = findViewById(R.id.recyclerView);
        buttonStop = findViewById(R.id.buttonStop);
        buttonAnimate = findViewById(R.id.buttonAnimate);
        buttonAdd = findViewById(R.id.buttonAdd);
        editTextNewItem = findViewById(R.id.editTextNewItem);

        // Инициализация списка стран
        countryList = new ArrayList<>();
        countryList.add("Belgium");
        countryList.add("Canada");
        countryList.add("Denmark");
        countryList.add("England");
        countryList.add("Germany");
        countryList.add("Ireland");
        countryList.add("Korea South");
        countryList.add("Netherlands");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Инициализация адаптера с обработчиком кликов
        countryAdapter = new CountryAdapter(countryList, (position, view) -> {
            String itemValue = countryList.get(position);

              int soundIndex = position % soundResources.length;
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(MainActivity.this, soundResources[soundIndex]);
            mediaPlayer.start();

            // Смена картинки и анимация флага
            int imageIndex = position % imageResources.length;
            wv1.setImageResource(imageResources[imageIndex]);
            wv1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, flagAnimations[position % flagAnimations.length]));

            // Уникальная анимация строки списка
            int animType = position % 8;
            switch (animType) {
                case 0: view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_scale)); break;
                case 1: view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_from_left)); break;
                case 2: view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_from_right)); break;
                case 3: view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_from_top)); break;
                case 4: view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_from_bottom)); break;
                case 5: view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_left_rotate)); break;
                case 6: view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_right_rotate)); break;
                case 7: view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_scale_from_left)); break;
            }

            Toast.makeText(getApplicationContext(), "Country: " + itemValue + " | Pos: " + position, Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(countryAdapter);

        // Анимация появления списка при старте
        recyclerView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom));

        // Кнопка добавления новой страны из EditText
        buttonAdd.setOnClickListener(v -> {
            String newCountry = editTextNewItem.getText().toString().trim();
            if (!newCountry.isEmpty()) {
                countryList.add(newCountry);
                int newPosition = countryList.size() - 1;
                countryAdapter.notifyItemInserted(newPosition);
                recyclerView.scrollToPosition(newPosition);
                editTextNewItem.setText("");
                Toast.makeText(getApplicationContext(), "Добавлено: " + newCountry, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Введите название!", Toast.LENGTH_SHORT).show();
            }
        });

        // Кнопка повтора анимации списка
        buttonAnimate.setOnClickListener(v -> {
            recyclerView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_from_bottom));
            Toast.makeText(getApplicationContext(), "Анимация перезапущена", Toast.LENGTH_SHORT).show();
        });

        // Кнопка остановки музыки
        buttonStop.setOnClickListener(v -> {
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
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
        private final List<String> countryList;
        private final OnItemClickListener listener;

        public interface OnItemClickListener {
            void onItemClick(int position, View view);
        }

        public CountryAdapter(List<String> countryList, OnItemClickListener listener) {
            this.countryList = countryList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String country = countryList.get(position);
            holder.textView.setText(country);

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition(), holder.itemView);
                }
            });
        }

        @Override
        public int getItemCount() {
            return countryList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
// 3 лаба сначала делаем сервис потом из него отправляем интент на броадкаст ресивер а он через интент фильтр отправляет во вторую активность(2 вариант)