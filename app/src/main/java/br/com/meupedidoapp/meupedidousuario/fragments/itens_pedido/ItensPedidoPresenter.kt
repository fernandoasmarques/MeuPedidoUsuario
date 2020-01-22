package br.com.meupedidoapp.meupedidousuario.fragments.itens_pedido

import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableArrayList
import br.com.meupedidoapp.meupedidousuario.entities.ItensPedido

class ItensPedidoPresenter(
    private val itensPedidoPresenterOutput: ItensPedidoContracts.ItensPedidoPresenterOutput
) : ItensPedidoContracts.ItensPedidoPresenterInput {

    companion object{
        val itensPedido by lazy { ObservableArrayList<ItensPedido>() }
    }

    override fun setarValores() {

    }
}