package br.repository;

import br.model.Exposicao;
import br.model.Obra;

import java.sql.SQLException;

public interface IRepositorioExposicao {
    void cadastrar(Exposicao exposicao) throws  SQLException;
    void adicionar(Exposicao exposicao, Obra obra) throws SQLException;
}
