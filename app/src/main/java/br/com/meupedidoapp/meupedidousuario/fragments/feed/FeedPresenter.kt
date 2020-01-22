package br.com.meupedidoapp.meupedidousuario.fragments.feed

import br.com.meupedidoapp.meupedidousuario.adapters.LojistaAdapter
import br.com.meupedidoapp.meupedidousuario.entities.Lojista
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FeedPresenter(private var feedPresenterOutput: FeedContracts.FeedPresenterOutput) :
    FeedContracts.FeedPresenterInput, FeedContracts.FeedInteractorOutput {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private var feedInteractorInput : FeedContracts.FeedInteractorInput? = FeedInteractor()

    override fun buscarLojistas() {
        val lojaAberta: Query by lazy {
            db.collection("Lojista")
                .orderBy("estaAberto", Query.Direction.DESCENDING)
                .whereEqualTo("usuarioAtivo", true)
        }

        feedPresenterOutput.mostrarLojistas(
            LojistaAdapter(
                FirestoreRecyclerOptions.Builder<Lojista>()
                    .setQuery(lojaAberta, Lojista::class.java)
                    .build()
            )
        )
    }

    override fun mostrarLojas() {

    }

    override fun onViewCreated() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*override fun onViewCreated() {
        feedInteractorInput = FeedInteractor(this)
    }*/
}