    private void removeAnimal(){
        //fazendo a validacao dos dados nos campos

        //pegando o id do animal pelo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Animal", Context.MODE_PRIVATE);

        final String id_animal;
        if(sharedPreferences.contains("id_animal")){

            id_animal = sharedPreferences.getString("id_animal","");

        }else{
            //se nao tem o sharedPreferences vai pra tela de login
            Toast.makeText(getApplicationContext(), obj.getString("Erro ao remover animal."), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MeusPets.class);
            startActivity(intent);
        }

        //fazendo o stringRequest para fazer o request ao WebServer
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //se nao deu erro
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("Animal Excluido com sucesso!"), Toast.LENGTH_LONG).show();

                                //mudando para a tela de meus animais cadastrados
                                Intent intent = new Intent(this, MeusPets.class);
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