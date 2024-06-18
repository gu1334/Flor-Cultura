package Main;

public abstract class FormaDePagamento {
    public abstract void processarPagamento(double valor);

    public void imprimirRecibo(double valor) {
        System.out.println("Recibo: Pagamento de R$" + valor + " processado.");
    }
}
