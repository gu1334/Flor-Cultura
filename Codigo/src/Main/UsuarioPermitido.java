package Main;

public class UsuarioPermitido {

    private String usuario;
    private String senha;

    public UsuarioPermitido(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }
}
