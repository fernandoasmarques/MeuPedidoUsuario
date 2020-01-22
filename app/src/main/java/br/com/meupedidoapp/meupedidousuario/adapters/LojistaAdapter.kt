package br.com.meupedidoapp.meupedidousuario.adapters

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.entities.Lojista
import br.com.meupedidoapp.meupedidousuario.fragments.lojista.LojistaSingleton
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.card.MaterialCardView

open class LojistaAdapter(options: FirestoreRecyclerOptions<Lojista>) :
    FirestoreRecyclerAdapter<Lojista, LojistaAdapter.LojistaHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LojistaHolder {
        return LojistaHolder(parent)
    }

    override fun onBindViewHolder(holder: LojistaHolder, position: Int, entity: Lojista) {
        holder.bind(entity)
    }

    inner class LojistaHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //private val iconStar = itemView.findViewById<ImageView>(R.id.imgStar)
        private val tituloLojista = itemView.findViewById<TextView>(R.id.ItemLojista_tituloLojista)
        private val subTitulo = itemView.findViewById<TextView>(R.id.ItemLojista_subTitulo)
        private val marketingBanner =
            itemView.findViewById<ImageView>(R.id.ItemLojista_marketingBanner)
        private val imagemPerfil = itemView.findViewById<ImageView>(R.id.ItemLojista_imagemPerfil)
        //private val txtAvaliacao = itemView.findViewById<TextView>(R.id.ItemLojista_txtAvaliacao)
        //private val btnCardapio = itemView.findViewById<Button>(R.id.ItemLojista_btnCardapio)
        private val lojistaGradient = itemView.findViewById<View>(R.id.ItemLojista_lojistaGradient)
        private val backgroundImgPerfilLojista =
            itemView.findViewById<View>(R.id.ItemLojista_backgroundImgLojista)
        //private val txtEstaAberto = itemView.findViewById<TextView>(R.id.ItemLojista_txtEstaAberto)
        private val cardLojista =
            itemView.findViewById<MaterialCardView>(R.id.ItemLojista_cardLojista)

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_lojista,
                parent,
                false
            )
        )

        fun bind(entity: Lojista) {
            tituloLojista.text = entity.nomeFantasia
            subTitulo.text = entity.categoriaPrimaria

            Glide.with(imagemPerfil.context)
                .load(entity.imagemPerfil)
                .into(imagemPerfil)

            Glide.with(marketingBanner.context)
                .load(entity.marketingBanner)
                .into(marketingBanner)

            /*if (entity.isEstaAberto) {
                txtEstaAberto.text = "Aberto"
                txtEstaAberto.background.setColorFilter(
                    Color
                        .parseColor("#008000"), PorterDuff.Mode.SRC_IN
                )
            } else {
                txtEstaAberto.text = "Fechado"
                txtEstaAberto.background.setColorFilter(
                    Color.RED,
                    PorterDuff.Mode.SRC_IN
                )
            }*/

            GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                intArrayOf(android.R.color.transparent, Color.parseColor(entity.tema.corPrimaria))
            ).apply {
                lojistaGradient.background = this
            }

            backgroundImgPerfilLojista.setBackgroundColor(Color.parseColor(entity.tema.corPrimaria))

            cardLojista.setOnClickListener {
                irParaLoja(it, entity)
            }

            /*btnCardapio.setOnClickListener {
                irParaLoja(it, entity)
            }*/
        }

        private fun irParaLoja(v: View, entity: Lojista) {
            LojistaSingleton.objetoLojista = entity
            v.findNavController().navigate(R.id.irParaLojistaFragment)
        }
    }
}