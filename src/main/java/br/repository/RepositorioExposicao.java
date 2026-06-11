package br.repository;

import br.config.ConnectionFactory;
import br.exception.NotaInvalidaException;
import br.model.*;
import br.repository.strategy.IStrategyBank;
import br.repository.strategy.StrategyArteGenerativa;
import br.repository.strategy.StrategyModelagem3D;
import br.repository.strategy.StrategyPinturaDigital;
import org.postgresql.util.PSQLException;

import javax.swing.text.html.HTMLDocument;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RepositorioExposicao implements IRepositorioExposicao {

    private final Map<Class<? extends Obra>, IStrategyBank> registry = new HashMap<>();
    private final IRepositorioObra obraRepository;

    public RepositorioExposicao(IRepositorioObra obraRepository) {
        this.obraRepository = obraRepository;
        registry.put(PinturaDigital.class, new StrategyPinturaDigital());
        registry.put(ArteGenerativa.class, new StrategyArteGenerativa());
        registry.put(Modelagem3D.class, new StrategyModelagem3D());
    }

    @Override
    public void cadastrar(Exposicao exposicao) {
        String sql = "INSERT INTO exposicoes (nome) VALUES (?)";
        String sqlObras = "INSERT INTO exposicoes_obras (exposicao_id, obra_id) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, exposicao.getNome());
                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException( "Não foi possível obter o ID da exposição.");
                    }
                    int idExposicao = rs.getInt(1);
                    exposicao.setId(idExposicao);
                    try (PreparedStatement statementObras = conn.prepareStatement(sqlObras)) {
                        for (Obra obra : exposicao.listarObras()) {
                            statementObras.setInt(1, idExposicao);
                            statementObras.setInt(2, obra.getId());
                            statementObras.addBatch();
                        }
                        statementObras.executeBatch();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar exposição",e);
        }
    }

    @Override
    public void adicionar(Exposicao exposicao, Obra obra) {
        String sql = "INSERT INTO exposicoes_obras (exposicao_id, obra_id) VALUES (?, ?)";
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, exposicao.getId());
            statement.setInt(2,obra.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possivel inserir exposição", e);
        }
    }

    public Exposicao getExpByNome(String nomeExposicao) throws NotaInvalidaException{
        String sql = "SELECT id, nome FROM exposicoes WHERE nome = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nomeExposicao);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Exposicao exposicao = new Exposicao(rs.getString("nome"));
                    Vector<Obra> obrasDaExposicao = this.getObras(exposicao);
                    for (Obra obra : obrasDaExposicao) {
                        exposicao.adicionarObra(obra);
                    }
                    return exposicao;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a exposição pelo nome: " + nomeExposicao, e);
        }
        return null;
    }

    public Vector<Obra> getObras(Exposicao exposicao) throws NotaInvalidaException {
        Vector<Obra> obrasExpostas = new Vector<>();
        String sql = "SELECT o.id, o.titulo, o.autor, o.ativa, o.tipo " +
                "FROM obras o " +
                "INNER JOIN exposicoes_obras eo ON o.id = eo.obra_id " +
                "INNER JOIN exposicoes e ON e.id = eo.exposicao_id " +
                "WHERE e.nome = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, exposicao.getNome());

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int idBanco = rs.getInt("id");
                    String tituloBanco = rs.getString("titulo");
                    String autorBanco = rs.getString("autor");
                    boolean ativaBanco = rs.getBoolean("ativa");
                    String tipoBanco = rs.getString("tipo");

                    // Já retorna com as avaliações
                    Obra obraCompleta = obraRepository.buscarObraPorTipo(
                            conn, idBanco, tituloBanco, autorBanco, ativaBanco, tipoBanco
                    );

                    if (obraCompleta != null) {
                        obrasExpostas.add(obraCompleta);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar as obras da exposição: " + exposicao.getNome(), e);
        }

        return obrasExpostas;
    }
}
