package view;

import Main.CarrinhoDeCompras;
import Main.Produtos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CarrinhoDeComprasUI extends JFrame {
    private JPanel produtosPanel;
    private CarrinhoDeCompras carrinho;

    private JLabel valorTotalLabel;

    public CarrinhoDeComprasUI(List<Produtos> carrinhoProdutos) {
        setTitle("Carrinho de Compras");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        carrinho = new CarrinhoDeCompras();

        produtosPanel = new JPanel();
        produtosPanel.setLayout(new BoxLayout(produtosPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(produtosPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        valorTotalLabel = new JLabel("Valor Total: R$ 0.00");
        valorTotalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valorTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        produtosPanel.add(valorTotalLabel);
        produtosPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        for (Produtos produto : carrinhoProdutos) {
            JPanel produtoPanel = criarPainelProduto(produto);
            produtosPanel.add(produtoPanel);
            produtosPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finalizarCompraButton.addActionListener(this::finalizarCompra);
        produtosPanel.add(finalizarCompraButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel criarPainelProduto(Produtos produto) {
        JPanel produtoPanel = new JPanel();
        produtoPanel.setLayout(new BorderLayout());
        produtoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel nomeLabel = new JLabel("Nome: " + produto.getNome());
        JLabel precoLabel = new JLabel("Preço: R$ " + produto.getPreco());
        JLabel categoriaLabel = new JLabel("Categoria: " + produto.getCategoria());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1));
        infoPanel.add(nomeLabel);
        infoPanel.add(precoLabel);
        infoPanel.add(categoriaLabel);

        produtoPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel quantidadePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel quantidadeLabel = new JLabel("Quantidade:");
        JTextField quantidadeField = new JTextField(3);
        quantidadeField.setEditable(false);
        quantidadeField.setText(String.valueOf(carrinho.getQuantidade(produto)));

        JButton aumentarButton = new JButton("+");
        aumentarButton.addActionListener(e -> {
            carrinho.adicionarProduto(produto);
            quantidadeField.setText(String.valueOf(carrinho.getQuantidade(produto)));
            atualizarValorTotal();
        });

        JButton diminuirButton = new JButton("-");
        diminuirButton.addActionListener(e -> {
            carrinho.removerProduto(produto);
            quantidadeField.setText(String.valueOf(carrinho.getQuantidade(produto)));
            atualizarValorTotal();
            if (carrinho.getQuantidade(produto) == 0) {
                produtosPanel.remove(produtoPanel);
                produtosPanel.revalidate();
                produtosPanel.repaint();
            }
        });

        JButton removerButton = new JButton("Remover");
        removerButton.addActionListener(e -> {
            carrinho.removerTodos(produto);
            produtosPanel.remove(produtoPanel);
            produtosPanel.revalidate();
            produtosPanel.repaint();
            atualizarValorTotal();
        });

        quantidadePanel.add(quantidadeLabel);
        quantidadePanel.add(quantidadeField);
        quantidadePanel.add(aumentarButton);
        quantidadePanel.add(diminuirButton);
        quantidadePanel.add(removerButton);

        produtoPanel.add(quantidadePanel, BorderLayout.SOUTH);

        return produtoPanel;
    }

    private void atualizarValorTotal() {
        double valorTotal = carrinho.calcularValorTotal();
        valorTotalLabel.setText(String.format("Valor Total: R$ %.2f", valorTotal));
    }

    private void finalizarCompra(ActionEvent e) {
        double valorTotal = carrinho.calcularValorTotal();
        JOptionPane.showMessageDialog(this, String.format("Compra finalizada! Valor total: R$ %.2f", valorTotal));
        carrinho.limparCarrinho();
        produtosPanel.removeAll();
        produtosPanel.revalidate();
        produtosPanel.repaint();
        atualizarValorTotal();

        new FinalizarPagamentoUI(carrinho).setVisible(true);

        dispose();
    }

    public void adicionarProduto(Produtos produto) {
        carrinho.adicionarProduto(produto);
        JPanel produtoPanel = criarPainelProduto(produto);
        produtosPanel.add(produtoPanel);
        produtosPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        produtosPanel.revalidate();
        produtosPanel.repaint();
        atualizarValorTotal();
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Produtos p1 = new Produtos(1, "Rosa", 10.0, "Flores", 5);
            Produtos p2 = new Produtos(2, "Orquídea", 20.0, "Flores", 3);

            CarrinhoDeComprasUI frame = new CarrinhoDeComprasUI(List.of(p1, p2));
        });
    }
}
