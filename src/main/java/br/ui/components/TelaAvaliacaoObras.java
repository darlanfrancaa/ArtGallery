package br.ui.components;

import br.IArtGallery;
import br.exception.NotaInvalidaException;
import br.exception.ObraInativaException;
import br.exception.ObraNaoEncontradaException;
import br.model.Avaliacao;

import javax.print.attribute.standard.JobName;
import javax.swing.*;
import java.awt.*;

public class TelaAvaliacaoObras extends JPanel {
    private JTextField usuario;
    private JTextField nota;
    private JTextField comentario;
    private JTextField titulo;
    private final IArtGallery artGallery;

    public TelaAvaliacaoObras(IArtGallery artGallery) {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.artGallery = artGallery;
        setLayout(new BorderLayout());
        JPanel inicial = new JPanel(new GridLayout(0,2,10,10));
        usuario = new JTextField();
        inicial.add(new JLabel("Seu nome:"));
        inicial.add(usuario);
        titulo = new JTextField();
        inicial.add(new JLabel("Título da Obra:"));
        inicial.add(titulo);
        nota = new JTextField();
        inicial.add(new JLabel("Nota:"));
        inicial.add(nota);
        comentario = new JTextField();
        inicial.add(new JLabel("Comentário:"));
        inicial.add(comentario);

        JButton botaoAvaliarObra = new JButton("Avaliar Obra");
        botaoAvaliarObra.addActionListener(e -> avaliarObra());
        JPanel total = new JPanel(new BorderLayout(0,10));
        total.add(inicial, BorderLayout.NORTH);
        total.add(botaoAvaliarObra, BorderLayout.SOUTH);

        add(total, BorderLayout.NORTH);
    }

    private void avaliarObra(){
        String tituloObra = titulo.getText().trim();
        String avaliacaoUsuario = usuario.getText().trim();
        String avaliacaoComentario = comentario.getText().trim();
        try{
            int avaliacaoNota = Integer.parseInt(nota.getText());
            Avaliacao avaliacao = new Avaliacao(avaliacaoUsuario, avaliacaoNota,avaliacaoComentario);
            artGallery.avaliarObra(tituloObra, avaliacao);
            JOptionPane.showMessageDialog(this, "Avaliação para a obra " + tituloObra + " salva com sucesso.");
        } catch (NotaInvalidaException e){
            JOptionPane.showMessageDialog(this, "A nota não é válida.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "A nota deve ser um número inteiro", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (ObraInativaException e) {
            JOptionPane.showMessageDialog(this, "Essa obra está inativa, não é possível avaliá-la", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (ObraNaoEncontradaException e) {
            JOptionPane.showMessageDialog(this, "Essa obra não foi encontrada", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
