package Main;

public class Login{
    private static UsuarioPermitido user;

    public Login(UsuarioPermitido user){
        this.user = user;
    }

    public static boolean ValidaDados(String Usuario, String Senha){

        return user.getUsuario().equals(Usuario) && user.getSenha().equals(Senha);
    }
}
