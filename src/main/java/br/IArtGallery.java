package br;

import br.exception.*;
import br.model.Avaliacao;
import br.model.Obra;

import java.util.Vector;
import java.sql.SQLException;
import br.model.Exposicao;

public interface IArtGallery {
    void publicarObra(Obra obra) throws ObraJaCadastradaException, NotaInvalidaException;
    void removerObra(String titulo) throws ObraInativaException, ObraNaoEncontradaException, NotaInvalidaException;
    void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException, ObraInativaException, NotaInvalidaException;
    Vector<Obra> buscarPorAutor(String autor) throws NotaInvalidaException;
    Vector<Obra> topObras() throws NotaInvalidaException;
    Vector<Obra> listarObras() throws NotaInvalidaException;
    Vector<Obra> obrasExpostas(String nomeExposicao) throws NotaInvalidaException, ExposicaoNaoEncontradaException, ObraInativaException;
    void cadastrarExposicao(Exposicao exposicao) throws SQLException;
    void adicionarObraExposicao(Exposicao exposicao, Obra obra) throws SQLException;
    Exposicao buscarExposicao(String nome) throws NotaInvalidaException, ExposicaoNaoEncontradaException, ObraInativaException;
    Obra buscarObra(String titulo) throws ObraNaoEncontradaException, NotaInvalidaException;
    boolean findByObraAndExposicao(Exposicao exposicao, Obra obra) throws NotaInvalidaException;
    Vector<Exposicao> topExposicoes() throws NotaInvalidaException;
}
