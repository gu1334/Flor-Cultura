package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Produtos extends Stock implements Category {
    private int id_product;
    private String nome;
    private double preco;
    private String categoria;
    private int quantidadeDisponivel;

    public Produtos(int id_product, String nome, double preco, String categoria, int quantidadeDisponivel) {
        this.id_product = id_product;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public void atualizarEstoque(Connection connection) {
        String sql = "UPDATE Stock SET amount = ? WHERE id_Product = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quantidadeDisponivel);
            statement.setInt(2, id_product);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar estoque: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void carregarEstoque(Connection connection) {
        String sql = "SELECT amount FROM Stock WHERE id_Product = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_product);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    quantidadeDisponivel = resultSet.getInt("amount");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar estoque: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int getcod_category(int cod) {
        return 0;
    }
}
