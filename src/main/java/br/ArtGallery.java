package br;

import br.IArtGallery;
import br.exception.ObraJaCadastradaException;
import br.model.Avaliacao;
import br.model.Obra;
import br.repository.IRepositorioObra;
import br.service.AvaliacaoService;
import br.service.ObraService;

import java.util.Vector;

public class ArtGallery implements IArtGallery {

    private final AvaliacaoService avaliacaoService;
    private final ObraService obraService;
    private final IRepositorioObra repositorio;

    public ArtGallery(AvaliacaoService avaliacaoService, ObraService obraService, IRepositorioObra repositorio) {
        this.avaliacaoService = avaliacaoService;
        this.obraService = obraService;
        this.repositorio = repositorio;
    }

    @Override
    public void publicarObra(Obra obra) throws ObraJaCadastradaException {
        obraService.publicarObra(obra);
    }

    @Override
    public void removerObra(String titulo) {
        obraService.removerObra(titulo);
    }

    @Override
    public void avaliarObra(String titulo, Avaliacao avaliacao) {
        avaliacaoService.avaliarObra(titulo, avaliacao);
    }

    @Override
    public Vector<Obra> listarObras() {
        return obraService.listarObras();
    }

    @Override
    public Vector<Obra> buscarPorAutor(String autor) {
        return obraService.buscaPorAutor(autor);
    }

    @Override
    public Vector<Obra> topObras() {
        return obraService.topObras();
    }

    @Override
    public Vector<Obra> ObrasExpostas(String nomeExposicao) {
        return obraService.obrasExpostas(nomeExposicao);
    }
}
