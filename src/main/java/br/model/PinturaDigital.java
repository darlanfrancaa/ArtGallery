package br.model;

import java.util.Vector;

public class PinturaDigital extends Obra {
    private String resolucao;
    private String softwareUtilizado;

    public PinturaDigital(String titulo, String autor, String resolucao, String softwareUtilizado){
        super(titulo, autor);
        this.resolucao = resolucao;
        this.softwareUtilizado = softwareUtilizado;
    }

    @Override
    public String exibirDetalhes(){
        String detalhes = "";
        detalhes = detalhes.concat("Titulo: " + this.getTitulo() + "\n");
        detalhes = detalhes.concat("Autor: " + this.getAutor() + "\n");
        detalhes = detalhes.concat("Tipo: Pintura Digital" + "\n");
        detalhes = detalhes.concat("Resolução: " + this.resolucao + "\n");
        detalhes = detalhes.concat("Software: " + this.softwareUtilizado + "\n");
        return detalhes;
    }

    public String getResolucao() {
        return resolucao;
    }

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }

    public String getSoftwareUtilizado() {
        return softwareUtilizado;
    }

    public void setSoftwareUtilizado(String softwareUtilizado) {
        this.softwareUtilizado = softwareUtilizado;
    }
}
