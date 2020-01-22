package br.com.meupedidoapp.meupedidousuario.entities;

import java.math.BigDecimal;

public class Tamanho {
    private String tamanho;
    private BigDecimal preco;
    private boolean ativo;

    public Tamanho() {
    }

    public String getTamanho() {
        return tamanho;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
