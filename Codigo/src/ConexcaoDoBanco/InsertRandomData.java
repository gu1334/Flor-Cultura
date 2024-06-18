package ConexcaoDoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertRandomData {

    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            System.out.println("Conexão estabelecida com sucesso!");

            // Habilita o modo de transação
            connection.setAutoCommit(false);

            insertRandomData(connection);

            // Confirma a transação
            connection.commit();
            System.out.println("Dados inseridos com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer a conexão ou inserir dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertRandomData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Inserir dados aleatórios na tabela Client
            statement.executeUpdate("INSERT INTO Client (name, phone, cpf, email, password, date_birth, address) " +
                    "VALUES ('João Silva', '123456789', '12345678901', 'joao@example.com', 'senha123', '1990-05-20', 'Rua A, 123')");

            // Inserir dados aleatórios na tabela Category
            statement.executeUpdate("INSERT INTO Category (name) VALUES ('Flor Rosa')");
            statement.executeUpdate("INSERT INTO Category (name) VALUES ('Flor Azul')");
            statement.executeUpdate("INSERT INTO Category (name) VALUES ('Flor Amarela')");

            // Inserir dados aleatórios na tabela Product com quantidade inicial em estoque
            statement.executeUpdate("INSERT INTO Product (name, price, id_Category, quantity) " +
                    "VALUES ('Rosa', 15.00, 1, 50)");
            statement.executeUpdate("INSERT INTO Product (name, price, id_Category, quantity) " +
                    "VALUES ('10 Rosas', 200.00, 1, 30)");
            statement.executeUpdate("INSERT INTO Product (name, price, id_Category, quantity) " +
                    "VALUES ('Girasol', 20.00, 2, 40)");
            statement.executeUpdate("INSERT INTO Product (name, price, id_Category, quantity) " +
                    "VALUES ('Urso com Coração', 35.00, 3, 20)");

            // Inserir dados aleatórios na tabela Itens_Product
            statement.executeUpdate("INSERT INTO Itens_Product (id_Product, amount) VALUES (1, 10)");
            statement.executeUpdate("INSERT INTO Itens_Product (id_Product, amount) VALUES (2, 5)");
            statement.executeUpdate("INSERT INTO Itens_Product (id_Product, amount) VALUES (3, 20)");
            statement.executeUpdate("INSERT INTO Itens_Product (id_Product, amount) VALUES (4, 15)");

            // Inserir dados aleatórios na tabela Order_itens
            statement.executeUpdate("INSERT INTO Order_itens (id_Client, id_Itens_Product, payment, order_date, total_value) " +
                    "VALUES (1, 1, 'Cartão de Crédito', '2024-06-01', 1500.00)");
            statement.executeUpdate("INSERT INTO Order_itens (id_Client, id_Itens_Product, payment, order_date, total_value) " +
                    "VALUES (1, 2, 'Boleto Bancário', '2024-06-02', 2500.00)");
            statement.executeUpdate("INSERT INTO Order_itens (id_Client, id_Itens_Product, payment, order_date, total_value) " +
                    "VALUES (1, 3, 'Pix', '2024-06-03', 1000.00)");
            statement.executeUpdate("INSERT INTO Order_itens (id_Client, id_Itens_Product, payment, order_date, total_value) " +
                    "VALUES (1, 4, 'Cartão de Débito', '2024-06-04', 525.00)");

            // Inserir dados aleatórios na tabela Stock
            statement.executeUpdate("INSERT INTO Stock (id_Product, amount) VALUES (1, 50)");
            statement.executeUpdate("INSERT INTO Stock (id_Product, amount) VALUES (2, 30)");
            statement.executeUpdate("INSERT INTO Stock (id_Product, amount) VALUES (3, 40)");
            statement.executeUpdate("INSERT INTO Stock (id_Product, amount) VALUES (4, 20)");
        }
    }
}
