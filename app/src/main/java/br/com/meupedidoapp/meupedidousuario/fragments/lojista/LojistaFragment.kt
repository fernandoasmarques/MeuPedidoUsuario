package br.com.meupedidoapp.meupedidousuario.fragments.lojista

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.entities.Tema
import br.com.meupedidoapp.meupedidousuario.adapters.CategoriaAdapter
import br.com.meupedidoapp.meupedidousuario.entities.Lojista
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerTitleStrip
import com.google.android.material.floatingactionbutton.FloatingActionButton


class LojistaFragment : Fragment(), LojistaContracts.LojistaPresenterOutput {

    private lateinit var lojistaPresenterInput: LojistaContracts.LojistaPresenterInput
    private lateinit var pagerCategoria: ViewPager2
    private lateinit var fabItensPedido: FloatingActionButton
    private lateinit var tabLayoutCategoria: TabLayout

    private var uidLojista: String? = LojistaSingleton.objetoLojista.uid
    private var nomeLojista: String? = LojistaSingleton.objetoLojista.nomeFantasia
    private var temaLojista: Tema? = LojistaSingleton.objetoLojista.tema

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.statusBarColor = Color.parseColor(temaLojista?.corPrimariaDark)
        lojistaPresenterInput = LojistaPresenter(this)
        fabItensPedido = (activity as AppCompatActivity).findViewById(R.id.PrincipalActivity_fabItensPedido)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_lojista, container, false)

        with(rootView.findViewById<Toolbar>(R.id.LojistaFragment_toolbar)) {
            this.setNavigationIcon(R.drawable.icone_voltar)
            this.setNavigationOnClickListener { activity?.onBackPressed() }
            this.background = ColorDrawable(Color.parseColor(temaLojista?.corPrimaria))
            this.title = nomeLojista
            this.setTitleTextAppearance(context, R.style.BebasNeueFont)
            this.setTitleTextColor(Color.parseColor(temaLojista?.corTextoTitulo))
        }

        with(rootView.findViewById<Toolbar>(R.id.LojistaFragment_toolbarFerramentas)) {
            this.inflateMenu(R.menu.menu_lojista)
        }

        pagerCategoria = rootView.findViewById<ViewPager2>(R.id.LojistaFragment_pagerCategoria)

        tabLayoutCategoria =
            rootView.findViewById<TabLayout>(R.id.LojistaFragment_tabLayout).apply {
                this.setTabTextColors(
                    Color.parseColor(temaLojista?.corTextoSecundario),
                    Color.parseColor(temaLojista?.corSecundaria)
                )
                this.setSelectedTabIndicatorColor(Color.parseColor(temaLojista?.corSecundaria))
            }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lojistaPresenterInput.buscarCategorias(uidLojista!!, temaLojista!!)

        if (fabItensPedido.isOrWillBeHidden)
            fabItensPedido.show()

    }

    override fun mostrarCategorias(categoriaAdapter: CategoriaAdapter) {
        pagerCategoria.adapter = categoriaAdapter
        categoriaAdapter.startListening()

        tabLayoutCategoria.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

                if (fabItensPedido.isOrWillBeHidden)
                    fabItensPedido.show()
            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        TabLayoutMediator(tabLayoutCategoria, pagerCategoria) { tab, position ->
            tab.text = categoriaAdapter.getItem(position).nome
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        LojistaSingleton.objetoLojista = Lojista()
    }
}
