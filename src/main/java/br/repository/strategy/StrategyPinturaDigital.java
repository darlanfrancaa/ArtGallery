package br.repository.strategy;

import br.model.Obra;
import br.model.PinturaDigital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StrategyPinturaDigital implements IStrategyBank {
    @Override
    public String getTipo(){
        return "PinturaDigital";
    }

    @Override
    public void inserir(Connection connection, int obraId, Obra obra) throws SQLException {
        PinturaDigital pinturaDigital = (PinturaDigital) obra;
        String sql = "INSERT INTO pinturas_digitais (obra_id, resolucao, software) VALUES (?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obraId);
            statement.setString(2, pinturaDigital.getResolucao());
            statement.setString(3, pinturaDigital.getSoftwareUtilizado());
            statement.executeUpdate();
        }
    }

    @Override
    public void atualizar(Connection connection, int obraId, Obra obra) throws SQLException {
        PinturaDigital pinturaDigital = (PinturaDigital) obra;
        String sql = "UPDATE pinturas_digitais SET resolucao = ?, software = ? WHERE obra_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, pinturaDigital.getResolucao());
            statement.setString(2, pinturaDigital.getSoftwareUtilizado());
            statement.setInt(3, obraId);
            statement.executeUpdate();
        }
    }

    @Override
    public Obra buscar(Connection connection, int obraId, String titulo, String autor, boolean ativa) throws SQLException {
        String sql = "SELECT resolucao, software FROM pinturas_digitais WHERE obra_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obraId);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                String resolucaoResultado = rs.getString("resolucao");
                String softwareResultado = rs.getString("software");
                PinturaDigital pd = new PinturaDigital(titulo, autor, resolucaoResultado, softwareResultado);
                pd.setAtiva(ativa);
                pd.setId(obraId);
                return pd;
            }
        }
        return null;
    }

    @Override
    public Vector<Obra> listAll(Connection connection) throws SQLException {
        Vector<Obra> lista = new Vector<>();
        String sql = "SELECT o.id, o.titulo, o.autor, o.ativa, p.resolucao, p.software " +
                "FROM obras o INNER JOIN pinturas_digitais p ON o.id = p.obra_id";

        try(PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {

            while(rs.next()) {
                PinturaDigital pd = new PinturaDigital(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("resolucao"),
                        rs.getString("software")
                );
                pd.setId(rs.getInt("id"));
                pd.setAtiva(rs.getBoolean("ativa"));
                lista.add(pd);
            }
        }
        return lista;
    }
}
