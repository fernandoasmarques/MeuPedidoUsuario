package br.com.meupedidoapp.meupedidousuario.fragments.itens_pedido

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.meupedidoapp.meupedidousuario.R

class ItensPedidoFragment : Fragment(), ItensPedidoContracts.ItensPedidoPresenterOutput{

    private lateinit var itensPedidoPresenterInput : ItensPedidoContracts.ItensPedidoPresenterInput

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itensPedidoPresenterInput = ItensPedidoPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_itens_pedido, container, false)
    }
}
