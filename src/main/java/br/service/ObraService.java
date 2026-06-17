package br.service;

import br.exception.*;
import br.model.Avaliacao;
import br.model.Exposicao;
import br.model.Obra;
import br.repository.IRepositorioExposicao;
import br.repository.IRepositorioObra;
import java.util.Comparator;
import java.util.Vector;

public class ObraService {

    private final IRepositorioObra obraRepository;
    private final IRepositorioExposicao exposicaoRepository;

    public ObraService(IRepositorioObra obraRepository, IRepositorioExposicao exposicaoRepository) {
        this.obraRepository = obraRepository;
        this.exposicaoRepository = exposicaoRepository;
    }

    public void publicarObra(Obra obra) throws ObraJaCadastradaException, NotaInvalidaException {
        String titulo  = obra.getTitulo();
        Obra obraBanco = obraRepository.buscar(titulo);
        if(obraBanco == null){
            obraRepository.cadastrar(obra);
        } else {
            throw new ObraJaCadastradaException("Obra com id: " + obraBanco.getId() + " já cadastrada.");
        }
    }

    public void removerObra(String titulo) throws ObraNaoEncontradaException, ObraInativaException , NotaInvalidaException{
        Obra obraBanco = obraRepository.buscar(titulo);
        if(obraBanco == null) {
            throw new ObraNaoEncontradaException("Obra com título: " +  titulo + " não encontrada.");
        } else if(!obraBanco.isAtiva()) {
            throw new ObraInativaException("A obra com título " + titulo + " está inativa.");
        } else {
            obraRepository.remover(titulo);
        }
    }

    public Vector<Obra> listarObras() throws NotaInvalidaException{
        Vector<Obra> obrasCadastradas = obraRepository.listar();
        Vector<Obra> obrasAtivas = new Vector<>();
        for(Obra obra: obrasCadastradas){
            if(obra.isAtiva()) obrasAtivas.add(obra);
        }
        return obrasAtivas;
    }

    public Vector<Obra> buscaPorAutor(String autor) throws NotaInvalidaException{
        return obraRepository.findByAutor(autor);
    }

    public Vector<Obra> topObras() throws NotaInvalidaException{
        // Filtrando as obras apenas pelas obras ativas
        Vector<Obra> obras = listarObras();
        obras.sort(Comparator.comparingDouble(Obra::mediaAvaliacoes).reversed());
        return obras;
    }

    public Vector<Obra> obrasExpostas(String nomeExposicao) throws NotaInvalidaException, ExposicaoNaoEncontradaException, ObraInativaException {
        Exposicao exposicao = exposicaoRepository.getExpByNome(nomeExposicao);
        return exposicao.listarObras();
    }

}
