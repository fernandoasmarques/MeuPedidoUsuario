package br.com.meupedidoapp.meupedidousuario.fragments.atributos

import android.os.Bundle
import android.widget.TextView
import androidx.databinding.ObservableArrayList
import androidx.navigation.fragment.NavHostFragment.findNavController
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.adapters.AtributoAdapter
import br.com.meupedidoapp.meupedidousuario.entities.*
import br.com.meupedidoapp.meupedidousuario.fragments.lojista.LojistaSingleton
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.math.BigDecimal
import java.text.DecimalFormat

class AtributosPresenter(
    private val atributosFragment: AtributosFragment,
    private val atributosPresenterOutput: AtributosContracts.AtributosPresenterOutput,
    private val bundle: Bundle?
) : AtributosContracts.AtributosPresenterInput{

    companion object {
        val itensAtributo by lazy { ObservableArrayList<ItensAtributo>() }
        //val itensAtributo : MutableList<ItensAtributo> = mutableListOf<ItensAtributo>()
        var qtdeProduto: Int = 1
        var subtotal = BigDecimal("0")
    }

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val idCategoria: String? = bundle?.getString("idCategoria")
    private val idProduto: String? = bundle?.getString("idProduto")
    private lateinit var valorProduto: BigDecimal
    private lateinit var produto: Any

    override fun buscarNomeProduto() {
        when (bundle?.getString("tipoProduto")) {
            TipoProdutoEnum.UN.name -> {
                produto = bundle.getParcelable(TipoProdutoEnum.UN.name)!!
                valorProduto = (produto as ProdutoUnidade).preco!!
                subtotal = (produto as ProdutoUnidade).preco!!
                atributosPresenterOutput.mostrarNomeProduto((produto as ProdutoUnidade).nome!!)
            }
            TipoProdutoEnum.BEB.name -> {
                produto = bundle.getParcelable(TipoProdutoEnum.BEB.name)!!
                atributosPresenterOutput.mostrarNomeProduto((produto as ProdutoTamanho).nome!!)
            }
        }
    }

    override fun buscarAtributos() {
        val atributosProduto: Query by lazy {
            db.collection("Lojista").document(LojistaSingleton.objetoLojista.uid)
                .collection("Cardapio")
                .document(idCategoria!!).collection("Produto").document(idProduto!!)
                .collection("Atributos")
        }

        atributosPresenterOutput.mostrarAtributos(
            AtributoAdapter(
                FirestoreRecyclerOptions.Builder<Any>()
                    .setQuery(atributosProduto, Any::class.java)
                    .setLifecycleOwner(atributosFragment)
                    .build(), idCategoria, idProduto
            )
        )
    }

    override fun limparItensSelecionados() {
        itensAtributo.clear()
        subtotal = BigDecimal("0")
        qtdeProduto = 0
    }

    // Fazer isso aqui com Maestria! Melhorar isso 100000000000000 de vezes.
    // função muito lenta.
    override fun atualizarPrecoSubTotal(txtSubtotal: TextView, qtde: Int) {
        qtdeProduto = qtde

        var soma = BigDecimal("0")
        for (itensSelecionados in itensAtributo) {
            soma = soma.add(itensSelecionados.getPrecoTotal())
        }
        subtotal = valorProduto.add(soma).multiply(BigDecimal(qtdeProduto.toString()))

        txtSubtotal.text = txtSubtotal.context.resources.getString(
            R.string.precoString, DecimalFormat("#,###.00").format(
                subtotal.setScale(
                    2,
                    BigDecimal.ROUND_HALF_EVEN
                )?.toDouble()
            )
        )

        itensAtributo.addOnListChangedCallback(
            ItemAtributoArrayListener(
                txtSubtotal,
                qtdeProduto,
                valorProduto
            )
        )
    }

    override fun irParaItensPedidoFragment() {
        val itensPedido : ItensPedido = ItensPedido(produto as ProdutoUnidade ,qtdeProduto)
        findNavController(atributosFragment).navigate(R.id.irParaItensPedidoFragment, Bundle().apply {
            this.putParcelable("itensPedido", itensPedido)
        })
    }
}