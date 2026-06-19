package br.service;

import br.exception.NotaInvalidaException;
import br.exception.ObraInativaException;
import br.exception.ObraNaoEncontradaException;
import br.model.Avaliacao;

public interface AvaliacaoService {
    void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraInativaException, ObraNaoEncontradaException, NotaInvalidaException;
}
