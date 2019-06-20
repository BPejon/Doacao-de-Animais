package com.example.cadastroanimais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuCadastro extends AppCompatActivity {

    Button cadastroPessoaFisica;
    Button cadastroOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cadastro);

        cadastroPessoaFisica = findViewById(R.id.botaoCadastroPessoaFisica);
        cadastroOng = findViewById(R.id.botaoCadastroOng);
    }

    public void botaoCadastroPessoaFisica(View view){
        Intent i = new Intent (MenuCadastro.this, TelaCadastroPessoaFisica.class);
        startActivity(i);

    }

    public void botaoCadastroOng (View view){
        Intent intent = new Intent(this, CadastrarONG.class);
        startActivity(intent);
    }

}
