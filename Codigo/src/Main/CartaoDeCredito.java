package Main;

public class CartaoDeCredito extends FormaDePagamento {
    private String numeroCartao;
    private String nomeTitular;
    private String validade;
    private String codigoSeguranca;

    public CartaoDeCredito(String numeroCartao, String nomeTitular, String validade, String codigoSeguranca) {
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.validade = validade;
        this.codigoSeguranca = codigoSeguranca;
    }

    @Override
    public void processarPagamento(double valor) {
        System.out.println("Processando pagamento de R$" + valor + " com Cartão de Crédito.");
        imprimirRecibo(valor);
    }

}
