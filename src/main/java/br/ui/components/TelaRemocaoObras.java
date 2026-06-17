package br.ui.components;

import br.IArtGallery;
import br.exception.NotaInvalidaException;
import br.exception.ObraInativaException;
import br.exception.ObraJaCadastradaException;
import br.exception.ObraNaoEncontradaException;
import br.model.ArteGenerativa;
import br.model.Modelagem3D;
import br.model.Obra;
import br.model.PinturaDigital;

import javax.swing.*;
import java.awt.*;

public class TelaRemocaoObras extends JPanel {

    private JTextField titulo;
    private final IArtGallery artGallery;
    public TelaRemocaoObras(IArtGallery artGallery){
        this.artGallery = artGallery;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inicial = new JPanel(new GridLayout(0,2,10,10));
        titulo = new JTextField();
        inicial.add(new JLabel("Titulo:"));
        inicial.add(titulo);

        JButton botaorRemover = new JButton("Remover Obra");
        botaorRemover.addActionListener(e -> removerObra());

        JPanel total = new JPanel(new BorderLayout(0,10));
        total.add(inicial, BorderLayout.NORTH);
        total.add(botaorRemover, BorderLayout.SOUTH);

        add(total, BorderLayout.NORTH);
    }

    private void removerObra() {
        String tituloObra = titulo.getText().trim();
        Obra obra = null;
        try{
            artGallery.removerObra(tituloObra);
            JOptionPane.showMessageDialog(this, "A obra com título " + tituloObra + " foi desativada.");
        }
        catch (ObraInativaException e) {
            JOptionPane.showMessageDialog(this, "Essa obra já está inativa");
        }
        catch (ObraNaoEncontradaException e) {
            JOptionPane.showMessageDialog(this, "Obra não encontrada");
        }
        catch (NotaInvalidaException e){
            JOptionPane.showMessageDialog(this, "Alguma nota dessa obra é inválida");
        }
        catch (RuntimeException e) {
           JOptionPane.showMessageDialog(this, "Erro inesperado ao apagar a mensagem.");
        }

    }
}
