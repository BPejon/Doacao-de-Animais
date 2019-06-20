package com.example.cadastroanimais;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class EditarCadastroAnimal extends AppCompatActivity {

    Boolean flagMudancaImagemAnimal;
    ImageView imagemAnimal;
    EditText setIdade, setNome, setBreveDescricao;
    Button botaoFinalizar, botaoEditar;
    Spinner spinnerEspecie, spinnerSexo, spinnerCondicao;


    Button BotaoEditar;

    Uri imageURI;
    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cadastro_animal);


        //Verifica se trocou imagem
        flagMudancaImagemAnimal = false;

        //Intent intent  = getIntent();

        spinnerCondicao = (Spinner) findViewById(R.id.spinnerCondicaoanimal);
        spinnerEspecie = (Spinner) findViewById(R.id.spinnerEspecieAnimal);
        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexoAnimal);

        botaoEditar = (Button) findViewById(R.id.botaoeEditar);
        botaoFinalizar = (Button) findViewById(R.id.botaoFinalizarInscricao);

        setIdade = (EditText) findViewById(R.id.setIdade);
        setNome = (EditText) findViewById(R.id.setNomeAnimal);
        setBreveDescricao = (EditText) findViewById(R.id.setBreveDescricao);

        imagemAnimal = (ImageView) findViewById(R.id.imagemAnimal);



        //CARREGAR O NOME DO ANIMAL
        String texto = "texto a ser colocado e editado";
        setNome.setText(texto);

        //CARREGA A IDADE DO ANIMAL
        String numero = "adasd";
        setIdade.setText(numero);

        //CARREGAR BREVE DESCRICAO DO ANIMAL
        String breve = "Ele é gordo e fofo!!";
        setBreveDescricao.setText(breve);

        //EDITA A FOTO DE UM ANIMAL
        imagemAnimal = (ImageView) findViewById(R.id.imagemAnimal);
        botaoEditar = (Button) findViewById(R.id.botaoeEditar);
        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagMudancaImagemAnimal = true;
                getImageFromAlbum();
            }
        });
    }

    private void getImageFromAlbum() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data.getData();
            imagemAnimal.setImageURI(imageURI);
        }
    }

}
