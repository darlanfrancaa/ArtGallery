package br.ui;
import br.ArtGallery;
import br.IArtGallery;
import br.repository.IRepositorioObra;
import br.repository.RepositorioAvaliacao;
import br.repository.RepositorioExposicao;
import br.repository.RepositorioObra;
import br.service.AvaliacaoService;
import br.service.AvaliacaoServiceImpl;
import br.service.ExposicaoService;
import br.service.ExposicaoServiceImpl;
import br.service.ObraService;
import br.service.ObraServiceImpl;
public class ArtGalleryUI {
    public static void main(String[] args){
        RepositorioAvaliacao repositorioAvaliacao = new RepositorioAvaliacao();
        IRepositorioObra repositorioObra = new RepositorioObra(repositorioAvaliacao);
        RepositorioExposicao repositorioExposicao = new RepositorioExposicao(repositorioObra);
        ExposicaoService exposicaoService = new ExposicaoServiceImpl(repositorioExposicao);
        ObraService obraService = new ObraServiceImpl(repositorioObra , exposicaoService);
        AvaliacaoService avaliacaoService = new AvaliacaoServiceImpl(repositorioObra, repositorioAvaliacao);
        IArtGallery artGallery = new ArtGallery(avaliacaoService, obraService, exposicaoService, repositorioObra);

        TelaInicial telaInicial = new TelaInicial("ArtGallery", artGallery);
    }
}
