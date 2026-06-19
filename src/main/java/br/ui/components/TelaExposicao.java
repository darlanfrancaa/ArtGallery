package br.ui.components;

import br.IArtGallery;
import br.exception.ExposicaoNaoEncontradaException;
import br.exception.NotaInvalidaException;
import br.exception.ObraInativaException;
import br.model.Modelagem3D;
import br.model.Obra;
import br.model.PinturaDigital;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TelaExposicao extends JPanel {
    private final IArtGallery artGallery;
    private JTable tabelaObras;
    private DefaultTableModel tableModel;
    private JTextField nomeExposicao;


    private CardLayout cardLayout;
    private JPanel painelCartoes;
    private boolean mostrarTabela = false;

    public TelaExposicao(IArtGallery artGallery){
        this.artGallery = artGallery;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cardLayout = new CardLayout();
        painelCartoes = new JPanel(cardLayout);

        // Primeiramente, o card que faz a pergunta do autor
        JPanel cardBusca = new JPanel(new BorderLayout());
        JPanel perguntaInicial = new JPanel(new GridLayout(0,2,10,10));
        perguntaInicial.add(new JLabel("Nome Exposição"));
        nomeExposicao = new JTextField();
        perguntaInicial.add(nomeExposicao);

        JButton botaoTabela = new JButton("Buscar obras dessa exposição");
        botaoTabela.addActionListener(e -> renderizarTabela());

        JPanel painelTopoBusca = new JPanel(new BorderLayout(0, 10));
        painelTopoBusca.add(perguntaInicial, BorderLayout.NORTH);
        painelTopoBusca.add(botaoTabela, BorderLayout.SOUTH);
        cardBusca.add(painelTopoBusca, BorderLayout.NORTH);

        // Fim do card
        // Card da tabela
        JPanel cardTabela = new JPanel(new BorderLayout(0, 10));

        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        String[] colunas = {"Titulo", "Autor", "Tipo da Obra", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabelaObras = new JTable(tableModel);
        tabelaObras.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabelaObras);
        JButton botaoVoltar = new JButton("Fazer Nova Busca");
        botaoVoltar.addActionListener(e -> alternarEstado(false));
        cardTabela.add(scrollPane, BorderLayout.CENTER);
        cardTabela.add(botaoVoltar, BorderLayout.SOUTH);
        // Fim do card da tabela
        painelCartoes.add(cardBusca, "Busca");
        painelCartoes.add(cardTabela, "Tabela");
        add(painelCartoes, BorderLayout.CENTER);
    }

    private void renderizarTabela(){
        String nomeExp = nomeExposicao.getText().trim();
        if(nomeExp.isEmpty()){
            JOptionPane.showMessageDialog(this, "Digite o nome de uma exposição primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.setRowCount(0);
        try {
            Vector<Obra> obras = artGallery.obrasExpostas(nomeExp);

            if (obras != null && !obras.isEmpty()) {
                for (Obra obra : obras) {
                    Object[] linha = getObjects(obra);
                    tableModel.addRow(linha);
                }
                alternarEstado(true);
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma obra encontrada para a Exposicao: " + nomeExp, "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NotaInvalidaException e) {
            JOptionPane.showMessageDialog(this, "Alguma nota está inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ExposicaoNaoEncontradaException e){
            JOptionPane.showMessageDialog(this, "Essa exposição " + nomeExp + " não foi encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (ObraInativaException e){
            JOptionPane.showMessageDialog(this, "Alguma dessas obras está inativa.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao buscar essa exposição.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alternarEstado(boolean mostrar) {
        this.mostrarTabela = mostrar;

        if (mostrarTabela) {
            cardLayout.show(painelCartoes, "Tabela");
        } else {
            cardLayout.show(painelCartoes, "Busca");
            nomeExposicao.setText("");
        }
    }

    private static Object[] getObjects(Obra obra) {
        String classe = obra.getClass().getSimpleName();
        String status = obra.isAtiva() ? "Ativa" : "Inativa";

        Map<String, String> mapaClasses = new HashMap<>();
        mapaClasses.put("PinturaDigital", "Pintura Digital");
        mapaClasses.put("Modelagem3D", "Modelagem 3D");
        mapaClasses.put("ArteGenerativa", "Arte Generativa");
        String tipo = mapaClasses.get(classe);

        return new Object[]{obra.getTitulo(), obra.getAutor(), tipo, status};

    }
}
