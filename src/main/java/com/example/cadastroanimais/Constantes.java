package com.example.cadastroanimais;

public class Constantes {
    private static final String ROOT_URL = "http://192.168.43.59/adocao/";
    public static final String ROOT_V= ROOT_URL+"v1/";
    public static final String URL_IMAGEM = ROOT_URL+"images/";

    public static final String URL_LOGIN = ROOT_V + "login.php";
    public static final String URL_ONG = ROOT_V + "inserirJuridica.php";
    public static final String URL_PESSOA = ROOT_V + "inserirFisica.php";
    public static final String URL_INSERE_ANIMAL = ROOT_V + "inserirAnimal.php";
    public static final String URL_EDITA_ANIMAL = ROOT_V + "editarAnimal.php";
    public static final String URL_REMOVE_ANIMAL = ROOT_V + "removerAnimal.php";
    public static final String URL_TODOS_ANIMAIS = ROOT_V + "listarAnimais.php";
    public static final String URL_MEUS_ANIMAIS = ROOT_V + "listarAnimaisdeUmaPessoa.php";
    public static final String URL_BUSCAR_ANIMAL = ROOT_V + "buscarAnimal.php";

}
