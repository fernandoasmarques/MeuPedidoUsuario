package br.com.meupedidoapp.meupedidousuario.entities

import java.math.BigDecimal

class ItensAtributo(opcao: Opcao, qtde: Int){
    private var opcao: Opcao? = opcao
    private var qtde: Int = qtde

    fun getPrecoTotal(): BigDecimal {
        return BigDecimal(qtde).multiply(opcao?.preco)
    }
}