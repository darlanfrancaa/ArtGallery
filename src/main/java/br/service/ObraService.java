package br.service;

import br.exception.ObraInativaException;
import br.exception.ObraJaCadastradaException;
import br.exception.ObraNaoEncontradaException;
import br.model.Avaliacao;
import br.model.Exposicao;
import br.model.Obra;
import br.repository.RepositorioExposicao;
import br.repository.RepositorioObra;

import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.Vector;

public class ObraService {

    private final RepositorioObra obraRepository;
    private final RepositorioExposicao exposicaoRepository;

    public ObraService(RepositorioObra obraRepository, RepositorioExposicao exposicaoRepository) {
        this.obraRepository = obraRepository;
        this.exposicaoRepository = exposicaoRepository;
    }

    public void publicarObra(Obra obra){
        String titulo  = obra.getTitulo();
        Obra obraBanco = obraRepository.buscar(titulo);
        if(obraBanco == null){
            obraRepository.cadastrar(obra);
        } else {
            throw new ObraJaCadastradaException("Obra com id: " + obraBanco.getId() + " já cadastrada.");
        }
    }

    public void removerObra(Obra obra){
        Obra obraBanco = obraRepository.buscar(obra.getTitulo());
        if(obraBanco == null) {
            throw new ObraNaoEncontradaException("Obra com título: " +  obra.getTitulo() + " não encontrada.");
        } else if(!obraBanco.isAtiva()) {
            throw new ObraInativaException("A obra com título " + obra.getTitulo() + " está inativa.");
        } else {
            obraRepository.remover(obra.getTitulo());
        }
    }

    public Vector<Obra> listarObras(){
        Vector<Obra> obrasCadastradas = obraRepository.listar();
        Vector<Obra> obrasAtivas = new Vector<>();
        for(Obra obra: obrasCadastradas){
            if(obra.isAtiva()) obrasAtivas.add(obra);
        }
        return obrasAtivas;
    }

    public Vector<Obra> buscaPorAutor(String autor){
        return obraRepository.findByAutor(autor);
    }

    public Vector<Obra> topObras() {
        Vector<Obra> obras = obraRepository.listar();
        obras.sort(Comparator.comparingDouble(Obra::mediaAvaliacoes).reversed());
        return obras;
    }

    public Vector<Obra> obrasExpostas(String nomeExposicao){
        Exposicao exposicao = exposicaoRepository.getExpByNome(nomeExposicao);
        return exposicaoRepository.getObras(exposicao);
    }

}
