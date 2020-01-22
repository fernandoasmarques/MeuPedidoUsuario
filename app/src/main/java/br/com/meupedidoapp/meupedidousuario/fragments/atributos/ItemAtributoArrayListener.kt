package br.com.meupedidoapp.meupedidousuario.fragments.atributos

import android.widget.TextView
import androidx.databinding.ObservableList
import androidx.databinding.ObservableList.OnListChangedCallback
import br.com.meupedidoapp.meupedidousuario.R
import java.math.BigDecimal
import java.text.DecimalFormat

class ItemAtributoArrayListener(
    private val bottomsheet_txtSubtotal: TextView,
    private var qtdeProduto: Int,
    private var valorProduto: BigDecimal
) : OnListChangedCallback<ObservableList<Any>>() {

    override fun onChanged(sender: ObservableList<Any>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemRangeRemoved(
        sender: ObservableList<Any>?,
        positionStart: Int,
        itemCount: Int
    ) {
        realizarCalculo()
    }

    override fun onItemRangeMoved(
        sender: ObservableList<Any>?,
        fromPosition: Int,
        toPosition: Int,
        itemCount: Int
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemRangeInserted(
        sender: ObservableList<Any>?,
        positionStart: Int,
        itemCount: Int
    ) {
        realizarCalculo()
    }

    override fun onItemRangeChanged(
        sender: ObservableList<Any>?,
        positionStart: Int,
        itemCount: Int
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun realizarCalculo() {
        var soma = BigDecimal("0")
        for (itensSelecionados in AtributosPresenter.itensAtributo) {
            soma = soma.add(itensSelecionados.getPrecoTotal())
        }
        AtributosPresenter.subtotal = valorProduto.add(soma).multiply(BigDecimal(qtdeProduto.toString()))
        setSubtotalText(AtributosPresenter.subtotal)
    }

    /*private fun realizarCalculo() {
        var soma = BigDecimal("0")
        for (itensSelecionados in AtributosPresenter.itensAtributo) {
            soma = soma.add(itensSelecionados.getPrecoTotal())
        }
        AtributosPresenter.subtotal.add(soma)
        setSubtotalText(AtributosPresenter.subtotal)
    }*/

    private fun setSubtotalText(valor: BigDecimal) {
        bottomsheet_txtSubtotal.text = bottomsheet_txtSubtotal.context.resources.getString(
            R.string.precoString, DecimalFormat("#,###.00").format(
                valor.setScale(2, BigDecimal.ROUND_HALF_EVEN).toDouble()
            )
        )
    }
}