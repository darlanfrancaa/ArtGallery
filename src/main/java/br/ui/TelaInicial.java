package br.ui;
import br.ArtGallery;
import br.IArtGallery;
import br.repository.IRepositorioObra;
import br.repository.RepositorioAvaliacao;
import br.repository.RepositorioExposicao;
import br.repository.RepositorioObra;
import br.service.AvaliacaoService;
import br.service.ObraService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial {
    public static void main(String[] args){
        IRepositorioObra repositorioObra = new RepositorioObra();
        RepositorioExposicao repositorioExposicao = new RepositorioExposicao(repositorioObra);
        RepositorioAvaliacao repositorioAvaliacao = new RepositorioAvaliacao();
        ObraService obraService = new ObraService(repositorioObra , repositorioExposicao);
        AvaliacaoService avaliacaoService = new AvaliacaoService(repositorioObra, repositorioAvaliacao);
        IArtGallery artGallery = new ArtGallery(avaliacaoService, obraService, repositorioObra);

        MinhaJanela frame = new MinhaJanela("ArtGallery", artGallery);
    }
}
