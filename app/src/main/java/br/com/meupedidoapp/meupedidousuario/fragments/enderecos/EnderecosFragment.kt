package br.com.meupedidoapp.meupedidousuario.fragments.enderecos


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import br.com.meupedidoapp.meupedidousuario.R
import com.google.android.material.appbar.AppBarLayout

/**
 * A simple [Fragment] subclass.
 */
class EnderecosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enderecos, container, false)
    }

    override fun onResume() {
        super.onResume()
        //(activity as AppCompatActivity).findViewById<AppBarLayout>(R.id.PrincipalActivity_appbarlayout).setExpanded(true)
        activity?.window?.statusBarColor = ContextCompat.getColor(activity?.baseContext!!, R.color.colorPrimaryDark)
    }
}
