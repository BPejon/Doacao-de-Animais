package com.example.cadastroanimais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class EditarCadastroAnimal extends AppCompatActivity {

    Boolean flagImagemAnimal;
    ImageView imagemAnimal;
    EditText setIdade, setNome, setBreveDescricao, setRaca;
    Button botaoFinalizar, botaoEditar, botaoApagar;
    Spinner spinnerEspecie, spinnerSexo, spinnerCondicao;


    Button BotaoEditar;

    Uri imageURI;
    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cadastro_animal);


        //Verifica se trocou imagem
        flagImagemAnimal = false;

        //Intent intent  = getIntent();

        spinnerCondicao = (Spinner) findViewById(R.id.spinnerCondicaoanimal);
        spinnerEspecie = (Spinner) findViewById(R.id.spinnerEspecieAnimal);
        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexoAnimal);

        //Botoes
        botaoEditar = (Button) findViewById(R.id.botaoeEditar);
        botaoFinalizar = (Button) findViewById(R.id.botaoFinalizarInscricao);
        botaoApagar     =         findViewById(R.id.botaoRemover);

        setIdade = (EditText) findViewById(R.id.setIdade);
        setNome = (EditText) findViewById(R.id.setNomeAnimal);
        setBreveDescricao = (EditText) findViewById(R.id.setBreveDescricao);
        setRaca = findViewById(R.id.setRacaAnimal);

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
                flagImagemAnimal = true;
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

    private void editAnimal(){
        //fazendo a validacao dos dados nos campos

        //inicializando os valores
        final String nome = setNome.getText().toString();

        final String descricao = setBreveDescricao.getText().toString();
        final String idade = setIdade.getText().toString().trim();
        final String raca = setRaca.getText().toString();

        //valores dos spinners
        int pos = spinnerSexo.getSelectedItemPosition();
        final String sexo = spinnerSexo.getItemAtPosition(pos).toString().trim();
        pos = spinnerCondicao.getSelectedItemPosition();
        final String condicao = spinnerCondicao.getItemAtPosition(pos).toString().trim();
        pos = spinnerEspecie.getSelectedItemPosition();
        final String especie = spinnerEspecie.getItemAtPosition(pos).toString().trim();

        //pegando o id do animal pelo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Animal", Context.MODE_PRIVATE);

        String id_animal_ = "";
        if(sharedPreferences.contains("id_animal")){

            id_animal_ = sharedPreferences.getString("id_animal","");

        }else{
            //se nao tem o sharedPreferences vai pra tela de login
            //mudando para a tela de meus animais cadastrados
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

        String attImage_ = "", image_ = "";
        if(flagImagemAnimal == true){
            attImage_ = "true";

            //fazendo a transformacao da imagem para string encode64, para se poder mandar a imagem para o webServer
            Bitmap bitmap = BitmapFactory.decodeFile(imageURI.getPath() );
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] array = stream.toByteArray();
            image_ = Base64.encodeToString(array, 0);
        }else{
            attImage_ = "false";
            image_ = "false";
        }
        final String id_animal = id_animal_;
        final String attImage = attImage_;
        final String image = image_;

        //fazendo o stringRequest para fazer o request ao WebServer
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //se nao deu erro
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("Animal Inserido com sucesso!"), Toast.LENGTH_LONG).show();

                                //mudando para a tela de meus animais cadastrados
                                Intent intent = new Intent(EditarCadastroAnimal.this, MeusPets.class);
                                startActivity(intent);

                            }else{
                                //imprime a mensagem de erro
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //setando os valores para o Request
                Map<String, String> params = new HashMap<>();
                params.put("nome", nome);
                params.put("especie", especie);
                params.put("descricao", descricao);
                params.put("sexo", sexo);
                params.put("idade", idade);
                params.put("raca", raca);
                params.put("condicao", condicao);
                params.put("image",image);
                params.put("attImage", attImage);
                params.put("id_animal", id_animal);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void removeAnimal(){
        //fazendo a validacao dos dados nos campos

        //pegando o id do animal pelo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Animal", Context.MODE_PRIVATE);

        String id_animal_ = "";
        if(sharedPreferences.contains("id_animal")){

            id_animal_ = sharedPreferences.getString("id_animal","");

        }else{
            //se nao tem o sharedPreferences vai pra tela de login
            Toast.makeText(getApplicationContext(), "Erro ao remover animal.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MeusPets.class);
            startActivity(intent);
        }

        final String id_animal = id_animal_;

        //fazendo o stringRequest para fazer o request ao WebServer
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_REMOVE_ANIMAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //se nao deu erro
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("Animal Excluido com sucesso!"), Toast.LENGTH_LONG).show();

                                //mudando para a tela de meus animais cadastrados
                                Intent intent = new Intent(EditarCadastroAnimal.this, MeusPets.class);
                                startActivity(intent);

                            }else{
                                //imprime a mensagem de erro
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //setando os valores para o Request
                Map<String, String> params = new HashMap<>();
                params.put("id_animal", id_animal);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


}
