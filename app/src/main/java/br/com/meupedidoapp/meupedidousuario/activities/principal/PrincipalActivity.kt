package br.com.meupedidoapp.meupedidousuario.activities.principal

import android.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.fragments.itens_pedido.ItensPedidoFragment
import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {

    private val itemPedidoFragment = ItensPedidoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        NavigationUI.setupWithNavController(
            PrincipalActivity_bottom_navigation,
            Navigation.findNavController(this, R.id.PrincipalActivity_navHostFragment)
        )

        PrincipalActivity_fabItensPedido.setOnClickListener {
            if (!itemPedidoFragment.isAdded) {
                supportFragmentManager.beginTransaction().apply {
                    add(R.id.fragment_container, itemPedidoFragment, "itemPedidoFragment")
                    hide(supportFragmentManager.findFragmentById(R.id.PrincipalActivity_navHostFragment)!!)
                    addToBackStack("itemPedidoFragment")
                }.commit()
            } else {
                return@setOnClickListener
            }

            /*findNavController(R.id.PrincipalActivity_navHostFragment).apply {
                when (this.currentDestination?.id) {
                    R.id.navigation_feed -> this.navigate(R.id.feed_to_itensPedidoFragment)
                    R.id.lojistaFragment -> this.navigate(R.id.lojistaFragment_to_itensPedidoFragment)
                }
            }*/
        }

        findNavController(R.id.PrincipalActivity_navHostFragment)
            .addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.escolhaProdutoFragment) {
                    PrincipalActivity_bottom_navigation.visibility = View.GONE
                    PrincipalActivity_fabItensPedido.hide()
                } else {
                    PrincipalActivity_bottom_navigation.visibility = View.VISIBLE
                }
            }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0)
            super.onBackPressed()
        else {
            supportFragmentManager.beginTransaction().apply {
                //remove(itemPedidoFragment)
                detach(itemPedidoFragment)
            }.commit()
            supportFragmentManager.popBackStack(
                "itemPedidoFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }
}
