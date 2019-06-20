package com.example.cadastroanimais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MeusPets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_pets);


        Intent intent = new Intent(MeusPets.this, MeusPets.class);
        startActivity(intent);
    }
}
