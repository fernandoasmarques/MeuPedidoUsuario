package br.com.meupedidoapp.meupedidousuario.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.entities.Categoria
import br.com.meupedidoapp.meupedidousuario.fragments.lojista.LojistaSingleton
import br.com.meupedidoapp.meupedidousuario.utils.GridSpacingItemDecoration
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

open class CategoriaAdapter(options: FirestoreRecyclerOptions<Categoria>) :
    FirestoreRecyclerAdapter<Categoria, CategoriaAdapter.CategoriaHolder>(options) {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        return CategoriaHolder(parent)
    }

    override fun onBindViewHolder(holder: CategoriaHolder, position: Int, entity: Categoria) {
        holder.procurarProdutos(snapshots.getSnapshot(position).id)
    }

    override fun getItemCount(): Int {
        return snapshots.size
    }

    inner class CategoriaHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val recyclerProdutos: RecyclerView =
            itemView.findViewById<RecyclerView>(R.id.itemCategoria_recyclerProdutos).apply {
                /*this.layoutManager =
                    LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)*/
                //this.layoutManager = GridLayoutManager(this.context, 2)
                this.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                this.addItemDecoration(GridSpacingItemDecoration(2, 20, true, 0))
                //this.setHasFixedSize(true)
                this.itemAnimator = DefaultItemAnimator()
            }

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_categoria,
                parent,
                false
            )
        )

        fun procurarProdutos(idCategoria: String) {
            val produtosQuery: Query by lazy {
                db.collection("Lojista").document(LojistaSingleton.objetoLojista.uid)
                    .collection("Cardapio")
                    .document(idCategoria)
                    .collection("Produto")
                    .whereEqualTo("produtoAtivo", true)
                    .whereEqualTo("visivel", true)
                    .orderBy("nome")
            }

            bindRecyclerProduto(
                ProdutoAdapter(
                    FirestoreRecyclerOptions.Builder<Any>()
                        .setQuery(produtosQuery, Any::class.java)
                        .build(), idCategoria)
            )
        }

        private fun bindRecyclerProduto(produtoAdapter: ProdutoAdapter) {
            recyclerProdutos.adapter = produtoAdapter
            produtoAdapter.startListening()
        }
    }
}