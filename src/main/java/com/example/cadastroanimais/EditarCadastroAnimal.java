package com.example.cadastroanimais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.cadastroanimais.Constantes.URL_IMAGEM;

/**
 * Classe que possibilita de editar o Animal
 */
public class EditarCadastroAnimal extends AppCompatActivity {

    Boolean flagImagemAnimal;
    ImageView imagemAnimal;
    EditText setIdade, setNome, setBreveDescricao, setRaca;
    Button botaoFinalizar, botaoEditar, botaoApagar;
    Spinner spinnerEspecie, spinnerSexo, spinnerCondicao;


    Button BotaoEditar;

    Uri imageURI;
    private static final int PICK_IMAGE = 100;

    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;


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

        //SETA OS SPINNERS
        //INICIALIZA SPINNER ESPECIE DO ANIMAL
        spinnerEspecie = findViewById(R.id.spinnerEspecieAnimal);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Especies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEspecie.setAdapter(adapter);

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






        //EDITA A FOTO DE UM ANIMAL
        imagemAnimal = (ImageView) findViewById(R.id.imagemAnimal);
        botaoEditar = (Button) findViewById(R.id.botaoeEditar);
        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagImagemAnimal = true;
                selectImage();
            }
        });

        botaoApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeAnimal();
            }
        });

        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidaCadastro()) {
                    editAnimal();
                }
            }
        });

        //CARREGAR OS dados do Animal nos campos
        carregaAnimal();
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
        final String especie = spinnerEspecie.getItemAtPosition(pos).toString();


        //pegando o id do animal pelo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Animal", Context.MODE_PRIVATE);

        String id_animal_ = "";
        if(sharedPreferences.contains("id_animal")){

            id_animal_ = sharedPreferences.getString("id_animal","");

        }else{
            //se nao tem o sharedPreferences vai pra tela de login
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

        String attImage_ = "", image_ = "";
        if(flagImagemAnimal == true){
            attImage_ = "true";

            //fazendo a transformacao da imagem para string encode64, para se poder mandar a imagem para o webServer
            image_ = imageToString(bitmap);
        }else{
            attImage_ = "false";
            image_ = "false";
        }

        //colocando os valores finais nas variaveis
        final String id_animal = id_animal_;
        final String attImage = attImage_;
        final String image = image_;

        //fazendo o stringRequest para fazer o request ao WebServer
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_EDITA_ANIMAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //se nao deu erro
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(),"Animal Editado com sucesso!", Toast.LENGTH_LONG).show();

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
                                Toast.makeText(getApplicationContext(), "Animal Excluido com sucesso!", Toast.LENGTH_LONG).show();

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

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagemAnimal.setImageBitmap(bitmap);

        }
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] array = stream.toByteArray();
        return Base64.encodeToString(array, 0);
    }

    /**
     * Funcao que verifica se o cadastro é válido,
     * Ou seja, se não há espaços em branco
     * @return true - Valido || false - Invalido
     */
    private boolean ValidaCadastro() {
        boolean valido = true;

        String nomeAnimal = setNome.getText().toString();
        String raca = setRaca.getText().toString();
        String idade = setIdade.getText().toString();
        String descricao = setBreveDescricao.getText().toString();

        boolean res = false;

        if (res = Validacao.isCampoVazio(nomeAnimal)) {
            setNome.requestFocus();
            setNome.setError("Erro");
        } else if (res = Validacao.isCampoVazio(raca)) {
            setRaca.requestFocus();
            setRaca.setError("Erro");
        } else if (res = Validacao.isCampoVazio(idade)) {
            setIdade.requestFocus();
            setIdade.setError("Erro");

        } else if (res = Validacao.isCampoVazio(descricao)) {
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


    private void carregaAnimal(){
        //pegando o id da pessoa pelo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Animal", Context.MODE_PRIVATE);

        String id_animal_ = "";
        if(sharedPreferences.contains("id_animal")){

            id_animal_= sharedPreferences.getString("id_animal","");

        }else{
            //se nao tem o sharedPreferences vai pra tela de login
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

        final String id_animal = id_animal_;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_BUSCAR_ANIMAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                //colocando os valores do JSON nos TextView's
                                setRaca.setText( obj.getString("raca"));
                                setIdade.setText( Integer.toString(obj.getInt("idade")) );
                                setBreveDescricao.setText(obj.getString("descricao"));
                                setNome.setText(obj.getString("nome"));

                                //carregando os valores dos spinners
                                String condicao = obj.getString("condicao");
                                String sexo = obj.getString("sexo");
                                String especie = obj.getString("especie");

                                //spinner Especie
                                int pos = 0;
                                for(int i = 0; i < spinnerEspecie.getAdapter().getCount(); i++){

                                    if( spinnerEspecie.getItemAtPosition(i).toString().trim().equals(especie) ){
                                        pos = i;
                                        break;
                                    }

                                }

                                spinnerEspecie.setSelection(pos);

                                //spinner Condicao
                                pos = 0;
                                for(int i = 0; i < spinnerCondicao.getAdapter().getCount(); i++){

                                    if( spinnerCondicao.getItemAtPosition(i).toString().trim().equals(condicao) ){
                                        pos = i;
                                        break;
                                    }

                                }

                                spinnerCondicao.setSelection(pos);

                                //spinner Sexo
                                pos = 0;
                                for(int i = 0; i < spinnerSexo.getAdapter().getCount(); i++){

                                    if( spinnerSexo.getItemAtPosition(i).toString().trim().equals(sexo) ){
                                        pos = i;
                                        break;
                                    }

                                }

                                spinnerSexo.setSelection(pos);

                                //colocando a imagen
                                final String image_url = URL_IMAGEM + obj.getString("image_name");
                                Picasso.with(EditarCadastroAnimal.this).load(image_url).into(imagemAnimal);

                            }else{
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
                Map<String, String> params = new HashMap<>();
                params.put("id_animal", id_animal );
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

}
