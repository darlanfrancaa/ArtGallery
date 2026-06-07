package br.ui;

import br.ArtGallery;
import br.IArtGallery;
import br.repository.IRepositorioObra;
import br.service.AvaliacaoService;
import br.service.ObraService;
import br.ui.components.TelaCadastroObras;

import javax.swing.*;
import java.awt.*;

public class MinhaJanela extends JFrame {

    private final IArtGallery artGallery;

    private JPanel painelCentral;
    private CardLayout cardLayout;

    MinhaJanela(String titulo, IArtGallery artGallery){

        super(titulo);
        this.artGallery = artGallery;

        setLayout(new BorderLayout());

        JPanel menuLateral = buildMenuLateral();
        add(menuLateral, BorderLayout.WEST);

        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);

        TelaCadastroObras cadastroObras = new TelaCadastroObras(artGallery);
        JLabel teste = new JLabel("Teste", SwingConstants.CENTER);

        painelCentral.add(cadastroObras, "tela_1");
        painelCentral.add(new JLabel("Tela 2: Remoção de Obras", SwingConstants.CENTER), "tela_2");
        painelCentral.add(teste, "tela_3");
        painelCentral.add(teste, "tela_4");
        painelCentral.add(teste, "tela_5");
        painelCentral.add(teste, "tela_6");
        painelCentral.add(teste, "tela_7");

        add(painelCentral, BorderLayout.CENTER);

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildMenuLateral(){
        JPanel menu = new JPanel(new GridLayout(7,1,5,5));
        menu.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JButton botao1 = new JButton("Cadastrar Obra");
        JButton botao2 = new JButton("Remover Obra");
        JButton botao3 = new JButton("Avaliar Obra");
        JButton botao4 = new JButton("Listar Obras");
        JButton botao5 = new JButton("Buscar por Autor");
        JButton botao6 = new JButton("Top Obras");
        JButton botao7 = new JButton("Exposicao Obras");


        botao1.addActionListener(e -> cardLayout.show(painelCentral,"tela_1"));
        botao2.addActionListener(e -> cardLayout.show(painelCentral,"tela_2"));
        botao3.addActionListener(e -> cardLayout.show(painelCentral,"tela_3"));
        botao4.addActionListener(e -> cardLayout.show(painelCentral,"tela_4"));
        botao5.addActionListener(e -> cardLayout.show(painelCentral,"tela_5"));
        botao6.addActionListener(e -> cardLayout.show(painelCentral,"tela_6"));
        botao7.addActionListener(e -> cardLayout.show(painelCentral,"tela_7"));


        menu.add(botao1);
        menu.add(botao2);
        menu.add(botao3);
        menu.add(botao4);
        menu.add(botao5);
        menu.add(botao6);
        menu.add(botao7);

        return menu;
    }
}
