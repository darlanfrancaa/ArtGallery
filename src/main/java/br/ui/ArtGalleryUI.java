package br.ui;
import br.ArtGallery;
import br.IArtGallery;
import br.repository.IRepositorioObra;
import br.repository.RepositorioAvaliacao;
import br.repository.RepositorioExposicao;
import br.repository.RepositorioObra;
import br.service.AvaliacaoService;
import br.service.ObraService;

public class ArtGalleryUI {
    public static void main(String[] args){
        RepositorioAvaliacao repositorioAvaliacao = new RepositorioAvaliacao();
        IRepositorioObra repositorioObra = new RepositorioObra(repositorioAvaliacao);
        RepositorioExposicao repositorioExposicao = new RepositorioExposicao(repositorioObra);
        ObraService obraService = new ObraService(repositorioObra , repositorioExposicao);
        AvaliacaoService avaliacaoService = new AvaliacaoService(repositorioObra, repositorioAvaliacao);
        IArtGallery artGallery = new ArtGallery(avaliacaoService, obraService, repositorioObra);

        TelaInicial telaInicial = new TelaInicial("ArtGallery", artGallery);
    }
}
