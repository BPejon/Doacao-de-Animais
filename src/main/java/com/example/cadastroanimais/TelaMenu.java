package com.example.cadastroanimais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaMenu extends AppCompatActivity {

    private Button MEUS_PETS, Adotar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);

        MEUS_PETS = findViewById(R.id.myPetsParaDoar);
        Adotar = findViewById(R.id.adotarPetButton);

        MEUS_PETS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaMenu.this, MeusPets.class);
                startActivity(intent);
            }
        });

        Adotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences preferencias = getSharedPreferences("Pessoa", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();

                editor.putString("id_pessoa", "1" );
                editor.apply();

                Intent intent = new Intent(TelaMenu.this, CadastroAnimal.class);
                startActivity(intent);
            }
        });
    }
}
