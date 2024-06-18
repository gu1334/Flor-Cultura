package view;

import Main.Login;
import Main.UsuarioPermitido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {

    public static void main(String[] args) {
        UsuarioPermitido user = new UsuarioPermitido("Newton", "Newton123");
        Login login = new Login(user);

        JFrame frame = new JFrame("Floricultura");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        String iconPath = "img/flor.png";
        ImageIcon imgLOGO = new ImageIcon(iconPath);
        Image scaledImage = imgLOGO.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel imageLabel = new JLabel(scaledIcon);
        logoPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Flor&Cultura", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.MAGENTA);
        titleLabel.setBackground(Color.WHITE);

        logoPanel.add(imageLabel);
        logoPanel.add(titleLabel);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        frame.add(logoPanel, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 1;
        JLabel userLabel = new JLabel("Usuário:", SwingConstants.CENTER);
        frame.add(userLabel, constraints);

        constraints.gridx = 1;
        JTextField userText = new JTextField(20);
        frame.add(userText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel passwordLabel = new JLabel("Senha:", SwingConstants.CENTER);
        frame.add(passwordLabel, constraints);

        constraints.gridx = 1;
        JPasswordField passwordText = new JPasswordField(20);
        frame.add(passwordText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        frame.add(loginButton, constraints);

        constraints.gridy = 4;
        JLabel messageLabel = new JLabel("");
        frame.add(messageLabel, constraints);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consoleUsuario = userText.getText();
                String consoleSenha = new String(passwordText.getPassword());

                if (login.ValidaDados(consoleUsuario, consoleSenha)) {
                    MostrarProdutosUI mostrarProdutosUI = new MostrarProdutosUI();
                    mostrarProdutosUI.setVisible(true);
                    frame.dispose();
                } else {
                    messageLabel.setText("Usuário ou senha incorreto!");
                }
            }
        });

        frame.setIconImage(imgLOGO.getImage());
        frame.setVisible(true);
    }
}
