package view;

import Main.Produtos;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class MostrarProdutosUI extends JFrame {
    private JPanel panel;
    private List<Produtos> produtos;
    private List<Produtos> carrinho;
    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    public MostrarProdutosUI() {
        setTitle("Floricultura");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String iconPath = "img/flor.png";
        File iconFile = new File(iconPath);
        if (iconFile.exists()) {
            ImageIcon icon = new ImageIcon(iconPath);
            setIconImage(icon.getImage());
        } else {
            System.err.println("Erro: Ícone não encontrado em " + iconPath);
        }

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(0, 3, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panel);
        getContentPane().add(scrollPane);

        mostrarProdutosCadastrados();
        adicionarBotaoCarrinho();

        setLocationRelativeTo(null);

        carrinho = new ArrayList<>();
    }

    private void adicionarProdutoAoCarrinho(Produtos produto, Connection connection) {
        produto.carregarEstoque(connection);

        if (produto.getQuantidadeDisponivel() > 0) {
            carrinho.add(produto);
            produto.setQuantidadeDisponivel(produto.getQuantidadeDisponivel() - 1);
            produto.atualizarEstoque(connection);
            JOptionPane.showMessageDialog(this, "Produto adicionado ao carrinho: " + produto.getNome(), "Produto Adicionado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Produto \"" + produto.getNome() + "\" está fora de estoque.", "Sem Estoque", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarProdutosCadastrados() {
        produtos = carregarProdutosDoBancoDeDados();

        for (Produtos produto : produtos) {
            ImageIcon imagem = carregarImagemDoProduto(produto);

            JPanel produtoPanel = new JPanel(new BorderLayout());
            produtoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JButton comprarButton = new JButton("Comprar");
            comprarButton.addActionListener(e -> adicionarProdutoAoCarrinho(produto, getConnection()));
            produtoPanel.add(comprarButton, BorderLayout.SOUTH);

            JLabel imagemLabel = new JLabel(imagem);
            produtoPanel.add(imagemLabel, BorderLayout.NORTH);

            JPanel infoPanel = new JPanel(new GridLayout(3, 1));
            infoPanel.add(new JLabel(produto.getNome()));
            infoPanel.add(new JLabel("Categoria: " + produto.getCategoria()));
            infoPanel.add(new JLabel("R$ " + produto.getPreco()));
            produtoPanel.add(infoPanel, BorderLayout.CENTER);

            panel.add(produtoPanel);
        }

        panel.revalidate();
        panel.repaint();
    }


    private ImageIcon carregarImagemDoProduto(Produtos produto) {
        String nomeImagem = "img/" + produto.getNome().toLowerCase().replace(" ", "_") + ".png";
        ImageIcon imagem = new ImageIcon(nomeImagem);

        Image image = imagem.getImage();
        Image novaImagem = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);

        if (imagem.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Erro ao carregar imagem: " + nomeImagem);
            imagem = new ImageIcon("img/default.png");
            novaImagem = imagem.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        }

        return new ImageIcon(novaImagem);
    }

    private List<Produtos> carregarProdutosDoBancoDeDados() {
        List<Produtos> produtos = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = "SELECT P.id_Product, P.name AS ProductName, " +
                    "P.price AS Price, " +
                    "C.name AS CategoryName, " +
                    "P.quantity AS Quantity " +
                    "FROM Product AS P " +
                    "INNER JOIN Category AS C ON P.id_Category = C.id_Category";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_Product");
                    String nome = resultSet.getString("ProductName");
                    double preco = resultSet.getDouble("Price");
                    String categoria = resultSet.getString("CategoryName");
                    int quantidadeDisponivel = resultSet.getInt("Quantity");
                    Produtos produto = new Produtos(id, nome, preco, categoria, quantidadeDisponivel);
                    produtos.add(produto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar produtos do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        return produtos;
    }


    private Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Erro ao obter conexão: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void abrirTelaCarrinho() {
        CarrinhoDeComprasUI carrinhoUI = new CarrinhoDeComprasUI(carrinho);
        carrinhoUI.setVisible(true);
    }

    private void adicionarBotaoCarrinho() {
        JButton carrinhoButton = new JButton();
        ImageIcon icon = new ImageIcon("img/carrinho.png");
        Image image = icon.getImage();
        Image novaImagem = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon novoIcone = new ImageIcon(novaImagem);
        carrinhoButton.setIcon(novoIcone);
        carrinhoButton.addActionListener(e -> abrirTelaCarrinho());
        getContentPane().add(carrinhoButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MostrarProdutosUI frame = new MostrarProdutosUI();
            frame.setVisible(true);
        });
    }

}
