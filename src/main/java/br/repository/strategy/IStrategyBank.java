package br.repository.strategy;

import br.model.Obra;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public interface IStrategyBank {
    String getTipo();
    void inserir(Connection connection, int obraId, Obra obra) throws SQLException;
    void atualizar(Connection connection, int obraId, Obra obra) throws SQLException;
    Obra buscar(Connection connection, int obraId, String titulo, String autor, boolean ativa) throws SQLException;
    Vector<Obra> listAll(Connection connection) throws SQLException;
}
