package com.example.work1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Metro extends AppCompatActivity {

    MyImageView joke;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        joke = (MyImageView) findViewById(R.id.c_joke);
        joke.setOnClickIntent(new MyImageView.OnViewClickListener()
        {

            @Override
            public void onViewClick(MyImageView view)
            {
                Toast.makeText(Metro.this, "Joke", 1000).show();
            }
        });
    }
}
