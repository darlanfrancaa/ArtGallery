import br.model.Avaliacao;
import br.model.Obra;

import java.util.Vector;

public interface IArtGallery {
    void publicarObra(Obra obra);
    void removerObra(String titulo);
    void avaliarObra(String titulo, Avaliacao avaliacao);
    public Vector<Obra> listarObras();
    public Vector<Obra> buscarPorAutor(String autor);
    public Vector<Obra> topObras();
    public Vector<Obra> ObrasExpostas(String nomeExposicao);
}
