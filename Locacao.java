import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Locacao {
    private int idlocacoes; // ID da locação
    private int idlocatario; // ID do locatário
    private int idveiculo; // ID do veículo
    private Date dataInicio; // Data de início da locação
    private Date dataTermino; // Data de término da locação
    private String statusLocacao; // Status da locação

    public int getIdlocacoes() {
        return idlocacoes;
    }

    public void setIdlocacoes(int idlocacoes) {
        this.idlocacoes = idlocacoes;
    }

    public int getIdlocatario() {
        return idlocatario;
    }

    public void setIdlocatario(int idlocatario) {
        this.idlocatario = idlocatario;
    }

    public int getIdveiculo() {
        return idveiculo;
    }

    public void setIdveiculo(int idveiculo) {
        this.idveiculo = idveiculo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getStatusLocacao() {
        return statusLocacao;
    }

    public void setStatusLocacao(String statusLocacao) {
        this.statusLocacao = statusLocacao;
    }

    public void save() {
        String sql = "INSERT INTO locacoes (idlocatario, idveiculo, data_inicio, data_termino, status_locacao) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.idlocatario);
            stmt.setInt(2, this.idveiculo);
            stmt.setDate(3, this.dataInicio);
            stmt.setDate(4, this.dataTermino);
            stmt.setString(5, this.statusLocacao);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.idlocacoes = generatedKeys.getInt(1); // Atualiza o ID da locação
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Locacao> getAll() {
        List<Locacao> locacoes = new ArrayList<>();
        String sql = "SELECT * FROM locacoes";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Locacao locacao = new Locacao();
                locacao.setIdlocacoes(rs.getInt("idlocacoes"));
                locacao.setIdlocatario(rs.getInt("idlocatario"));
                locacao.setIdveiculo(rs.getInt("idveiculo"));
                locacao.setDataInicio(rs.getDate("data_inicio"));
                locacao.setDataTermino(rs.getDate("data_termino"));
                locacao.setStatusLocacao(rs.getString("status_locacao"));
                locacoes.add(locacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locacoes;
    }

    public void update() {
        String sql = "UPDATE locacoes SET idlocatario = ?, idveiculo = ?, data_inicio = ?, data_termino = ?, status_locacao = ? WHERE idlocacoes = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idlocatario);
            stmt.setInt(2, this.idveiculo);
            stmt.setDate(3, this.dataInicio);
            stmt.setDate(4, this.dataTermino);
            stmt.setString(5, this.statusLocacao);
            stmt.setInt(6, this.idlocacoes);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "DELETE FROM locacoes WHERE idlocacoes = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idlocacoes);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
