package Main;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CarrinhoDeCompras implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Produtos, Integer> itens;

    public CarrinhoDeCompras() {
        this.itens = new HashMap<>();
    }

    public void adicionarProduto(Produtos produto) {
        int quantidadeAtual = itens.getOrDefault(produto, 0);
        itens.put(produto, quantidadeAtual + 1);
    }

    public void removerProduto(Produtos produto) {
        itens.remove(produto);
    }

    public void aumentarQuantidade(Produtos produto) {
        int quantidadeAtual = itens.getOrDefault(produto, 0);
        itens.put(produto, quantidadeAtual + 1);
    }

    public void diminuirQuantidade(Produtos produto) {
        int quantidadeAtual = itens.getOrDefault(produto, 0);
        if (quantidadeAtual > 0) {
            itens.put(produto, quantidadeAtual - 1);
            if (quantidadeAtual - 1 == 0) {
                itens.remove(produto);
            }
        }
    }

    public void removerTodos(Produtos produto) {
        itens.remove(produto);
    }

    public int getQuantidade(Produtos produto) {
        return itens.getOrDefault(produto, 0);
    }

    public Map<Produtos, Integer> getItens() {
        return new HashMap<>(itens);
    }

    public double calcularValorTotal() {
        double valorTotal = 0.0;
        for (Map.Entry<Produtos, Integer> entry : itens.entrySet()) {
            Produtos produto = entry.getKey();
            int quantidade = entry.getValue();
            valorTotal += produto.getPreco() * quantidade;
        }
        return valorTotal;
    }

    public void limparCarrinho() {
        itens.clear();
    }

    public void finalizarCompra(FormaDePagamento formaDePagamento) {
        double valorTotal = calcularValorTotal();
        formaDePagamento.processarPagamento(valorTotal);
    }
}
