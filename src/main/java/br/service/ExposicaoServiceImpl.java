package br.service;

import br.exception.ExposicaoNaoEncontradaException;
import br.exception.NotaInvalidaException;
import br.exception.ObraInativaException;
import br.model.Exposicao;
import br.model.Obra;
import br.repository.IRepositorioExposicao;

import java.sql.SQLException;
import java.util.Vector;

public class ExposicaoServiceImpl implements ExposicaoService {
    private final IRepositorioExposicao exposicaoRepository;

    public ExposicaoServiceImpl(IRepositorioExposicao exposicaoRepository) {
        this.exposicaoRepository = exposicaoRepository;
    }

    @Override
    public void cadastrar(Exposicao exposicao) throws SQLException {
        exposicaoRepository.cadastrar(exposicao);
    }

    @Override
    public void adicionar(Exposicao exposicao, Obra obra) throws SQLException {
        exposicaoRepository.adicionar(exposicao, obra);
    }

    @Override
    public Exposicao getExpByNome(String nomeExposicao) throws NotaInvalidaException, ExposicaoNaoEncontradaException, ObraInativaException {
        return exposicaoRepository.getExpByNome(nomeExposicao);
    }

    @Override
    public Vector<Obra> getObras(Exposicao exposicao) throws NotaInvalidaException {
        return exposicaoRepository.getObras(exposicao);
    }

    @Override
    public boolean findByObraAndExposicao(Exposicao exposicao, Obra obra) throws NotaInvalidaException {
        Vector<Obra> obras = getObras(exposicao);
        for(Obra obraExp: obras){
            if(obraExp.getTitulo().equals(obra.getTitulo())) return true;
        }
        return false;
    }

    @Override
    public Vector<Exposicao> topExposicoes() throws NotaInvalidaException {
        Vector<Exposicao> exposicoes = exposicaoRepository.listar();
        exposicoes.sort((e1, e2) -> Double.compare(e2.media(), e1.media()));
        return exposicoes;
    }
}
