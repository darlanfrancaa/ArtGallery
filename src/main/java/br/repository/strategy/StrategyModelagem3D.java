package br.repository.strategy;

import br.model.Modelagem3D;
import br.model.Obra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StrategyModelagem3D implements IStrategyBank {
    @Override
    public String getTipo(){
        return "Modelagem3D";
    }

    @Override
    public void inserir(Connection connection, int obraId, Obra obra) throws SQLException {
        Modelagem3D modelagem3D = (Modelagem3D) obra;
        String sql = "INSERT INTO modelagens_3d (obra_id, poligonos, engine) VALUES (?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obraId);
            statement.setInt(2, modelagem3D.getNumeroPoligonos());
            statement.setString(3, modelagem3D.getEngine());
            statement.executeUpdate();
        }
    }

    @Override
    public void atualizar(Connection connection, int obraId, Obra obra) throws SQLException {
        Modelagem3D modelagem3D = (Modelagem3D) obra;
        String sql = "UPDATE modelagens_3d SET poligonos = ?, engine = ? WHERE obra_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, modelagem3D.getNumeroPoligonos());
            statement.setString(2, modelagem3D.getEngine());
            statement.setInt(3, obraId);
            statement.executeUpdate();
        }
    }

    @Override
    public Obra buscar(Connection connection, int obraId, String titulo, String autor, boolean ativa) throws SQLException {
        String sql = "SELECT poligonos, engine FROM modelagens_3d WHERE obra_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, obraId);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                int poligonosResultado = rs.getInt("poligonos");
                String engineResultado = rs.getString("engine");
                Modelagem3D m3d = new Modelagem3D(titulo, autor, poligonosResultado, engineResultado);
                m3d.setAtiva(ativa);
                m3d.setId(obraId);
                return m3d;
            }
        }
        return null;
    }

    @Override
    public Vector<Obra> listAll(Connection connection) throws SQLException {
        Vector<Obra> lista = new Vector<>();
        String sql = "SELECT o.titulo, o.autor, o.ativa, m.poligonos, m.engine " +
                "FROM obras o INNER JOIN modelagens_3d m ON o.id = m.obra_id";

        try(PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {

            while(rs.next()) {
                Modelagem3D m3d = new Modelagem3D(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("poligonos"),
                        rs.getString("engine")
                );
                m3d.setAtiva(rs.getBoolean("ativa"));
                lista.add(m3d);
            }
        }
        return lista;
    }
}
