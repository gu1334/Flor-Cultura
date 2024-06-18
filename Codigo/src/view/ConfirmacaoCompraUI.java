package view;

import javax.swing.*;
import java.awt.*;

public class ConfirmacaoCompraUI extends JFrame {

    public ConfirmacaoCompraUI(String formaPagamento) {
        setTitle("Confirmação de Compra");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fechar apenas esta janela

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        JLabel label = new JLabel("Compra finalizada! Pagamento realizado com " + formaPagamento);
        panel.add(label);

        getContentPane().add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
