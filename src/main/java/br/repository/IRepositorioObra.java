package br.repository;

import br.exception.ObraJaCadastradaException;
import br.exception.ObraNaoEncontradaException;
import br.model.Obra;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public interface IRepositorioObra {
    void cadastrar(Obra obra) throws ObraJaCadastradaException;
    Obra buscar(String titulo);
    boolean atualizar(Obra obra) throws ObraNaoEncontradaException;
    void remover(String titulo);
    Vector<Obra> listar();
    Vector<Obra> findByAutor(String autor);
    Obra buscarObraPorTipo(Connection conn, int id, String titulo, String autor, boolean ativa, String tipo) throws SQLException;
}
