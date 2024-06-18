package ConexcaoDoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteData {

    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            System.out.println("Conexão estabelecida com sucesso!");

            deleteAllData(connection);
        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer a conexão: " + e.getMessage());
        }
    }

    private static void deleteAllData(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            // Excluir dados das tabelas filhas
            statement.executeUpdate("DELETE FROM Order_itens");
            statement.executeUpdate("DELETE FROM Itens_Product");

            // Excluir dados das tabelas pai
            statement.executeUpdate("DELETE FROM Product");
            statement.executeUpdate("DELETE FROM Category");
            statement.executeUpdate("DELETE FROM Client");

            System.out.println("Dados excluídos com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir dados: " + e.getMessage());
        }
    }
}
