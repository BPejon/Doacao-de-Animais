package com.example.cadastroanimais;

/**
 * Esta classe representa uma pessoa generica, nao se deve ser instanciada, eh uma classe abstrata
 * @author ICMC
 *
 */
public abstract class Pessoa {

    protected int id_pessoa;

    //nome - para o caso da pessoa juridica seria o nome da empresa
    protected String nome;

    protected String telefone;
    protected String email;

    protected String endereco;
    protected String cidade;
    protected String estado;

    //chaves estrangeiras para o banco de dados
    protected int id_fisica;
    protected int id_juridica;

    //dados para logar
    protected String senha;

    //metodos gettters and setters

    /**
     * M�todo set do atributo id
     * @param id - o que sera setado no atributo
     */
    public void setId(int id){
        this.id_pessoa = id;
    }

    /**
     * M�todo get do atributo id
     * @return - id da pessoa
     */
    public int getId(){
        return this.id_pessoa;
    }

    /**
     * M�todo set do atributo nome
     * @param nome - o que sera setado no atributo
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * M�todo get do atributo nome
     * @return - nome da pessoa
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * M�todo set do atributo telefone
     * @param telefone - o que sera setado no atributo
     */
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    /**
     * M�todo get do atributo telefone
     * @return - telefone da pessoa
     */
    public String getTelefone(){
        return this.telefone;
    }


    /**
     * M�todo set do atributo email
     * @param email - o que sera setado no atributo
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * M�todo get do atributo email
     * @return - email da pessoa
     */
    public String getEmail(){
        return this.email;
    }


    /**
     * M�todo set do atributo endereco
     * @param endereco - o que sera setado no atributo
     */
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    /**
     * M�todo get do atributo endereco
     * @return - endereco da pessoa
     */
    public String getEndereco(){
        return this.endereco;
    }

    /**
     * M�todo set do atributo cidade
     * @param cidade - o que sera setado no atributo
     */
    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    /**
     * M�todo get do atributo cidade
     * @return - cidade da pessoa
     */
    public String getCidade(){
        return this.cidade;
    }

    /**
     * M�todo set do atributo estado
     * @param estado - o que sera setado no atributo
     */
    public void setEstado(String estado){
        this.estado = estado;
    }

    /**
     * M�todo get do atributo estado
     * @return - estado da pessoa
     */
    public String getEstado(){
        return this.estado;
    }

    /**
     * M�todo set do atributo senha
     * @param senha - o que sera setado no atributo
     */
    public void setSenha(String senha){
        this.senha = senha;
    }

    /**
     * M�todo get do atributo senha
     * @return - senha da pessoa
     */
    public String getSenha(){
        return senha;
    }

    //getters e setters para chave estrangeira
    /**
     * M�todo set do atributo id_fisica
     * @param id_fisica - o que sera setado no atributo
     */
    public void setIdFisica(int id_fisica){
        this.id_fisica = id_fisica;
    }

    /**
     * M�todo get do atributo id_fisica
     * @return - atributo id_fisica da pessoa
     */
    public int getIdFisica(){
        return this.id_fisica;
    }

    /**
     * M�todo set do atributo id_juridica
     * @param id_juridica - o que sera setado no atributo
     */
    public void setIdJuridica(int id_juridica){
        this.id_juridica = id_juridica;
    }

    /**
     * M�todo get do atributo id_juridica
     * @return - atributo id_juridica da pessoa
     */
    public int getIdJuridica(){
        return this.id_juridica;
    }
}
