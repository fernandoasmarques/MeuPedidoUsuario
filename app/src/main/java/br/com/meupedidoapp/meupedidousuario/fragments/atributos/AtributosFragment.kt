package br.com.meupedidoapp.meupedidousuario.fragments.atributos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.adapters.AtributoAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.bottomsheet_itenspedidos.*
import kotlinx.android.synthetic.main.fragment_atributos.*

class AtributosFragment : Fragment(), AtributosContracts.AtributosPresenterOutput {

    private lateinit var atributosPresenterInput: AtributosContracts.AtributosPresenterInput
    private lateinit var recyclerAtributos: RecyclerView
    private lateinit var nomeProdutoToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var nomeProduto: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        atributosPresenterInput = AtributosPresenter(this, this, arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_atributos, container, false)

        recyclerAtributos =
            rootView.findViewById<RecyclerView>(R.id.fragmentAtributos_recyclerAtributos).apply {
                this.layoutManager =
                    LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            }

        nomeProdutoToolbar =
            rootView.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fragmentAtributos_toolBar)
                .apply {
                    this.setNavigationIcon(R.drawable.icone_fechar)
                    this.setNavigationOnClickListener { activity?.onBackPressed() }
                    this.setTitleTextAppearance(context, R.style.GoogleSansFont)
                    //this.setTitleTextColor(Color.parseColor("#FFFFFF"))
                }

        BottomSheetBehavior.from(rootView.findViewById<LinearLayout>(R.id.fragmentAtributos_bottomsheet))
            .apply {
                this.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        fragmentAtributos_bottomsheet_imgseta.rotation = slideOffset * 180f
                    }

                    override fun onStateChanged(bottomSheet: View, newState: Int) {

                    }
                })
            }

        with(rootView.findViewById<NumberPicker>(R.id.numberpicker)) {
            this.minValue = 1
            this.maxValue = 15
            this.wrapSelectorWheel = true

            this.setOnValueChangedListener { _, _, newVal ->
                if (newVal == 1)
                    fragmentAtributos_txtNomeProdutoQtde.text = "$nomeProduto\napenas."
                else
                    fragmentAtributos_txtNomeProdutoQtde.text = "$nomeProduto\niguais a este."

                atributosPresenterInput.atualizarPrecoSubTotal(
                    rootView.findViewById<TextView>(R.id.fragmentAtributos_bottomsheet_txtSubtotal),
                    newVal
                )
            }
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        atributosPresenterInput.buscarAtributos()
        atributosPresenterInput.buscarNomeProduto()
        atributosPresenterInput.atualizarPrecoSubTotal(view.findViewById<TextView>(R.id.fragmentAtributos_bottomsheet_txtSubtotal))

        view.findViewById<MaterialButton>(R.id.fragmentAtributos_bottomsheet_btnAvancar)
            .setOnClickListener {
                atributosPresenterInput.irParaItensPedidoFragment()
            }
    }

    override fun mostrarAtributos(atributosAdapter: AtributoAdapter) {
        recyclerAtributos.adapter = atributosAdapter
    }

    override fun mostrarNomeProduto(nomeProduto: String) {
        this.nomeProdutoToolbar.title = nomeProduto
        this.nomeProduto = nomeProduto
        fragmentAtributos_txtNomeProdutoQtde.text = "$nomeProduto\napenas."
    }

    override fun onDestroy() {
        atributosPresenterInput.limparItensSelecionados()
        super.onDestroy()
    }
}
