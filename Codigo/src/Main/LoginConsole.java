package Main;

import view.MostrarProdutosUI;

import java.util.Scanner;

public class LoginConsole {

    public static void main(String[] args) {
        UsuarioPermitido user = new UsuarioPermitido("Newton", "Newton123");
        Login login = new Login(user);

        Scanner ler = new Scanner(System.in);

        System.out.print("Usuário: ");
        String consoleUsuario = ler.nextLine();

        System.out.print("Senha: ");
        String consoleSenha = ler.nextLine();

        if (login.ValidaDados(consoleUsuario, consoleSenha)) {
            MostrarProdutosUI mostrarProdutosUI = new MostrarProdutosUI();
        } else {
            System.out.println("Usuário ou senha incorreto!");
        }
    }
}
