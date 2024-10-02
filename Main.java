import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        Locatario locatario = new Locatario();

        // Definindo os valores para o locatário
        locatario.setNome("João Silva");
        locatario.setDataNasc("1990-05-20");
        locatario.setEndereco("Rua das Flores, 123");
        locatario.setTelefone("123456789");
        locatario.setCpf("12345678901");
        locatario.setCnh("1234567890");

        locatario.save();

        System.out.println("Locatário salvo com sucesso!");
    }
}
