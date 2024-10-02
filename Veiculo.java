import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Veiculo {
    private int idveiculo; // ID do veículo
    private String placa; // Placa do veículo
    private String marca; // Marca do veículo
    private String modelo; // Modelo do veículo
    private int ano; // Ano do veículo
    private String disponibilidade; // Disponibilidade do veículo
    private float valorDiaria; // Valor da diária

    public int getIdveiculo() {
        return idveiculo;
    }

    public void setIdveiculo(int idveiculo) {
        this.idveiculo = idveiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public float getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(float valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public void save() {
        String sql = "INSERT INTO veiculos (placa, marca, modelo, ano, disponibilidade, valor_diaria) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.placa);
            stmt.setString(2, this.marca);
            stmt.setString(3, this.modelo);
            stmt.setInt(4, this.ano);
            stmt.setString(5, this.disponibilidade);
            stmt.setFloat(6, this.valorDiaria);
            stmt.executeUpdate();

            // Obter o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.idveiculo = generatedKeys.getInt(1); // Atualiza o ID do veículo
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Veiculo> getAll() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setIdveiculo(rs.getInt("idveiculo"));
                veiculo.setPlaca(rs.getString("placa"));
                veiculo.setMarca(rs.getString("marca"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setAno(rs.getInt("ano"));
                veiculo.setDisponibilidade(rs.getString("disponibilidade"));
                veiculo.setValorDiaria(rs.getFloat("valor_diaria"));
                veiculos.add(veiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    public void update() {
        String sql = "UPDATE veiculos SET placa = ?, marca = ?, modelo = ?, ano = ?, disponibilidade = ?, valor_diaria = ? WHERE idveiculo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.placa);
            stmt.setString(2, this.marca);
            stmt.setString(3, this.modelo);
            stmt.setInt(4, this.ano);
            stmt.setString(5, this.disponibilidade);
            stmt.setFloat(6, this.valorDiaria);
            stmt.setInt(7, this.idveiculo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "DELETE FROM veiculos WHERE idveiculo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idveiculo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
