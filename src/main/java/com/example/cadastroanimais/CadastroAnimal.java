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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CadastroAnimal extends AppCompatActivity{

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
        setContentView(R.layout.activity_cadastro_animal);



        flagImagemAnimal = false;

        setIdade = (EditText)findViewById(R.id.setIdade);
        setNome = (EditText) findViewById(R.id.setNomeAnimal);
        setBreveDescricao = findViewById(R.id.setBreveDescricao);
        setRaca = findViewById(R.id.setRacaAnimal);

        botaoEditar = (Button) findViewById(R.id.botaoeEditar);
        botaoFinalizar = findViewById(R.id.botaoFinalizarInscricao);

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
        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagImagemAnimal = true;
                getImageFromAlbum();
            }
        });

        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ValidaCadastro()){

                    insereAnimal();

                }

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




    private void insereAnimal(){
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
        final String condicao = spinnerCondicao.getItemAtPosition(pos).toString();
        pos = spinnerEspecie.getSelectedItemPosition();
        final String especie = spinnerEspecie.getItemAtPosition(pos).toString();

        //fazendo a transformacao da imagem para string encode64, para se poder mandar a imagem para o webServer
        Bitmap bitmap = BitmapFactory.decodeFile(imageURI.getPath() );
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] array = stream.toByteArray();
        final String image = Base64.encodeToString(array, 0);

        //pegando o id da pessoa pelo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Pessoa", Context.MODE_PRIVATE);

        String id_pessoa_ = "";
        if(sharedPreferences.contains("id_pessoa")){

            id_pessoa_ = sharedPreferences.getString("id_pessoa","");

        }else{
            //se nao tem o sharedPreferences vai pra tela de login
            //mudando para a tela de meus animais cadastrados
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        final String id_pessoa = id_pessoa_;


        //fazendo o stringRequest para fazer o request ao WebServer
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_INSERE_ANIMAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //se nao deu erro
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("Animal Inserido com sucesso!"), Toast.LENGTH_LONG).show();

                                //mudando para a tela de meus animais cadastrados
                                Intent intent = new Intent(CadastroAnimal.this, MeusPets.class);
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
                params.put("id_pessoa", id_pessoa);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

}
