import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pagamento {
    private int idpagamentos; // ID do pagamento
    private int idlocacoes; // ID da locação
    private float valorTotal; // Valor total do pagamento
    private Date dataPagamento; // Data do pagamento
    private int metodoPagamento; // Método de pagamento (pode ser um ID para um tipo de pagamento)
    private String statusPagamento; // Status do pagamento

    // Getters e Setters
    public int getIdpagamentos() {
        return idpagamentos;
    }

    public void setIdpagamentos(int idpagamentos) {
        this.idpagamentos = idpagamentos;
    }

    public int getIdlocacoes() {
        return idlocacoes;
    }

    public void setIdlocacoes(int idlocacoes) {
        this.idlocacoes = idlocacoes;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public int getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(int metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    // Método para salvar pagamento no banco de dados
    public void save() {
        String sql = "INSERT INTO pagamentos (idlocacoes, valor_total, data_pagamento, metodo_pagamento, status_pagamento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.idlocacoes);
            stmt.setFloat(2, this.valorTotal);
            stmt.setDate(3, this.dataPagamento);
            stmt.setInt(4, this.metodoPagamento);
            stmt.setString(5, this.statusPagamento);
            stmt.executeUpdate();

            // Obter o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.idpagamentos = generatedKeys.getInt(1); // Atualiza o ID do pagamento
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obter todos os pagamentos
    public static List<Pagamento> getAll() {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamentos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setIdpagamentos(rs.getInt("idpagamentos"));
                pagamento.setIdlocacoes(rs.getInt("idlocacoes"));
                pagamento.setValorTotal(rs.getFloat("valor_total"));
                pagamento.setDataPagamento(rs.getDate("data_pagamento"));
                pagamento.setMetodoPagamento(rs.getInt("metodo_pagamento"));
                pagamento.setStatusPagamento(rs.getString("status_pagamento"));
                pagamentos.add(pagamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagamentos;
    }

    // Método para atualizar pagamento
    public void update() {
        String sql = "UPDATE pagamentos SET idlocacoes = ?, valor_total = ?, data_pagamento = ?, metodo_pagamento = ?, status_pagamento = ? WHERE idpagamentos = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idlocacoes);
            stmt.setFloat(2, this.valorTotal);
            stmt.setDate(3, this.dataPagamento);
            stmt.setInt(4, this.metodoPagamento);
            stmt.setString(5, this.statusPagamento);
            stmt.setInt(6, this.idpagamentos);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar pagamento
    public void delete() {
        String sql = "DELETE FROM pagamentos WHERE idpagamentos = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idpagamentos);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
