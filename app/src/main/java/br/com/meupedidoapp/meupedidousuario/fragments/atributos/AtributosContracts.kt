package br.com.meupedidoapp.meupedidousuario.fragments.atributos

import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.meupedidoapp.meupedidousuario.adapters.AtributoAdapter
import com.google.android.material.button.MaterialButton

interface AtributosContracts {

    interface AtributosPresenterOutput {
        fun mostrarAtributos(atributosAdapter: AtributoAdapter)
        fun mostrarNomeProduto(nomeProduto: String)
    }

    interface AtributosPresenterInput {
        fun buscarAtributos()
        fun buscarNomeProduto()
        fun limparItensSelecionados()
        fun atualizarPrecoSubTotal(txtSubtotal: TextView, qtde: Int = 1)
        fun irParaItensPedidoFragment()
    }
}