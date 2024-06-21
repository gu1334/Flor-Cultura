package view;

import Main.CarrinhoDeCompras;
import Main.FormaDePagamento;
import Main.Dinheiro;
import Main.Pix;
import Main.CartaoDeCredito;
import Main.Produtos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FinalizarPagamentoUI extends JFrame {

    private CarrinhoDeCompras carrinho;
    private JComboBox<String> formaPagamentoComboBox;
    private JLabel valorTotalLabel;

    public FinalizarPagamentoUI(CarrinhoDeCompras carrinho) {
        this.carrinho = carrinho;

        setTitle("Floricultura - Finalizar Pagamento");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("img/flor.png");
        setIconImage(icon.getImage());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        getContentPane().add(mainPanel);

        // Adiciona o ComboBox de formas de pagamento
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        String[] formasPagamento = {"Dinheiro", "Pix", "Cartão de Crédito"};
        formaPagamentoComboBox = new JComboBox<>(formasPagamento);
        formaPagamentoComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        formaPagamentoComboBox.setMinimumSize(new Dimension(150, formaPagamentoComboBox.getPreferredSize().height));
        mainPanel.add(formaPagamentoComboBox);

        // Adiciona o label do valor total
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        valorTotalLabel = new JLabel();
        valorTotalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        valorTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(valorTotalLabel);

        // Adiciona o botão de finalizar compra
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finalizarCompraButton.addActionListener(this::finalizarCompra);
        mainPanel.add(finalizarCompraButton);

        setLocationRelativeTo(null);
        atualizarValorTotal(); // Atualiza o valor total inicialmente
    }

    private void atualizarValorTotal() {
        double valorTotal = carrinho.calcularValorTotal();
        valorTotalLabel.setText("Valor Total: R$ " + String.format("%.2f", valorTotal));
        getContentPane().revalidate(); // Revalida o conteúdo para garantir que seja exibido corretamente
    }

    private void finalizarCompra(ActionEvent e) {
        String formaSelecionada = (String) formaPagamentoComboBox.getSelectedItem();
        FormaDePagamento formaDePagamento = null;

        switch (formaSelecionada) {
            case "Dinheiro":
                formaDePagamento = new Dinheiro();
                break;
            case "Pix":
                formaDePagamento = new Pix();
                break;
            case "Cartão de Crédito":
                formaDePagamento = new CartaoDeCredito("1234567890123456", "Fulano de Tal", "12/25", "123");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Selecione uma forma de pagamento válida!");
                return;
        }

        carrinho.finalizarCompra(formaDePagamento);
        atualizarValorTotal(); // Atualiza o valor total após finalizar a compra

        if (formaDePagamento instanceof Pix) {
            String chavePix = ((Pix) formaDePagamento).getChavePix();
            new ConfirmacaoCompraUI("Pix - Chave: " + chavePix).setVisible(true);
        } else {
            new ConfirmacaoCompraUI(formaSelecionada).setVisible(true);
        }

        dispose();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
            // Adicione produtos ao carrinho para teste
            carrinho.adicionarProduto(new Produtos(1, "Rosa", 10.0, "Flores", 5));
            carrinho.adicionarProduto(new Produtos(2, "Orquídea", 20.0, "Flores", 3));

            FinalizarPagamentoUI frame = new FinalizarPagamentoUI(carrinho);
            frame.setVisible(true);
        });
    }
}
