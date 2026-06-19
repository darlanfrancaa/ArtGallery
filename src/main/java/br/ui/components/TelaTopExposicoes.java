package br.ui.components;

import br.IArtGallery;
import br.exception.NotaInvalidaException;
import br.model.Exposicao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Vector;

public class TelaTopExposicoes extends JPanel {
    private final IArtGallery artGallery;
    private JTable tabelaExposicoes;
    private DefaultTableModel tableModel;

    public TelaTopExposicoes(IArtGallery artGallery){
        this.artGallery = artGallery;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        String[] colunas = {"NomeExp", "Qtd Obras", "Media das Obras"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna){
                return false;
            }
        };

        tabelaExposicoes = new JTable(tableModel);
        tabelaExposicoes.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabelaExposicoes);
        add(scrollPane, BorderLayout.CENTER);

        renderizarTabela();

        JButton botaoAtualizar = new JButton("Atualizar Tabela");
        botaoAtualizar.addActionListener(e -> renderizarTabela());
        add(botaoAtualizar, BorderLayout.SOUTH);
    }

    private void renderizarTabela(){
        tableModel.setRowCount(0);
        try {
            Vector<Exposicao> exposicoes = artGallery.topExposicoes();
            if(exposicoes != null){
                for(Exposicao exposicao: exposicoes){
                    Object[] linha = getObjects(exposicao);
                    tableModel.addRow(linha);
                }
            }
        } catch (NotaInvalidaException e) {
            JOptionPane.showMessageDialog(this, "Existe alguma obra com uma nota inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar notas de obras nas exposições.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Object[] getObjects(Exposicao exposicao) {
        int qtdObras = exposicao.listarObras() != null ? exposicao.listarObras().size() : 0;
        double media = exposicao.media();
        DecimalFormat df = new DecimalFormat("0.00");

        return new Object[]{exposicao.getNome(), qtdObras, df.format(media)};
    }
}
