package com.example.cadastroanimais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaMenu extends AppCompatActivity {

    private Button MEUS_PETS, Adotar, CadastrarPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);

        Adotar = findViewById(R.id.botaoAdotarPet);
        MEUS_PETS = findViewById(R.id.botaoMeusAnimais);
        CadastrarPet = findViewById(R.id.botaoDoarPet);


        Adotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TelaMenu.this, CatalogoAnimais.class);
                startActivity(intent);
            }
        });


        MEUS_PETS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaMenu.this, MeusPets.class);
                startActivity(intent);
            }
        });

        CadastrarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TelaMenu.this, CadastroAnimal.class);
                startActivity(i);
            }
        });
    }
}
