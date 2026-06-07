package br.model;

import br.exception.NotaInvalidaException;

public class Avaliacao {
    private String usuario;
    private int nota;
    private String comentario;

    public Avaliacao(String usuario, int nota, String comentario){
        this.usuario = usuario;
        this.comentario = comentario;
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException("A nota deve estar entre 0 e 10.");
        }
        this.nota = nota;
    }

    public void setNota(int nota){
        if(nota < 0 || nota > 10) {
            throw new NotaInvalidaException("Nota inválida (deve estar entre 0 e 10). Valor recebido: " + nota);
        }
        this.nota = nota;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
