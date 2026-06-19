package br.service;

import br.exception.ExposicaoNaoEncontradaException;
import br.exception.NotaInvalidaException;
import br.exception.ObraInativaException;
import br.model.Exposicao;
import br.model.Obra;

import java.sql.SQLException;
import java.util.Vector;

public interface ExposicaoService {
    void cadastrar(Exposicao exposicao) throws SQLException;
    void adicionar(Exposicao exposicao, Obra obra) throws SQLException;
    Exposicao getExpByNome(String nomeExposicao) throws NotaInvalidaException, ExposicaoNaoEncontradaException, ObraInativaException;
    Vector<Obra> getObras(Exposicao exposicao) throws NotaInvalidaException;
    boolean findByObraAndExposicao(Exposicao exposicao, Obra obra) throws NotaInvalidaException;
    Vector<Exposicao> topExposicoes() throws NotaInvalidaException;
}
