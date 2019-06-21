package com.example.cadastroanimais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.cadastroanimais.Constantes.URL_IMAGEM;

/**
 * Classe que disponibiliza as informações do animal para serem visualizadas
 * Informa todos os dados e uma imagem do animal para o usuario
 */
public class VisualizarAnimal extends AppCompatActivity {

    TextView nomeAnimal, Especie, Raca, Sexo, Idade, NomeResp, Email, Telefone, Endereco, Cidade, Estado, Descricao ;
    ImageView ImagemAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_animal);

        //Text Views
        nomeAnimal = findViewById(R.id.lbl_vi_nome);
        Especie = findViewById(R.id.lbl_vi_especie);
        Raca = findViewById(R.id.lbl_vi_raca);
        Sexo = findViewById(R.id.lbl_vi_sexo);
        Idade = findViewById(R.id.lbl_vi_idade);
        NomeResp = findViewById(R.id.lbl_vi_resp);
        Email = findViewById(R.id.lbl_vi_email);
        Telefone = findViewById(R.id.lbl_vi_telefone);
        Endereco = findViewById(R.id.lbl_vi_endereco);
        Cidade = findViewById(R.id.lbl_vi_cidade);
        Estado = findViewById(R.id.lbl_vi_estado);
        Descricao = findViewById(R.id.textoBreveDescricao);

        //Image View
        ImagemAnimal = findViewById(R.id.img_vi_animal);

        VisualizarAnimal();
    }

    /**
     * Carrega os dados do animal do banco de dados para o aplicativo
     */
    private void VisualizarAnimal(){
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
                                nomeAnimal.setText(obj.getString("nome"));
                                Especie.setText(  obj.getString("especie"));
                                Raca.setText( obj.getString("raca"));
                                Sexo.setText(obj.getString("sexo"));
                                Idade.setText( Integer.toString(obj.getInt("idade")) + " Anos" );
                                NomeResp.setText(obj.getString("nome_dono"));
                                Email.setText( obj.getString("email") );
                                Descricao.setText(obj.getString("descricao"));

                                Telefone.setText( obj.getString("telefone") );
                                Endereco.setText( obj.getString("endereco"));
                                Cidade.setText(obj.getString("cidade"));
                                Estado.setText(obj.getString("uf"));

                                //colocando as imagens
                                final String image_url = URL_IMAGEM + obj.getString("image_name");
                                Picasso.with(VisualizarAnimal.this).load(image_url).into(ImagemAnimal);

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
