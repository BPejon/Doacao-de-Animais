package com.example.cadastroanimais;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity{

    boolean flagImagemAnimal;

    ImageView imagemAnimal;
    EditText setIdade, setNome, setBreveDescricao, setRaca;
    Button botaoFinalizar, botaoEditar;
    Spinner spinnerEspecie, spinnerSexo, spinnerCondicao;

    Uri imageURI;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        flagImagemAnimal = false;

        setIdade = (EditText)findViewById(R.id.setIdade);
        setNome = (EditText) findViewById(R.id.setNomeAnimal);
        setBreveDescricao = findViewById(R.id.setBreveDescricao);
        setRaca = findViewById(R.id.setRacaAnimal);

        //INICIALIZA SPINNER ESPECIE DO ANIMAL
        spinnerEspecie = findViewById(R.id.spinnerEspecieAnimal);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Especies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEspecie.setAdapter(adapter);

        /*PEGAR INFORMACAO DO SPINNER
        String selectec = spinnerEspecie.getSelectedItem().toString();
        /Inicializa com o dado escolhido
        spinnerEspecie.setSelection(3);
        */

        //INICIALIZA SPINNER SEXO DO ANIMAL
        spinnerSexo = findViewById(R.id.spinnerSexoAnimal);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Sexo, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter2);

        //INICIALIZA SPINNER CONDICAO DO ANIMAL
        spinnerCondicao = findViewById(R.id.spinnerCondicaoanimal);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.Condicao, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondicao.setAdapter(adapter3);



        //INSIRE A FOTO DE UM ANIMAL
        imagemAnimal = (ImageView) findViewById(R.id.imagemAnimal);
        botaoEditar = (Button) findViewById(R.id.botaoeEditar);
        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagImagemAnimal = true;
                getImageFromAlbum();
            }
        });
    }

    /**
     *Pega uma imagem da galeria e coloca em image view
     */
    public void getImageFromAlbum() {
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

    /**
     * Função que finaliza o cadastro do animal e retorna para a aba de edicao
     * @param Botão Finalizar inscricao
     */
    public void FinalizarCadastro(View view){
        if(ValidaCadastro()) {
            Intent intent = new Intent(this, EditarCadastroAnimal.class);
            startActivity(intent);
        }
    }

    /*
    ImageView imagemAnimal;
    EditText setIdade, setNome, setBreveDescricao;
    Button botaoFinalizar, botaoEditar;
    Spinner spinnerEspecie, spinnerSexo, spinnerCondicao;
    */
    private boolean ValidaCadastro() {
        boolean valido = true;

        String nomeAnimal = setNome.getText().toString();
        String raca = setRaca.getText().toString();
        String idade = setIdade.getText().toString();
        String descricao = setBreveDescricao.getText().toString();

        boolean res = false;

        if (res = isCampoVazio(nomeAnimal)) {
            setNome.requestFocus();
            setNome.setError("Erro");
        } else if (res = isCampoVazio(raca)) {
            setRaca.requestFocus();
            setRaca.setError("Erro");
        } else if (res = isCampoVazio(idade)) {
            setIdade.requestFocus();
            setIdade.setError("Erro");

        } else if (res = isCampoVazio(descricao)) {
            setBreveDescricao.requestFocus();
            setBreveDescricao.setError("Erro");
        }

        /*MENSAGEM DE ERRO NOS SPINNERS

        } else if (false) {
            spinnerEspecie.requestFocus();
            System.out.printf("cond");
        }else if (res = !(spinnerEspecie.isDirty())){
            spinnerEspecie.requestFocus();
            TextView errorText = (TextView)spinnerEspecie.getSelectedView();
            errorText.setError("anything here, just to add the icon");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("my actual error text");//changes the selected item text to this
        }else if (false){
            spinnerSexo.requestFocus();
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Aviso!");
            alerta.setMessage("Há campos inválidos ou em branco!");
            alerta.setNeutralButton("Ok", null);
            alerta.show();
            System.out.printf("sexo");
        }*/

        /*
        TextView errorText = (TextView)spinnerSexo.getSelectedView();
        errorText.setError("anything here, just to add the icon");
        errorText.setTextColor(Color.RED);//just to highlight that this is an error
        errorText.setText("my actual error text");//changes the selected item text to this
        */

        if(res){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Aviso!");
            alerta.setMessage("Há campos inválidos ou em branco!");
            alerta.setNeutralButton("Ok", null);
            alerta.show();
            valido = false;

        }

        return valido;


    }

    /**
     *Verifica se o campo está vazio
     * @param string a ser avaliada
     * @return falso se nao for vazio || true se for vazio
     */
    private boolean isCampoVazio(String valor){
        boolean resultado = (valor.trim().isEmpty());
        return resultado;
    }
}
