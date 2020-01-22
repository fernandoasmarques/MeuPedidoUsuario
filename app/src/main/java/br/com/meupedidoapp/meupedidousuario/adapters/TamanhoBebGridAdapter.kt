package br.com.meupedidoapp.meupedidousuario.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.entities.Tamanho
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.gson.Gson
import java.math.BigDecimal
import java.text.DecimalFormat

class TamanhoBebGridAdapter(options: FirestoreRecyclerOptions<Any>) :
    FirestoreRecyclerAdapter<Any, TamanhoBebGridAdapter.TamanhoBebViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TamanhoBebViewHolder {
        return TamanhoBebViewHolder(parent)

    }

    override fun onBindViewHolder(holder: TamanhoBebViewHolder, position: Int, entity: Any) {
        holder.bind(convertToObject(entity))

        // Log.v("Msg", "Referencia: ${snapshots.getSnapshot(position).reference.parent.parent?.id}")
    }

    private fun convertToObject(entity: Any): Tamanho {
        return Gson().fromJson(
            Gson().toJsonTree(entity),
            Tamanho::class.java
        )
    }

    inner class TamanhoBebViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var qtdeProduto: Int = 0

        private val backgroundColor =
            itemView.findViewById<ConstraintLayout>(R.id.itemProdutoBebTamanhos_background)

        private val tamanhoBeb =
            itemView.findViewById<TextView>(R.id.itemProdutoBebTamanhos_tamanho)

        private val tamanhoBebPreco =
            itemView.findViewById<TextView>(R.id.itemProdutoBebTamanhos_tamanhoPreco)

        private val counterQtdeProduto =
            itemView.findViewById<TextView>(R.id.itemProdutoBebTamanhos_qtdeProduto)

        private val txtAdicionarProduto =
            itemView.findViewById<TextView>(R.id.itemProdutoBebTamanhos_adicionarProduto)

        private val txtRetirarProduto =
            itemView.findViewById<TextView>(R.id.itemProdutoBebTamanhos_retirarProduto)

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_produto_beb_refrigerante_tamanhos,
                parent,
                false
            )
        )

        fun bind(tamanho: Tamanho) {
            backgroundColor.setBackgroundColor(Color.parseColor("#F5F5F5"))

            tamanhoBeb.text = tamanho.tamanho

            tamanhoBebPreco.text = itemView.context.resources.getString(
                R.string.precoString, DecimalFormat("#,###.00").format(
                    tamanho.preco.setScale(
                        2,
                        BigDecimal.ROUND_HALF_EVEN
                    ).toDouble()
                )
            )

            txtAdicionarProduto.setOnClickListener{
                adicionarProduto()
            }

            txtRetirarProduto.setOnClickListener{
                removerProduto()
            }
        }

        private fun adicionarProduto() {
            ++qtdeProduto
            mostrarQtdeProduto(qtdeProduto)
        }

        private fun removerProduto() {
            --qtdeProduto
            mostrarQtdeProduto(qtdeProduto)
        }

        private fun mostrarQtdeProduto(numero: Int) {
            counterQtdeProduto.text = numero.toString()
        }
    }
}