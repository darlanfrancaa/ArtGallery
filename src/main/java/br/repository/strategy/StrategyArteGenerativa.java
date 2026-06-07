package br.repository.strategy;

import br.model.ArteGenerativa;
import br.model.Obra;

import java.awt.geom.Area;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StrategyArteGenerativa implements IStrategyBank {

    @Override
    public String getTipo() {
        return "ArteGenerativa";
    }

    @Override
    public void inserir(Connection connection, int obraId, Obra obra) throws SQLException {
        ArteGenerativa arte = (ArteGenerativa) obra;
        String sql = "INSERT INTO artes_generativas (obra_id, algoritmo, seed) VALUES (?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obraId);
            statement.setString(2, arte.getAlgoritmo());
            statement.setLong(3, arte.getSeed());
            statement.executeUpdate();
        }
    }

    @Override
    public void atualizar(Connection connection, int obraId, Obra obra) throws SQLException {
        ArteGenerativa arteGenerativa = (ArteGenerativa) obra;
        String sql = "UPDATE artes_generativas SET algoritmo = ?, seed = ? WHERE obra_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, arteGenerativa.getAlgoritmo());
            statement.setLong(2, arteGenerativa.getSeed());
            statement.setInt(3, obraId);
            statement.executeUpdate();
        }
    }

    @Override
    public Obra buscar(Connection connection, int obraId, String titulo, String autor, boolean ativa) throws SQLException {
        String sql = "SELECT algoritmo, seed FROM artes_generativas WHERE obra_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obraId);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                String algoritmoResultado = rs.getString("algoritmo");
                long seedResultado = rs.getLong("seed");
                ArteGenerativa ag = new ArteGenerativa(titulo, autor, algoritmoResultado, seedResultado);
                ag.setAtiva(ativa);
                return ag;
            }
        }
        return null;
    }

    @Override
    public Vector<Obra> listAll(Connection connection) throws SQLException {
        Vector<Obra> lista = new Vector<>();
        String sql = "SELECT o.id, o.titulo, o.autor, o.ativa, a.algoritmo, a.seed " +
                "FROM obras o INNER JOIN artes_generativas a ON o.id = a.obra_id";

        try(PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {

            while(rs.next()) {
                ArteGenerativa ag = new ArteGenerativa(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("algoritmo"),
                        rs.getLong("seed")
                );
                ag.setId(rs.getInt("id"));
                ag.setAtiva(rs.getBoolean("ativa"));
                lista.add(ag);
            }
        }
        return lista;
    }


}
