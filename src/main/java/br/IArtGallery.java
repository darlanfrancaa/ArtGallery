package br;

import br.exception.*;
import br.model.Avaliacao;
import br.model.Obra;

import java.util.Vector;

public interface IArtGallery {
    void publicarObra(Obra obra) throws ObraJaCadastradaException, NotaInvalidaException;
    void removerObra(String titulo) throws ObraInativaException, ObraNaoEncontradaException, NotaInvalidaException;
    void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException, ObraInativaException, NotaInvalidaException;
    Vector<Obra> buscarPorAutor(String autor) throws NotaInvalidaException;
    Vector<Obra> topObras() throws NotaInvalidaException;
    Vector<Obra> listarObras() throws NotaInvalidaException;
    Vector<Obra> ObrasExpostas(String nomeExposicao) throws NotaInvalidaException, ExposicaoNaoEncontradaException;
}
