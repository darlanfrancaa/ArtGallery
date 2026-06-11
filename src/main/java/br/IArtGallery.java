package br;

import br.exception.NotaInvalidaException;
import br.exception.ObraInativaException;
import br.exception.ObraJaCadastradaException;
import br.exception.ObraNaoEncontradaException;
import br.model.Avaliacao;
import br.model.Obra;

import java.util.Vector;

public interface IArtGallery {
    void publicarObra(Obra obra) throws ObraJaCadastradaException, NotaInvalidaException;
    void removerObra(String titulo) throws ObraInativaException, ObraNaoEncontradaException, NotaInvalidaException;
    void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException, ObraInativaException, NotaInvalidaException;
    public Vector<Obra> listarObras() throws NotaInvalidaException;
    public Vector<Obra> buscarPorAutor(String autor) throws NotaInvalidaException;
    public Vector<Obra> topObras() throws NotaInvalidaException;
    public Vector<Obra> ObrasExpostas(String nomeExposicao) throws NotaInvalidaException;
}
