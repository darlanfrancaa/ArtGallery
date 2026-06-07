package br;

import br.exception.ObraInativaException;
import br.exception.ObraJaCadastradaException;
import br.exception.ObraNaoEncontradaException;
import br.model.Avaliacao;
import br.model.Obra;

import java.util.Vector;

public interface IArtGallery {
    void publicarObra(Obra obra) throws ObraJaCadastradaException;
    void removerObra(String titulo) throws ObraInativaException, ObraNaoEncontradaException;
    void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException, ObraInativaException;
    public Vector<Obra> listarObras();
    public Vector<Obra> buscarPorAutor(String autor);
    public Vector<Obra> topObras();
    public Vector<Obra> ObrasExpostas(String nomeExposicao);
}
