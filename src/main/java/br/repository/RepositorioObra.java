package br.repository;

import br.config.ConnectionFactory;
import br.exception.ObraNaoEncontradaException;
import br.model.ArteGenerativa;
import br.model.Modelagem3D;
import br.model.Obra;
import br.model.PinturaDigital;
import br.repository.strategy.IStrategyBank;
import br.repository.strategy.StrategyArteGenerativa;
import br.repository.strategy.StrategyModelagem3D;
import br.repository.strategy.StrategyPinturaDigital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RepositorioObra implements IRepositorioObra{
    private final Map<Class<? extends Obra>, IStrategyBank> registry = new HashMap<>();
    private final RepositorioAvaliacao avalicaoRepository = new RepositorioAvaliacao();

    public RepositorioObra(){
        registry.put(PinturaDigital.class, new StrategyPinturaDigital());
        registry.put(ArteGenerativa.class, new StrategyArteGenerativa());
        registry.put(Modelagem3D.class, new StrategyModelagem3D());
    }

    @Override
    public void cadastrar(Obra obra){
        // Vai pegar a classe de Strategy em adequada à classe da Obra
        IStrategyBank strategyInsercao = registry.get(obra.getClass());

        if(strategyInsercao == null){
            throw new IllegalArgumentException("Tipo de obra inexistente: + " + obra.getClass().getSimpleName());
        }

        String sql = "INSERT INTO obras (titulo, autor, ativa, tipo) VALUES (?,?,?,?)";
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            // Insere na tabelas de obras e depois insere na tabela da cada um dos tipos

            connection.setAutoCommit(false);

            statement.setString(1, obra.getTitulo());
            statement.setString(2, obra.getAutor());
            statement.setBoolean(3, obra.isAtiva());
            statement.setString(4, strategyInsercao.getTipo());

            statement.executeUpdate();

            ResultSet resultSetId = statement.getGeneratedKeys();
            if(resultSetId.next()){
                int obraId = resultSetId.getInt(1);
                strategyInsercao.inserir(connection, obraId, obra);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar obra", e);
        }
    }

    @Override
    public Obra buscar(String titulo) {
        String sql = "SELECT id, autor, ativa, tipo FROM obras WHERE titulo = ?";
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, titulo);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                int id = rs.getInt("id");
                String autor = rs.getString("autor");
                boolean ativa = rs.getBoolean("ativa");
                String tipo = rs.getString("tipo");

                IStrategyBank strategyBank = getStrategyByTipo(tipo);
                if(strategyBank != null){
                    Obra obraEncontrada = strategyBank.buscar(connection, id, titulo, autor, ativa);
                    obraEncontrada.setAvaliacoes(avalicaoRepository.buscarPorObra(id));
                    return obraEncontrada;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // No RepositorioObra.java
    @Override
    public boolean atualizar(Obra obra) {
        String sql = "UPDATE obras SET autor = ?, ativa = ? WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, obra.getAutor());
                statement.setBoolean(2, obra.isAtiva());
                statement.setInt(3, obra.getId());

                int numUpdates = statement.executeUpdate();

                if (numUpdates > 0) {
                    IStrategyBank strategyBank = registry.get(obra.getClass());
                    strategyBank.atualizar(connection, obra.getId(), obra);
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a obra no banco", e);
        }
    }

    // Assumindo que não tem duas obras com o mesmo título
    @Override
    public void remover(String titulo) {
        String sql = "UPDATE obras SET ativa = false WHERE titulo = ?";
        try (Connection conn = ConnectionFactory.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,titulo);
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Erro ao desativar obra", e);
        }
    }

    @Override
    public Vector<Obra> listar() {
        Vector<Obra> obrasCadastradas = new Vector<>();

        try(Connection conn = ConnectionFactory.getConnection()) {
            for(IStrategyBank strategy : registry.values()) {
                obrasCadastradas.addAll(strategy.listAll(conn));
            }
            // Preenhce os valores de avaliação de cada um dos objetos Obras
            for(Obra obra : obrasCadastradas) {
                obra.setAvaliacoes(avalicaoRepository.buscarPorObra(obra.getId()));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar", e);
        }
        return obrasCadastradas;
    }

    // Retorna a classe strategy baseada no nome do tipo que está no banco/map (Útil para o buscar no banco
    //         que após a query principal você não tem diretamente a classe )
    private IStrategyBank getStrategyByTipo(String tipo){
        for(IStrategyBank strategyBank: registry.values()){
            if(strategyBank.getTipo().equals(tipo)) {
                return strategyBank;
            }
        }
        return null;
    }

    @Override
    public Vector<Obra> findByAutor(String autor) {
        Vector<Obra> obras = new Vector<>();
        String sql = "SELECT id, titulo, autor, ativa, tipo FROM obras WHERE autor = ?";
        try(Connection conn = ConnectionFactory.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, autor);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int idBanco = rs.getInt("id");
                    String tituloBanco = rs.getString("titulo");
                    String autorBanco = rs.getString("autor");
                    boolean ativaBanco = rs.getBoolean("ativa");
                    String tipoBanco = rs.getString("tipo");

                    IStrategyBank strategyBank = getStrategyByTipo(tipoBanco);

                    if (strategyBank != null) {
                        Obra obraEncontrada = strategyBank.buscar(conn, idBanco, tituloBanco, autorBanco, ativaBanco);
                        // Precisa preenhcer o vetor de Avaliacoes daquela obra também
                        obraEncontrada.setAvaliacoes(avalicaoRepository.buscarPorObra(idBanco));
                        obras.add(obraEncontrada);
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Não foi possivel buscar as obras do autor: " + autor, e);
        }
        return null;
    }

    public Obra buscarObraPorTipo(Connection conn, int id, String titulo, String autor, boolean ativa, String tipo) throws SQLException {
        IStrategyBank strategyBank = this.getStrategyByTipo(tipo);
        if (strategyBank != null) {
            Obra obraEncontrada = strategyBank.buscar(conn, id, titulo, autor, ativa);
            obraEncontrada.setAvaliacoes(this.avalicaoRepository.buscarPorObra(id));
            return obraEncontrada;
        }
        return null;
    }

}
