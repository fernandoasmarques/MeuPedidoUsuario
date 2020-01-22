package br.com.meupedidoapp.meupedidousuario.fragments.feed

import br.com.meupedidoapp.meupedidousuario.adapters.LojistaAdapter

interface FeedContracts {

    /*interface FeedPresenterOutput {
        fun mostrarLojistas(lojistaAdapter: LojistaAdapter)
    }

    interface FeedPresenterInput {
        fun buscarLojistas()

        fun onViewCreated()
        fun onDestroy()
    }

    interface FeedInteractorInput{
        fun visualizarLojas()
    }

    interface FeedInteractorOutput {
        fun mostrarLojas()
    }*/

    interface FeedPresenterOutput {
        fun mostrarLojistas(lojistaAdapter: LojistaAdapter)
    }

    interface FeedPresenterInput {
        fun buscarLojistas()
        fun onViewCreated()
        fun onDestroy()
    }

    interface FeedInteractorInput {
        fun visualizarLojas()
    }

    interface FeedInteractorOutput {
        fun mostrarLojas()
    }


    /*interface FeedInteractorInput{
        fun visualizarLojas()
        fun setInteractorOutput(feedInteractorOutput: FeedInteractorOutput)
    }

    interface FeedInteractorOutput {
        fun mostrarLojas()
    }

    interface FeedPresenterInput {
        fun buscarLojistas()
        fun setPresenterOutput(feedPresenterOutput: FeedPresenterOutput)
    }

    interface FeedPresenterOutput {
        fun mostrarLojistas(lojistaAdapter: LojistaAdapter)
    }*/
}