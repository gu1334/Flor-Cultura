package view;

import Main.Produtos;

import javax.swing.*;
import java.awt.*;

public class ProdutoPanel extends JPanel {
    private JLabel imagemLabel;
    private JLabel nomeLabel;
    private JLabel categoriaLabel;
    private JLabel precoLabel;
    private JButton comprarButton;

    public ProdutoPanel(Produtos produto, ImageIcon novaImagem) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        imagemLabel = new JLabel(novaImagem);
        imagemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nomeLabel = new JLabel(produto.getNome());
        nomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        categoriaLabel = new JLabel("Categoria: " + produto.getCategoria());
        categoriaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        precoLabel = new JLabel("R$" + produto.getPreco());
        precoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        comprarButton = new JButton("Comprar");
        comprarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        comprarButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Você comprou: " + produto.getNome(), "Compra Efetuada", JOptionPane.INFORMATION_MESSAGE);
        });

        add(imagemLabel);
        add(nomeLabel);
        add(precoLabel);
        add(categoriaLabel);
        add(comprarButton);

        setPreferredSize(new Dimension(200, 300));
    }
}
