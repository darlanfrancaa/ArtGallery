package br.model;

import java.util.Vector;

public class Exposicao {
    private int id;
    private String nome;
    private Vector<Obra> obras;

    public Exposicao(String nome){
        this.nome = nome;
        this.obras = new Vector<>();
    }

    public void adicionarObra(Obra obra){
        // Assumindo aqui que só é possível colocar uma obra ativa
        if(obra != null && obra.isAtiva()){
            this.obras.add(obra);
        }
    }

    public Vector<Obra> listarObras(){
        return this.obras;
    }

    public String getNome(){
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
