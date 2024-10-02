import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Devolucao {
    private int iddevolucoes; // ID da devolução
    private int idlocacoes; // ID da locação relacionada
    private Date dataDevolucao; // Data da devolução
    private float quilometragem; // Quilometragem do veículo
    private String danos; // Danos do veículo
    private float multaAtraso; // Multa por atraso

    public int getIddevolucoes() {
        return iddevolucoes;
    }

    public void setIddevolucoes(int iddevolucoes) {
        this.iddevolucoes = iddevolucoes;
    }

    public int getIdlocacoes() {
        return idlocacoes;
    }

    public void setIdlocacoes(int idlocacoes) {
        this.idlocacoes = idlocacoes;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public float getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(float quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getDanos() {
        return danos;
    }

    public void setDanos(String danos) {
        this.danos = danos;
    }

    public float getMultaAtraso() {
        return multaAtraso;
    }

    public void setMultaAtraso(float multaAtraso) {
        this.multaAtraso = multaAtraso;
    }

    public void save() {
        String sql = "INSERT INTO devolucoes (idlocacoes, data_devolucao, quilometragem, danos, multa_atraso) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.idlocacoes);
            stmt.setDate(2, this.dataDevolucao);
            stmt.setFloat(3, this.quilometragem);
            stmt.setString(4, this.danos);
            stmt.setFloat(5, this.multaAtraso);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.iddevolucoes = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Devolucao> getAll() {
        List<Devolucao> devolucoes = new ArrayList<>();
        String sql = "SELECT * FROM devolucoes";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Devolucao devolucao = new Devolucao();
                devolucao.setIddevolucoes(rs.getInt("iddevolucoes"));
                devolucao.setIdlocacoes(rs.getInt("idlocacoes"));
                devolucao.setDataDevolucao(rs.getDate("data_devolucao"));
                devolucao.setQuilometragem(rs.getFloat("quilometragem"));
                devolucao.setDanos(rs.getString("danos"));
                devolucao.setMultaAtraso(rs.getFloat("multa_atraso"));
                devolucoes.add(devolucao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devolucoes;
    }

    public void update() {
        String sql = "UPDATE devolucoes SET idlocacoes = ?, data_devolucao = ?, quilometragem = ?, danos = ?, multa_atraso = ? WHERE iddevolucoes = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idlocacoes);
            stmt.setDate(2, this.dataDevolucao);
            stmt.setFloat(3, this.quilometragem);
            stmt.setString(4, this.danos);
            stmt.setFloat(5, this.multaAtraso);
            stmt.setInt(6, this.iddevolucoes);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "DELETE FROM devolucoes WHERE iddevolucoes = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.iddevolucoes);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
