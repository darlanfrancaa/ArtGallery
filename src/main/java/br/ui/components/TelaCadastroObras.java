package br.ui.components;

import br.exception.ObraJaCadastradaException;
import br.model.ArteGenerativa;
import br.model.Modelagem3D;
import br.model.Obra;
import br.model.PinturaDigital;
import br.repository.IRepositorioObra;
import br.service.AvaliacaoService;
import br.service.ObraService;
import com.sun.jdi.IntegerType;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.nio.channels.ScatteringByteChannel;
import java.text.NumberFormat;
import br.*;


public class TelaCadastroObras extends JPanel {

    private JTextField titulo;
    private JTextField autor;
    private JComboBox<String> tipoObra;

    private JPanel painelObraEspecifica;
    private CardLayout cardLayout;

    private JTextField resolucao;
    private JTextField softwarePintura;

    private JTextField algoritmo;
    private JTextField seed;

    private JTextField poligonos;
    private JTextField engine;

    private final IArtGallery artGallery;

    public TelaCadastroObras(IArtGallery artGallery){
        this.artGallery = artGallery;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inicial = new JPanel(new GridLayout(0,2,10,10));
        titulo = new JTextField();
        autor = new JTextField();

        String[] tipos = {"Pintura Digital", "Arte Generativa", "Modelagem 3D"};
        tipoObra = new JComboBox<>(tipos);
        inicial.add(new JLabel("Titulo:"));
        inicial.add(titulo);
        inicial.add(new JLabel("Autor:"));
        inicial.add(autor);
        inicial.add(new JLabel("Tipo da Obra:"));
        inicial.add(tipoObra);

        // Agora vamos criar os cards específicos para serem mostrados quando o tipo obra for escolhido

        cardLayout = new CardLayout();
        painelObraEspecifica = new JPanel(cardLayout);

        JPanel cardPintura = new JPanel(new GridLayout(0, 2, 10, 10));
        resolucao = new JTextField();
        softwarePintura = new JTextField();
        cardPintura.add(new JLabel("Resolução:"));
        cardPintura.add(resolucao);
        cardPintura.add(new JLabel("Software:"));
        cardPintura.add(softwarePintura);

        JPanel cardArte = new JPanel(new GridLayout(0, 2, 10, 10));
        algoritmo = new JTextField();
        seed = new JTextField();
        cardArte.add(new JLabel("Algoritmo:"));
        cardArte.add(algoritmo);
        cardArte.add(new JLabel("Seed: "));
        cardArte.add(seed);

        JPanel cardModelagem = new JPanel(new GridLayout(0, 2, 10, 10));
        poligonos = new JTextField();
        engine = new JTextField();
        cardModelagem.add(new JLabel("Qtd. Polígonos:"));
        cardModelagem.add(poligonos);
        cardModelagem.add(new JLabel("Engine:"));
        cardModelagem.add(engine);

        painelObraEspecifica.add(cardPintura, "Pintura Digital");
        painelObraEspecifica.add(cardArte, "Arte Generativa");
        painelObraEspecifica.add(cardModelagem, "Modelagem 3D");

        tipoObra.addActionListener(e -> {
            String tipoEspecifico = (String) tipoObra.getSelectedItem();
            cardLayout.show(painelObraEspecifica, tipoEspecifico);
        });

        JButton botaoSalvar = new JButton("Cadastrar Obra");
        botaoSalvar.addActionListener(e -> cadastrarObra());

        JPanel total = new JPanel(new BorderLayout(0,10));
        total.add(inicial, BorderLayout.NORTH);
        total.add(painelObraEspecifica, BorderLayout.CENTER);
        total.add(botaoSalvar, BorderLayout.SOUTH);

        add(total, BorderLayout.NORTH);
    }

    private void cadastrarObra(){
        String tituloObra = titulo.getText().trim();
        String autorObra = autor.getText().trim();
        String tipoEscolhido = (String) tipoObra.getSelectedItem();
        if (tituloObra.isEmpty() || autorObra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o título e o autor da obra.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (tipoEscolhido == null) {
            JOptionPane.showMessageDialog(this, "Selecione algum tipo da obra.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Obra obra = null;
        try{
            if(tipoEscolhido.equals("Modelagem 3D")) {
                int qtdPoligonosObra = Integer.parseInt(poligonos.getText().trim());
                String engineObra = engine.getText().trim();
                obra = new Modelagem3D(tituloObra, autorObra, qtdPoligonosObra, engineObra);
            }
            else if(tipoEscolhido.equals("Arte Generativa")) {
                String algoritmoObra = algoritmo.getText().trim();
                long seedObra = Long.parseLong(seed.getText().trim());
                obra = new ArteGenerativa(tituloObra, autorObra, algoritmoObra, seedObra);
            }
            else if(tipoEscolhido.equals("Pintura Digital")) {
                String resolucaoObra = resolucao.getText().trim();
                String softwareObra = softwarePintura.getText().trim();
                obra = new PinturaDigital(tituloObra, autorObra, resolucaoObra, softwareObra);
            }

            if(obra != null){
                artGallery.publicarObra(obra);
                JOptionPane.showMessageDialog(this, "Obra cadastrada com sucesso.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: os campos númericos estão inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch (ObraJaCadastradaException e) {
            JOptionPane.showMessageDialog(this, "Essa obra já foi cadastrada", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao cadastrar obra.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
