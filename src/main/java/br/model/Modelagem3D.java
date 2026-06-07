package br.model;

public class Modelagem3D extends Obra{
    private int numeroPoligonos;
    private String engine;

    public Modelagem3D(String titulo, String autor, int numeroPoligonos, String engine){
        super(titulo, autor);
        this.numeroPoligonos = numeroPoligonos;
        this.engine = engine;
    }

    @Override
    public String exibirDetalhes(){
        String detalhes = "";
        detalhes = detalhes.concat("Titulo: " + this.getTitulo() + "\n");
        detalhes = detalhes.concat("Autor: " + this.getAutor() + "\n");
        detalhes = detalhes.concat("Tipo: Modelagem3D" + "\n");
        detalhes = detalhes.concat("Polígonos: " + this.numeroPoligonos + "\n");
        detalhes = detalhes.concat("Engine: " + this.engine + "\n");
        return detalhes;
    }

    public int getNumeroPoligonos() {
        return numeroPoligonos;
    }

    public void setNumeroPoligonos(int numeroPoligonos) {
        this.numeroPoligonos = numeroPoligonos;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
