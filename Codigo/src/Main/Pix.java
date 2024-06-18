package Main;

import java.util.Random;

public class Pix extends FormaDePagamento {
    private String chavePix;

    public Pix() {
        this.chavePix = gerarChavePixAleatoria();
    }

    private String gerarChavePixAleatoria() {
        Random random = new Random();
        long numero = random.nextLong();
        if (numero < 0) {
            numero = -numero;
        }
        String chavePix = String.format("%010d", numero);
        return chavePix;
    }

    public String getChavePix() {
        return chavePix;
    }

    @Override
    public void processarPagamento(double valor) {
        System.out.println("Processando pagamento de R$" + valor + " com Pix. Chave Pix: " + chavePix);
        imprimirRecibo(valor);
    }
}
