package br.com.meupedidoapp.meupedidousuario.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.entities.Atributo
import br.com.meupedidoapp.meupedidousuario.entities.ItensAtributo
import br.com.meupedidoapp.meupedidousuario.entities.Opcao
import br.com.meupedidoapp.meupedidousuario.entities.TipoOpcaoEnum
import br.com.meupedidoapp.meupedidousuario.fragments.atributos.AtributosPresenter
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.gson.Gson
import java.math.BigDecimal
import java.text.DecimalFormat

interface OpcaoSelecionadaListener {
    fun onOpcaoSelecionada(position: Int)
}

class OpcaoAdapter(
    options: FirestoreRecyclerOptions<Any>,
    private val tipoOpcao: TipoOpcaoEnum
) : FirestoreRecyclerAdapter<Any, RecyclerView.ViewHolder>(options) {

    private lateinit var opcaoSelecionadaListener: OpcaoSelecionadaListener

    fun setOpcaoSelecionadaListener(opcaoSelecionadaListener: OpcaoSelecionadaListener) {
        this.opcaoSelecionadaListener = opcaoSelecionadaListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TipoOpcaoEnum.UN.ordinal -> OpcaoUnHolder(parent)
            TipoOpcaoEnum.AD.ordinal -> OpcaoAdHolder(parent)
            else -> OpcaoQtdeHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, entity: Any) {
        when (getItemViewType(position)) {
            TipoOpcaoEnum.UN.ordinal -> (holder as OpcaoUnHolder).bind(
                convertToObject(
                    snapshots.getSnapshot(position).id, entity
                )
            )

            TipoOpcaoEnum.AD.ordinal -> (holder as OpcaoAdHolder).bind(
                convertToObject(
                    snapshots.getSnapshot(position).id, entity
                ), position
            )
            /*
            TipoOpcaoEnum.QTDE.ordinal -> (holder as OpcaoQtdeHolder).bind(
            )*/
        }
    }

    override fun getItemCount(): Int {
        return snapshots.size
    }

    private fun convertToObject(id: String, entity: Any): Opcao {
        return Gson().fromJson(
            Gson().toJsonTree(entity),
            Opcao::class.java
        ).apply {
            this.id = id
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (tipoOpcao) {
            TipoOpcaoEnum.UN -> TipoOpcaoEnum.UN.ordinal
            TipoOpcaoEnum.AD -> TipoOpcaoEnum.AD.ordinal
            else -> TipoOpcaoEnum.QTDE.ordinal
        }
    }

    inner class OpcaoUnHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nomeOpcao = itemView.findViewById<TextView>(R.id.itemOpcaoUn_nome)
        private val valorOpcao = itemView.findViewById<TextView>(R.id.itemOpcaoUn_valor)

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_opcao_un,
                parent,
                false
            )
        )

        fun bind(opcao: Opcao) {
            nomeOpcao.text = opcao.nome
            valorOpcao.text = itemView.context.resources.getString(
                R.string.precoString, DecimalFormat("#,###.00").format(
                    opcao.preco?.setScale(
                        2,
                        BigDecimal.ROUND_HALF_EVEN
                    )?.toDouble()
                )
            )
        }
    }

    inner class OpcaoAdHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nomeOpcao = itemView.findViewById<TextView>(R.id.itemOpcaoAd_nome)
        private val valorOpcao = itemView.findViewById<TextView>(R.id.itemOpcaoAd_valor)
        private val checkBoxUn =
            itemView.findViewById<MaterialCheckBox>(R.id.itemOpcaoAd_checkBoxUn)
        private val background =
            itemView.findViewById<MaterialCardView>(R.id.itemOpcaoAd_background)

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_opcao_ad,
                parent,
                false
            )
        )

        fun bind(opcao: Opcao, pos: Int) {
            nomeOpcao.text = opcao.nome
            valorOpcao.text = itemView.context.resources.getString(
                R.string.opcao_ad_preco, DecimalFormat("#,###.00").format(
                    opcao.preco?.setScale(
                        2,
                        BigDecimal.ROUND_HALF_EVEN
                    )?.toDouble()
                )
            )
            val itensAtributo = ItensAtributo(opcao, 1)

            background.setOnClickListener {
                checkBoxUn.isChecked = !checkBoxUn.isChecked
                opcaoSelecionadaListener.onOpcaoSelecionada(pos)
            }

            checkBoxUn.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    adicionarAtributo(itensAtributo)
                else
                    removerAtributo(itensAtributo)
            }
        }

        private fun adicionarAtributo(itemAtributo: ItensAtributo) {
            if (!AtributosPresenter.itensAtributo.contains(itemAtributo))
                AtributosPresenter.itensAtributo.add(itemAtributo)
        }

        private fun removerAtributo(itemAtributo: ItensAtributo) {
            AtributosPresenter.itensAtributo.remove(itemAtributo)
        }
    }

    inner class OpcaoQtdeHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_opcao_un,
                parent,
                false
            )
        )

        fun bind(atributoUn: Atributo) {

        }
    }
}