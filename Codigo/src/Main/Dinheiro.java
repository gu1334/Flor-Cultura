package Main;

public class Dinheiro extends FormaDePagamento {
    @Override
    public void processarPagamento(double valor) {
        System.out.println("Processando pagamento de R$" + valor + " com Dinheiro.");
        imprimirRecibo(valor);
    }
}
