import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Locatario {
    private int idlocatarios; // ID do locatário
    private String nome; // Nome do locatário
    private String dataNasc; // Data de nascimento
    private String endereco; // Endereço
    private String telefone; // Telefone
    private String cpf; // CPF
    private String cnh; // CNH

    public int getIdlocatarios() {
        return idlocatarios;
    }

    public void setIdlocatarios(int idlocatarios) {
        this.idlocatarios = idlocatarios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public void save() {
        String sql = "INSERT INTO locatarios (nome, data_nasc, endereço, telefone, cpf, cnh) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.nome);
            stmt.setString(2, this.dataNasc);
            stmt.setString(3, this.endereco);
            stmt.setString(4, this.telefone);
            stmt.setString(5, this.cpf);
            stmt.setString(6, this.cnh);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.idlocatarios = generatedKeys.getInt(1); // Atualiza o ID do locatário
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Locatario> getAll() {
        List<Locatario> locatarios = new ArrayList<>();
        String sql = "SELECT * FROM locatarios";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Locatario locatario = new Locatario();
                locatario.setIdlocatarios(rs.getInt("idlocatarios"));
                locatario.setNome(rs.getString("nome"));
                locatario.setDataNasc(rs.getString("data_nasc"));
                locatario.setEndereco(rs.getString("endereço"));
                locatario.setTelefone(rs.getString("telefone"));
                locatario.setCpf(rs.getString("cpf"));
                locatario.setCnh(rs.getString("cnh"));
                locatarios.add(locatario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locatarios;
    }

    public void update() {
        String sql = "UPDATE locatarios SET nome = ?, data_nasc = ?, endereço = ?, telefone = ?, cpf = ?, cnh = ? WHERE idlocatarios = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.nome);
            stmt.setString(2, this.dataNasc);
            stmt.setString(3, this.endereco);
            stmt.setString(4, this.telefone);
            stmt.setString(5, this.cpf);
            stmt.setString(6, this.cnh);
            stmt.setInt(7, this.idlocatarios);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "DELETE FROM locatarios WHERE idlocatarios = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idlocatarios);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
