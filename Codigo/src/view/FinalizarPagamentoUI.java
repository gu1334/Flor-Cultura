package view;

import Main.CarrinhoDeCompras;
import Main.FormaDePagamento;
import Main.Dinheiro;
import Main.Pix;
import Main.CartaoDeCredito;

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

        valorTotalLabel = new JLabel("Valor Total: R$ " + carrinho.calcularValorTotal());
        valorTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(valorTotalLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        String[] formasPagamento = {"Dinheiro", "Pix", "Cartão de Crédito"};
        formaPagamentoComboBox = new JComboBox<>(formasPagamento);
        formaPagamentoComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        formaPagamentoComboBox.setMinimumSize(new Dimension(150, formaPagamentoComboBox.getPreferredSize().height));

        mainPanel.add(formaPagamentoComboBox);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finalizarCompraButton.addActionListener(this::finalizarCompra);
        mainPanel.add(finalizarCompraButton);

        setLocationRelativeTo(null);
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
            FinalizarPagamentoUI frame = new FinalizarPagamentoUI(carrinho);
            frame.setVisible(true);
        });
    }
}
