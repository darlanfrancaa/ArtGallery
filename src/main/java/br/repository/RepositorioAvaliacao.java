package br.repository;

import br.model.Avaliacao;
import br.config.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RepositorioAvaliacao implements IRepositorioAvaliacao {

    public void adicionar(int obraId, Avaliacao avaliacao) {
        String sql = "INSERT INTO avaliacoes (obra_id, usuario, nota, comentario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, obraId);
            statement.setString(2, avaliacao.getUsuario());
            statement.setInt(3, avaliacao.getNota());
            statement.setString(4, avaliacao.getComentario());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir a avaliação", e);
        }
    }

    public Vector<Avaliacao> buscarPorObra(int obraId) {
        Vector<Avaliacao> avaliacoes = new Vector<>();
        String sql = "SELECT usuario, nota, comentario FROM avaliacoes WHERE obra_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, obraId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao(
                        rs.getString("usuario"),
                        rs.getInt("nota"),
                        rs.getString("comentario")
                );
                avaliacoes.add(avaliacao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar as avaliações", e);
        }

        return avaliacoes;
    }
}