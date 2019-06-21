package com.example.cadastroanimais;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CatalogoAnimais extends AppCompatActivity {

	//Classe que fará a adaptação dos contatos para um ListView personalizado
    public class AnimaisAdapter extends BaseAdapter {

        private final ArrayList<Animal> animais;
        private final Activity activity;

        public AnimaisAdapter(ArrayList<animais> animais, Activity activity) {
            this.animais = animais;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return contatos.size();
        }

        @Override
        public Object getItem(int position) {
            return animais.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = activity.getLayoutInflater().inflate(R.layout.exemplo_animal, parent, false);

            final Animal animal = contatos.get(position);

            //Instanciando as Views
            final TextView txtNomeContato = (TextView) view.findViewById(R.id.txtNomeContato);
            TextView txtStatusContato = (TextView) view.findViewById(R.id.txtStatusContato);

            TextView especie  = view.findViewById(R.id.lbl_especie);
            TextView idade    = view.findViewById(Rid.lbl_idade);
            TextView raca     = view.findViewById(R.id.lbl_raca);
            TextView email    = view.findViewById(R.id.lbl_email);
            TextView telefone = view.findViewById(R.id.lbl_telefone);


            //colocando a imagem
            String image_url = URL_IMAGEM + animal.getImage();
            ImageView imgView = view.findViewById(R.id.img_animal);
            Picasso.with(this).load(image_url).into(imgView);

            //Definindo os valores para as Views
            txtNomeContato.setText(contato.getEmail());

            especie.setText(animal.getEspecie());
            idade.setText( Integer.toString( animal.getIdade() ) );
            raca.setText( animal.getRaca() );
            email.setText( animal.getDono().getEmail() );
            telefone.setText( animal.getDono().getTelefone() );

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	//Indo para a próxima tela
                	Intent intent = new Intent(CatalogoAnimais.this, VisualizarAnimal.class);

                	//colocando o shsaredPreferences para a proxima tela saber a qual animal estamos nos referindo
                	SharedPreferences preferencias = getSharedPreferences("Animal", Context.MODE_PRIVATE);
                	SharedPreferences.Editor editor = preferencias.edit();
                	editor.putString("id_animal", Integer.toString( animal.id_animal ) );
                	editor.apply();

                	startActivity(intent);
                }
            });

            return view;
        }
    }

     //Array que conterá os animais exibidos na lista
    private ArrayList<Animal> animais = new ArrayList<>();

    //Lista de animais
    private ListView listaAnimais;

    //ArrayAdapter para exibir os animais no ListView Personalizado
    private AnimaisAdapter animaisArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_animais);

        //instancia a lista
        listaAnimais = findViewById(R.id.lista_animais);

        carregaAnimais();

    }

     //Função responsável por carregar os animais na lista
    private void carregaAnimais() {

        //Instancia o ArrayAdapter de usuários para a lista de contatos
        animaisArrayAdapter = new AnimaisAdapter(animais, CatalogoAnimais.this);

        //Seta o ArrayAdapter para a lista de contatos
        listaContatos.setAdapter(animaisArrayAdapter);

        //pegando os animais para adocao noa banco de dados com string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_TODOS_ANIMAIS,
        	new Response.Listener<String>() {
        		@Override
        		public void onResponse(String response) {
        			try {
        				JSONObject obj = new JSONObject(response);
        				if(!obj.getBoolean("error")){

        					//pegando o numero de animais que conseguimos encontrar
        					int tamanho = obj.getNumber("num") ;

        					//pegando todos os animais e colocando em animais
        					for(int i = 0; i < tamanho; i++){
        						Animal aux = new Animal();

        						JSONObject jsonAux = obj.getJSONObject( Integer.toString(i) );
        						//colocando os valores do jsonAux no Animal aux
        						aux.setIdAnimal( jsonAux.getString("id_animal" ) );
        						aux.setNome( jsonAux.getString("nome" ) );
        						aux.setEspecie( jsonAux.getString("especie" ) );
        						aux.setDescricao( jsonAux.getString("descricao" ) );
        						aux.setSexo( jsonAux.getString("sexo" ) );
        						aux.setIdade( jsonAux.getNumber("idade") );
        						aux.setRaca( jsonAux.getString("raca" ) );
        						aux.setImageName( jsonAux.getString("image_name" ) );
        						aux.setCondicao( jsonAux.getString("condicao" ) );

        						//vendo se eh uma Pessoa ou uma Ong
        						int id_juridica, id_fisica_;
        						id_juridica_ = jsonAux.getNumber("id_juridica");
        						id_fisica_   = jsonAux.getNumber("id_fisica");

        						dono_aux.setIdJuridica(  id_juridica_ );
        						dono_aux.setIdFisica(  id_fisica_ );

        						Pessoa dono_aux;
        						//colocando os valores para pessoa ou para ong
        						if(id_fisica_ == -1){
        							dono_aux = new PessoaJuridica();
        							dono_aux.setCNPJ( jsonAux.getString("cnpj") );
        							dono_aux.setNomeResponsavel( jsonAux.getString("responsavel") );
        							
        						}else{
        							dono_aux = new PessoaFisica();
        							dono_aux.setCNPJ( jsonAux.getString("cpf"));
        							dono_aux.setIdFisica(  Integer.parseInt( id_fisica_ ) );
        							dono_aux.setIdJuridica(  -1 );
        							
        						}

        						//colocando os valores de pessoas gerais, dos tipos que ong e pessoa fisica compartilham
        						dono_aux.setIdPessoa(  jsonAux.getNumber("id_pessoa") );
        						dono_aux.setEmail(jsonAux.getString("email"));
        						dono_aux.setTelefone( jsonAux.getString("telefone") );
        						dono_aux.setEndereco( jsonAux.getString("endereco"));
        						dono_aux.setCidade( jsonAux.getString("cidade"));
        						dono_aux.setEstado( jsonAux.getString("uf"));
        						dono_aux.setNome( jsonAux.getString("nome"));

        						aux.setDono(dono_aux);

        						//adicionando Animal aux no vetor de animais
        						animais.add(aux);

        					}
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
        		params.put("email", email);
        		params.put("senha", senha);
        		return params;
        	}
        };

        //Notifica ao ArrayAdapter que o DataSet foi alterado
        animaissArrayAdapter.notifyDataSetChanged();

    }

}