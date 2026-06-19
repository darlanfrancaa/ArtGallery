package br.ui.components;

import br.IArtGallery;
import br.exception.ExposicaoNaoEncontradaException;
import br.exception.ObraNaoEncontradaException;
import br.model.Exposicao;
import br.model.Obra;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaAdicionarNaExposicao extends JPanel {

    private final IArtGallery artGallery;
    private JTextField nomeObra;
    private JTextField nomeExposicao;

    public TelaAdicionarNaExposicao(IArtGallery artGallery) {
        this.artGallery = artGallery;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.add(new JLabel("Nome da Obra:"));
        nomeObra = new JTextField();
        painel.add(nomeObra);

        painel.add(new JLabel("Nome da Exposição:"));
        nomeExposicao = new JTextField();
        painel.add(nomeExposicao);

        JButton botaoAdicionar = new JButton("Adicionar obra à Exposição");
        botaoAdicionar.addActionListener(e -> adicionarObra());

        JPanel total = new JPanel(new BorderLayout(0, 10));

        total.add(painel, BorderLayout.NORTH);
        total.add(botaoAdicionar, BorderLayout.SOUTH);
        add(total, BorderLayout.CENTER);
    }

    private void adicionarObra() {
        String tituloObra = nomeObra.getText().trim();
        String tituloExposicao = nomeExposicao.getText().trim();

        if (tituloObra.isEmpty() || tituloExposicao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome da obra e da exposição.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Obra obra = artGallery.buscarObra(tituloObra);
            Exposicao exposicao = artGallery.buscarExposicao(tituloExposicao);

            boolean obraJaCadastrada = artGallery.findByObraAndExposicao(exposicao, obra);

            if (obraJaCadastrada) {
                JOptionPane.showMessageDialog(this, "Esta obra já está cadastrada nesta exposição.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                artGallery.adicionarObraExposicao(exposicao, obra);
                JOptionPane.showMessageDialog(this, "Obra adicionada à exposição com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ObraNaoEncontradaException e) {
            JOptionPane.showMessageDialog(this, "A obra com titulo " + tituloObra + " não foi encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (ExposicaoNaoEncontradaException e) {
            JOptionPane.showMessageDialog(this, "A exposição com titulo " + tituloExposicao + " não foi encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao adicionar obra na exposição.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
