package br.ui;

import br.IArtGallery;
import br.ui.components.*;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {

    private final IArtGallery artGallery;

    private JPanel painelCentral;
    private CardLayout cardLayout;

    TelaInicial(String titulo, IArtGallery artGallery){

        super(titulo);
        this.artGallery = artGallery;

        setLayout(new BorderLayout());

        JPanel menuLateral = buildMenuLateral();
        add(menuLateral, BorderLayout.WEST);

        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);

        TelaCadastroObras cadastroObras = new TelaCadastroObras(artGallery);
        TelaRemocaoObras remocaoObras = new TelaRemocaoObras(artGallery);
        TelaAvaliacaoObras avaliacaoObras = new TelaAvaliacaoObras(artGallery);
        TelaListarObras listarObras = new TelaListarObras(artGallery);
        TelaBuscarPorAutor buscarPorAutor = new TelaBuscarPorAutor(artGallery);
        TelaTopObras telaTopObras = new TelaTopObras(artGallery);
        TelaCadastrarExposicao telaCadastrarExposicao = new TelaCadastrarExposicao(artGallery);
        TelaAdicionarNaExposicao telaAdicionarNaExposicao = new TelaAdicionarNaExposicao(artGallery);
        TelaExposicao telaExposicao = new TelaExposicao(artGallery);
        TelaTopExposicoes telaTopExposicoes = new TelaTopExposicoes(artGallery);

        JLabel teste = new JLabel("Teste", SwingConstants.CENTER);

        painelCentral.add(cadastroObras, "tela_1");
        painelCentral.add(remocaoObras, "tela_2");
        painelCentral.add(avaliacaoObras, "tela_3");
        painelCentral.add(listarObras, "tela_4");
        painelCentral.add(buscarPorAutor, "tela_5");
        painelCentral.add(telaTopObras, "tela_6");
        painelCentral.add(telaCadastrarExposicao, "tela_7");
        painelCentral.add(telaAdicionarNaExposicao, "tela_8");
        painelCentral.add(telaExposicao, "tela_9");
        painelCentral.add(telaTopExposicoes, "tela_10");

        add(painelCentral, BorderLayout.CENTER);

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildMenuLateral(){
        JPanel menu = new JPanel(new GridLayout(10,1,5,5));
        menu.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JButton botao1 = new JButton("Cadastrar Obra");
        JButton botao2 = new JButton("Remover Obra");
        JButton botao3 = new JButton("Avaliar Obra");
        JButton botao4 = new JButton("Listar Obras");
        JButton botao5 = new JButton("Buscar por Autor");
        JButton botao6 = new JButton("Top Obras");
        JButton botao7 = new JButton("Criar Exposicao");
        JButton botao8 = new JButton("Inserir Obra");
        JButton botao9 = new JButton("Exposicao Obras");
        JButton botao10 = new JButton("Top Exposições");


        botao1.addActionListener(e -> cardLayout.show(painelCentral,"tela_1"));
        botao2.addActionListener(e -> cardLayout.show(painelCentral,"tela_2"));
        botao3.addActionListener(e -> cardLayout.show(painelCentral,"tela_3"));
        botao4.addActionListener(e -> cardLayout.show(painelCentral,"tela_4"));
        botao5.addActionListener(e -> cardLayout.show(painelCentral,"tela_5"));
        botao6.addActionListener(e -> cardLayout.show(painelCentral,"tela_6"));
        botao7.addActionListener(e -> cardLayout.show(painelCentral,"tela_7"));
        botao8.addActionListener(e -> cardLayout.show(painelCentral,"tela_8"));
        botao9.addActionListener(e -> cardLayout.show(painelCentral,"tela_9"));
        botao10.addActionListener(e -> cardLayout.show(painelCentral,"tela_10"));


        menu.add(botao1);
        menu.add(botao2);
        menu.add(botao3);
        menu.add(botao4);
        menu.add(botao5);
        menu.add(botao6);
        menu.add(botao7);
        menu.add(botao8);
        menu.add(botao9);
        menu.add(botao10);

        return menu;
    }
}
