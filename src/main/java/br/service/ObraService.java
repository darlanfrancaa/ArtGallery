package br.service;

import br.exception.*;
import br.model.Obra;
import java.util.Vector;

public interface ObraService {
    void publicarObra(Obra obra) throws ObraJaCadastradaException, NotaInvalidaException;
    void removerObra(String titulo) throws ObraNaoEncontradaException, ObraInativaException , NotaInvalidaException;
    Vector<Obra> listarObras() throws NotaInvalidaException;
    Vector<Obra> buscaPorAutor(String autor) throws NotaInvalidaException;
    Vector<Obra> topObras() throws NotaInvalidaException;
    Vector<Obra> obrasExpostas(String nomeExposicao) throws NotaInvalidaException, ExposicaoNaoEncontradaException, ObraInativaException;
    Obra buscarObra(String titulo) throws ObraNaoEncontradaException, NotaInvalidaException;
}
