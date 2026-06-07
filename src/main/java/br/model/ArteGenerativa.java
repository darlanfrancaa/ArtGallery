package br.model;

public class ArteGenerativa extends Obra{
    private String algoritmo;
    private long seed;

    public ArteGenerativa(String titulo, String autor, String algoritmo, long seed){
        super(titulo, autor);
        this.algoritmo = algoritmo;
        this.seed = seed;
    }

    @Override
    public String exibirDetalhes(){
        String detalhes = "";
        detalhes = detalhes.concat("Titulo: " + this.getTitulo() + "\n");
        detalhes = detalhes.concat("Autor: " + this.getAutor() + "\n");
        detalhes = detalhes.concat("Tipo: ArteGenerativa" + "\n");
        detalhes = detalhes.concat("Algoritmo: " + this.algoritmo + "\n");
        detalhes = detalhes.concat("Seed: " + this.seed + "\n");
        return detalhes;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
