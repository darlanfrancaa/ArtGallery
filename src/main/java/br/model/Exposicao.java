package br.model;

import br.exception.ObraInativaException;

import java.util.Vector;

public class Exposicao {
    private int id;
    private String nome;
    private Vector<Obra> obras;

    public Exposicao(String nome){
        this.nome = nome;
        this.obras = new Vector<>();
    }

    public void adicionarObra(Obra obra) throws ObraInativaException {
        if (obra == null) {
            throw new IllegalArgumentException("A obra não pode ser nula.");
        }
        if (!obra.isAtiva()) {
            throw new ObraInativaException("Não é possível adicionar a obra '" + obra.getTitulo() + "' pois ela está inativa.");
        }
        this.obras.add(obra);
    }

    // Esse metódo é necessário por que se excluirmos uma obra não poderiamos mais pegar o Vector dela se fosse usado apenas
    // o adicionarObra, visto que ele só adiciona se a obra estiver ativa
    // sendo que uma exposição pode ter obras inativas (acredito), caso ela tenha acontecido no passado

    public void adicionarObraVector(Obra obra) {
        if(obra != null) {
            this.obras.add(obra);
        }
    }

    public Vector<Obra> listarObras(){
        return this.obras;
    }

    public double media() {
        if (obras == null || obras.isEmpty()) {
            return 0.0;
        }
        double soma = 0.0;
        for (Obra obra : obras) {
            soma += obra.mediaAvaliacoes();
        }
        return soma / obras.size();
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

    public void setObras(Vector<Obra> obras){
        this.obras = obras;
    }




}
