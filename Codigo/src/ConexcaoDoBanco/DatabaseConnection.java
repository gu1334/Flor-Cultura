package ConexcaoDoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // URL de conexão com o banco de dados SQLite
    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    // Método para conectar ao banco de dados
    public static Connection connect() {
        Connection connection = null;
        try {
            // Registrar o driver JDBC
            Class.forName("org.sqlite.JDBC");

            // Estabelecer a conexão
            connection = DriverManager.getConnection(URL);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return connection;
    }

    // Método para fechar a conexão com o banco de dados
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Teste de conexão
        Connection connection = connect();

        // Exemplo de consulta
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Client");
                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id_client") +
                            ", Nome: " + resultSet.getString("name") +
                            ", Email: " + resultSet.getString("email"));
                }
            } catch (SQLException e) {
                System.err.println("Erro ao executar a consulta: " + e.getMessage());
            }
        }

        // Fechar a conexão
        close(connection);
    }
}
