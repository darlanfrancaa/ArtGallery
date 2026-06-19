package br;

import br.IArtGallery;
import br.exception.*;
import br.model.Avaliacao;
import br.model.Exposicao;
import br.model.Obra;
import br.repository.IRepositorioObra;
import br.service.AvaliacaoService;
import br.service.ExposicaoService;
import br.service.ObraService;

import java.sql.SQLException;
import java.util.Vector;

public class ArtGallery implements IArtGallery {

    private final AvaliacaoService avaliacaoService;
    private final ObraService obraService;
    private final ExposicaoService exposicaoService;
    private final IRepositorioObra repositorio;

    public ArtGallery(AvaliacaoService avaliacaoService, ObraService obraService, ExposicaoService exposicaoService, IRepositorioObra repositorio) {
        this.avaliacaoService = avaliacaoService;
        this.obraService = obraService;
        this.exposicaoService = exposicaoService;
        this.repositorio = repositorio;
    }

    @Override
    public void publicarObra(Obra obra) throws ObraJaCadastradaException, NotaInvalidaException {
        obraService.publicarObra(obra);
    }

    @Override
    public void removerObra(String titulo) throws ObraNaoEncontradaException, ObraInativaException, NotaInvalidaException {
        obraService.removerObra(titulo);
    }

    @Override
    public void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException, ObraInativaException, NotaInvalidaException {
        avaliacaoService.avaliarObra(titulo, avaliacao);
    }

    @Override
    public Vector<Obra> listarObras() throws NotaInvalidaException {
        return obraService.listarObras();
    }

    @Override
    public Vector<Obra> buscarPorAutor(String autor) throws NotaInvalidaException {
        return obraService.buscaPorAutor(autor);
    }

    @Override
    public Vector<Obra> topObras() throws NotaInvalidaException {
        return obraService.topObras();
    }

    @Override
    public Vector<Obra> obrasExpostas(String nomeExposicao) throws NotaInvalidaException, ExposicaoNaoEncontradaException, ObraInativaException {
        return obraService.obrasExpostas(nomeExposicao);
    }

    @Override
    public void cadastrarExposicao(Exposicao exposicao) throws SQLException {
        exposicaoService.cadastrar(exposicao);
    }

    @Override
    public void adicionarObraExposicao(Exposicao exposicao, Obra obra) throws SQLException {
        exposicaoService.adicionar(exposicao, obra);
    }

    @Override
    public Exposicao buscarExposicao(String nome) throws NotaInvalidaException, ExposicaoNaoEncontradaException, ObraInativaException {
        return exposicaoService.getExpByNome(nome);
    }

    @Override
    public Obra buscarObra(String titulo) throws ObraNaoEncontradaException, NotaInvalidaException {
        return obraService.buscarObra(titulo);
    }

    @Override
    public boolean findByObraAndExposicao(Exposicao exposicao, Obra obra) throws NotaInvalidaException {
        return exposicaoService.findByObraAndExposicao(exposicao,obra);
    }

    @Override
    public Vector<Exposicao> topExposicoes() throws NotaInvalidaException {
        return exposicaoService.topExposicoes();
    }
}
