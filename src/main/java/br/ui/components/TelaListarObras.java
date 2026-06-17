package br.ui.components;

import br.IArtGallery;
import br.exception.NotaInvalidaException;
import br.model.Modelagem3D;
import br.model.Obra;
import br.model.PinturaDigital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TelaListarObras extends JPanel {
    private final IArtGallery artGallery;
    private JTable tabelaObras;
    private DefaultTableModel tableModel;

    public TelaListarObras(IArtGallery artGallery){
        this.artGallery = artGallery;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        String[] colunas = {"Titulo", "Autor","Tipo da Obra"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna){
                return false;
            }
        };

        tabelaObras = new JTable(tableModel);
        tabelaObras.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabelaObras);
        add(scrollPane, BorderLayout.CENTER);

        renderizarTabela();

        JButton botaoAtualizar = new JButton("Atualizar Tabela");
        botaoAtualizar.addActionListener(e -> renderizarTabela());
        add(botaoAtualizar, BorderLayout.SOUTH);
    }

    private void renderizarTabela(){
        tableModel.setRowCount(0);
        try{
            Vector<Obra> obras = artGallery.listarObras();
            if(obras != null){
                for(Obra obra: obras){
                    Object[] linha = getObjects(obra);
                    tableModel.addRow(linha);
                }
            }
        } catch (NotaInvalidaException e) {
            JOptionPane.showMessageDialog(this, "Alguma nota está inválida.");
        }
    }

    private static Object[] getObjects(Obra obra) {
        String classe = obra.getClass().getSimpleName();

        Map<String, String> mapaClasses = new HashMap<>();
        mapaClasses.put("PinturaDigital", "Pintura Digital");
        mapaClasses.put("Modelagem3D", "Modelagem 3D");
        mapaClasses.put("ArteGenerativa", "Arte Generativa");
        String tipo = mapaClasses.get(classe);

        return new Object[]{obra.getTitulo(), obra.getAutor(), tipo};

    }
}
