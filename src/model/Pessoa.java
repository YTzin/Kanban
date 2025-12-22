package model;

public class Pessoa {
    private String nome;
    private String cargo;
    private int id;

    public Pessoa(String nome, String cargo, int id){
        this.nome = nome;
        this.cargo = cargo;
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public String getCargo(){
        return cargo;
    }

    public int getId(){
        return id;
    }
}