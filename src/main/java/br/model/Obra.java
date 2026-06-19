package br.model;

import br.repository.RepositorioObra;

import java.util.Vector;

public abstract class Obra {
    private int id;
    private String titulo;
    private String autor;
    private boolean ativa;
    private Vector<Avaliacao> avaliacoes;

    public Obra(String titulo, String autor){
        this.titulo = titulo;
        this.autor = autor;
        this.avaliacoes = new Vector<>();
        this.ativa = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(Vector<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        // Na criação do objeto avaliação só é permitida uma nota válida, logo, o avaliação existe então a nota é válida
        // e, portanto, a Avaliacao é válida, logo não é necessário tratar aqui
        if(avaliacao != null){
            this.avaliacoes.add(avaliacao);
        }
    }

    public double mediaAvaliacoes(){
        int somaNotas = 0;
        for(Avaliacao avaliacao : avaliacoes){
                somaNotas += avaliacao.getNota();
        }
        int qtdAvaliacoes = avaliacoes.size();
        double media;
        if(avaliacoes.isEmpty()) media = 0;
        else media = (double)somaNotas / qtdAvaliacoes;
        return media;
    }

    public abstract String exibirDetalhes();

}
