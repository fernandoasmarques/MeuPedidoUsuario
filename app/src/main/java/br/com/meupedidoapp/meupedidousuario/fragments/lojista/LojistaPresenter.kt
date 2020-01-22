package br.com.meupedidoapp.meupedidousuario.fragments.lojista

import br.com.meupedidoapp.meupedidousuario.adapters.CategoriaAdapter
import br.com.meupedidoapp.meupedidousuario.entities.Categoria
import br.com.meupedidoapp.meupedidousuario.entities.Tema
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class LojistaPresenter(private val lojistaPresenterOutput: LojistaContracts.LojistaPresenterOutput) :
    LojistaContracts.LojistaPresenterInput{

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun buscarCategorias(uidLojista: String, tema: Tema) {
        val categoria: Query by lazy {
            db.collection("Lojista").document(uidLojista).collection("Cardapio")
                .orderBy("ordem", Query.Direction.ASCENDING)
        }

        lojistaPresenterOutput.mostrarCategorias(
            CategoriaAdapter(
                FirestoreRecyclerOptions.Builder<Categoria>()
                    .setQuery(categoria, Categoria::class.java)
                    .build()
            )
        )
    }
}