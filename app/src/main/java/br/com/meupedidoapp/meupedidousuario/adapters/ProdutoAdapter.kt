package br.com.meupedidoapp.meupedidousuario.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.entities.*
import br.com.meupedidoapp.meupedidousuario.fragments.lojista.LojistaSingleton
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.gson.Gson
import java.util.ArrayList

open class ProdutoAdapter(
    options: FirestoreRecyclerOptions<Any>,
    private val idCategoria: String
) : FirestoreRecyclerAdapter<Any, RecyclerView.ViewHolder>(options) {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TipoProdutoEnum.UN.ordinal -> ProdutoUnHolder(parent)
            TipoProdutoEnum.TAM.ordinal -> ProdutoTamHolder(parent)
            else -> ProdutoBebHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, entity: Any) {

        when (getItemViewType(position)) {
            TipoProdutoEnum.UN.ordinal -> (holder as ProdutoUnHolder).bind(
                Gson().fromJson(
                    Gson().toJsonTree(entity),
                    ProdutoUnidade::class.java
                ), snapshots.getSnapshot(position).id
            )

            TipoProdutoEnum.TAM.ordinal -> (holder as ProdutoTamHolder).bind(
                Gson().fromJson(
                    Gson().toJsonTree(entity),
                    ProdutoTamanho::class.java
                ), snapshots.getSnapshot(position).id
            )

            TipoProdutoEnum.BEB.ordinal -> {
                (holder as ProdutoBebHolder).bind(
                    Gson().fromJson(
                        Gson().toJsonTree(entity),
                        ProdutoBebida::class.java
                    ), snapshots.getSnapshot(position).id
                )

                holder.procurarTamanhos(position)

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (snapshots.getSnapshot(position)["tipoProduto"]) {
            TipoProdutoEnum.UN.name -> TipoProdutoEnum.UN.ordinal
            TipoProdutoEnum.TAM.name -> TipoProdutoEnum.TAM.ordinal
            else -> TipoProdutoEnum.BEB.ordinal
        }
    }

    inner class ProdutoUnHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtProdutoNome =
            itemView.findViewById<TextView>(R.id.itemProdutoUn_txtProdutoNome)
        private val txtProdutoIngredientes =
            itemView.findViewById<TextView>(R.id.itemProdutoUn_txtProdutoIngredientes)
        private val imgImagemProduto =
            itemView.findViewById<ImageView>(R.id.itemProdutoUn_imagemProduto)
        private val cardViewProdutoUn =
            itemView.findViewById<MaterialCardView>(R.id.itemProdutoUn_cardViewProdutoUn)


        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_produto_un,
                parent,
                false
            )
        )

        fun bind(produtoUn: ProdutoUnidade, idProduto: String) {
            txtProdutoNome.text = produtoUn.nome

            val ingredientList = ArrayList<String>().apply {
                this.addAll(produtoUn.ingredientes!!)
            }

            txtProdutoIngredientes.text = when {
                ingredientList.size > 1 -> {
                    ingredientList.removeAt(ingredientList.size - 1)
                    ingredientList.joinToString(", ") + " e " + produtoUn.ingredientes!![produtoUn.ingredientes.size - 1] + "."
                }
                ingredientList.size == 1 -> produtoUn.ingredientes!![0] + "."
                ingredientList.size == 0 -> ""
                else -> ""
            }

            cardViewProdutoUn.setOnClickListener {
                irParaAtributoFragment(itemView, produtoUn, idProduto)
            }
        }
    }

    inner class ProdutoTamHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtProdutoNome =
            itemView.findViewById<TextView>(R.id.itemProdutoTam_txtProdutoNome)
        //private val txtProdutoIngredientes = itemView.findViewById<TextView>(R.id.itemProduto_txtProdutoIngredientes)
        private val imgImagemProduto =
            itemView.findViewById<ImageView>(R.id.itemProdutoTam_imagemProduto)

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_produto_tam,
                parent,
                false
            )
        )

        fun bind(produtoTam: ProdutoTamanho, idProduto: String) {
            txtProdutoNome.text = produtoTam.nome

            Glide.with(this.imgImagemProduto.context)
                .load(produtoTam.imagem)
                .into(this.imgImagemProduto)
        }
    }

    // verificar se essa classe atende a apenas refrigerantes
    inner class ProdutoBebHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cardBackground =
            itemView.findViewById<MaterialCardView>(R.id.itemProdutoBebRefrigerante_cardBackground)
        private val imgLogo =
            itemView.findViewById<ImageView>(R.id.itemProdutoBebRefrigerante_imgLogo)
        private val produtoNome =
            itemView.findViewById<TextView>(R.id.itemProdutoBebRefrigerante_txtProdutoNome)
        private val tamanhoRecyclerView =
            itemView.findViewById<RecyclerView>(R.id.itemProdutoBebRefrigerante_recyclerView)
                .apply {
                    //this.layoutManager = GridLayoutManager(itemView.context, 2)
                    this.layoutManager =
                        LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                    this.addItemDecoration(
                        DividerItemDecoration(
                            this.context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    this.setRecycledViewPool(RecyclerView.RecycledViewPool())
                }

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_produto_beb_refrigerante, parent, false
            )
        )

        fun bind(produtoBebida: ProdutoBebida, idProduto: String) {

            Glide.with(imgLogo.context)
                .load(produtoBebida.imagem)
                //.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.splash_screen)
                .into(imgLogo)

            produtoNome.text = (produtoBebida.nome as String).replace("\\n", "\n")
            //tamanhoRecyclerView.adapter = TamanhoBebGridAdapter(produtoBebida, idProduto)

            if (produtoBebida.atributos != null) {
                cardBackground.setOnClickListener {
                    irParaAtributoFragment(itemView, produtoBebida, idProduto)
                }
            }
        }

        fun procurarTamanhos(pos: Int) {
            val tamanhosBebQuery: Query by lazy {
                db.collection("Lojista").document(LojistaSingleton.objetoLojista.uid)
                    .collection("Cardapio")
                    .document(idCategoria)
                    .collection("Produto")
                    .document(snapshots.getSnapshot(pos).id)
                    .collection("tamanho")
                    .whereEqualTo("ativo", true)
            }

            bindRecyclerTamanhoBeb(
                TamanhoBebGridAdapter(
                    FirestoreRecyclerOptions.Builder<Any>()
                        .setQuery(tamanhosBebQuery, Any::class.java).build()
                )
            )
        }

        private fun bindRecyclerTamanhoBeb(adapter: TamanhoBebGridAdapter) {
            tamanhoRecyclerView.adapter = adapter
            adapter.startListening()
        }
    }

    private fun irParaAtributoFragment(v: View, entity: Any, idProduto: String) {
        val bundle: Bundle = when (entity) {
            is ProdutoUnidade -> {
                Bundle().apply {
                    this.putParcelable(entity.tipoProduto?.name, entity)
                    this.putString("tipoProduto", entity.tipoProduto?.name)
                }
            }

            is ProdutoBebida -> {
                Bundle().apply {
                    this.putParcelable(entity.tipoProduto?.name, entity)
                    this.putString("tipoProduto", entity.tipoProduto?.name)
                }
            }
            else -> Bundle.EMPTY
        }

        bundle.putString("idCategoria", idCategoria)
        bundle.putString("idProduto", idProduto)

        v.findNavController().navigate(
            R.id.irParaAtributoFragment, bundle
        )
    }
}