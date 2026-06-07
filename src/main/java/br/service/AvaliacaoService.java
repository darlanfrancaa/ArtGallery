package br.service;

import br.exception.ObraInativaException;
import br.exception.ObraNaoEncontradaException;
import br.model.Avaliacao;
import br.model.Obra;
import br.repository.IRepositorioAvaliacao;
import br.repository.IRepositorioObra;


public class AvaliacaoService {

    private final IRepositorioObra obraRepository;
    private final IRepositorioAvaliacao avaliacaoRepository;

    public AvaliacaoService(IRepositorioObra obraRepository, IRepositorioAvaliacao avaliacaoRepository) {
        this.obraRepository = obraRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraInativaException, ObraNaoEncontradaException {
        // primeiro eu tenho que checar se a obra existe e está ativa e depois eu preciso salvar a avaliacao no banco e
        // colocar a avalição no Vector da obra
        Obra obraBanco = obraRepository.buscar(titulo);
        if(obraBanco == null) {
            throw new ObraNaoEncontradaException("Obra com título: " +  titulo  + " não encontrada.");
        } else if(!obraBanco.isAtiva()) {
            throw new ObraInativaException("A obra com id:  " + obraBanco.getId() + " está inativa.");
        } else {
            // Em tese um objeto Avaliacao só é criado com uma nota válida, então podemos assumir aqui que essa notá está válida
            avaliacaoRepository.adicionar(obraBanco.getId(), avaliacao);
            obraBanco.adicionarAvaliacao(avaliacao);
        }
    }
}
