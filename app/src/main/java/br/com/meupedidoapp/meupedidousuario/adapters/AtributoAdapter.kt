package br.com.meupedidoapp.meupedidousuario.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.entities.Atributo
import br.com.meupedidoapp.meupedidousuario.fragments.lojista.LojistaSingleton
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.gson.Gson

open class AtributoAdapter(
    options: FirestoreRecyclerOptions<Any>,
    private val idCategoria: String?, private val idProduto: String?
) : FirestoreRecyclerAdapter<Any, AtributoAdapter.AtributoHolder>(options), OpcaoSelecionadaListener{

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtributoHolder {
        return AtributoHolder(parent)
    }

    override fun onBindViewHolder(holder: AtributoHolder, position: Int, entity: Any) {
        holder.bind(convertToObject(entity))
        holder.buscarOpcoes(position, convertToObject(entity))
    }

    private fun convertToObject(entity: Any): Atributo {
        return Gson().fromJson(
            Gson().toJsonTree(entity),
            Atributo::class.java
        )
    }

    override fun onOpcaoSelecionada(position: Int) {
        Log.v("Msg", "posicao: $position")
    }

    inner class AtributoHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_atributo,
                parent,
                false
            )
        )

        private val nomeAtributo: TextView =
            itemView.findViewById<TextView>(R.id.itemAtributo_nomeAtributo)

        private val recyclerOpcoes: RecyclerView =
            itemView.findViewById<RecyclerView>(R.id.itemAtributo_recyclerOpcoes).apply {
                this.layoutManager =
                    LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                this.setRecycledViewPool(RecyclerView.RecycledViewPool())
            }


        fun bind(atributo: Atributo) {
            nomeAtributo.text = atributo.nome
            //recyclerOpcoes.adapter = OpcaoAdapter(atributo)
        }

        fun buscarOpcoes(pos: Int, atributo: Atributo) {

            val opcoesQuery: Query by lazy {
                db.collection("Lojista")
                    .document(LojistaSingleton.objetoLojista.uid)
                    .collection("Cardapio")
                    .document(idCategoria!!)
                    .collection("Produto")
                    .document(idProduto!!)
                    .collection("Atributos")
                    .document(snapshots.getSnapshot(pos).id)
                    .collection("opcoes")
                    .whereEqualTo("opcaoAtivo", true)
            }

            bindRecyclerOpcoes(
                OpcaoAdapter(
                    FirestoreRecyclerOptions.Builder<Any>()
                        .setQuery(opcoesQuery, Any::class.java).build()
                    , atributo.tipoOpcao
                )
            )
        }

        private fun bindRecyclerOpcoes(adapter: OpcaoAdapter) {
            recyclerOpcoes.adapter = adapter
            adapter.setOpcaoSelecionadaListener(this@AtributoAdapter)
            adapter.startListening()
        }
    }
}
