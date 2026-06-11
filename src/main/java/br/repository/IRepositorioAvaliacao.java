package br.repository;

import br.exception.NotaInvalidaException;
import br.model.Avaliacao;

import java.sql.SQLException;
import java.util.Vector;

public interface IRepositorioAvaliacao {
    void adicionar(int obraId, Avaliacao avaliacao) ;
    Vector<Avaliacao> buscarPorObra(int obraId) throws NotaInvalidaException;
}
