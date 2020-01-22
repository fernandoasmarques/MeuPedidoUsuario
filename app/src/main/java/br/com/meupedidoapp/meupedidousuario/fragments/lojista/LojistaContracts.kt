package br.com.meupedidoapp.meupedidousuario.fragments.lojista

import br.com.meupedidoapp.meupedidousuario.adapters.CategoriaAdapter
import br.com.meupedidoapp.meupedidousuario.entities.Tema

interface LojistaContracts {

    interface LojistaPresenterInput {
        fun buscarCategorias(uidLojista: String, tema: Tema)
    }

    interface LojistaPresenterOutput {
        fun mostrarCategorias(categoriaAdapter: CategoriaAdapter)
    }
}