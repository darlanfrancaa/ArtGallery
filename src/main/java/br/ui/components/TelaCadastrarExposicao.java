package br.ui.components;

import br.IArtGallery;
import br.exception.ExposicaoNaoEncontradaException;
import br.model.Exposicao;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaCadastrarExposicao extends JPanel {

    private final IArtGallery artGallery;
    private JTextField nome;

    public TelaCadastrarExposicao(IArtGallery artGallery) {
        this.artGallery = artGallery;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.add(new JLabel("Nome da Exposição:"));
        nome = new JTextField();
        painel.add(nome);

        JButton botaoCadastrar = new JButton("Cadastrar Exposição");
        botaoCadastrar.addActionListener(e -> cadastrarExposicao());

        JPanel total = new JPanel(new BorderLayout(0, 10));

        total.add(painel, BorderLayout.NORTH);
        total.add(botaoCadastrar, BorderLayout.SOUTH);
        add(total, BorderLayout.NORTH);
    }

    private void cadastrarExposicao() {
        String nome = this.nome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome da exposição.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // primeiro procura a exposição para não criar duas com o mesmo nome (restrição minha)
            artGallery.buscarExposicao(nome);
            JOptionPane.showMessageDialog(this, "Já existe uma exposição com o nome " + nome + ".", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (ExposicaoNaoEncontradaException e) {
            // se não tem, basta criar a exposição nova
            try {
                Exposicao exposicao = new Exposicao(nome);
                artGallery.cadastrarExposicao(exposicao);
                JOptionPane.showMessageDialog(this, "Exposição cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }  catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar exposição.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar a exposição.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
