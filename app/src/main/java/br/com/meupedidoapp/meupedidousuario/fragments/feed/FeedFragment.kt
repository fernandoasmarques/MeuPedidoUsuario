package br.com.meupedidoapp.meupedidousuario.fragments.feed

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.adapters.LojistaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FeedFragment : Fragment(R.layout.fragment_feed), FeedContracts.FeedPresenterOutput {

    private lateinit var feedPresenterInput: FeedContracts.FeedPresenterInput
    private lateinit var recyclerViewLojistas: RecyclerView
    private lateinit var fabItensPedido: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedPresenterInput = FeedPresenter(this)
        fabItensPedido = (activity as AppCompatActivity).findViewById(R.id.PrincipalActivity_fabItensPedido)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_feed, container, false)

        recyclerViewLojistas =
            rootView.findViewById<RecyclerView>(R.id.FragmentFeed_recyclerView).apply {
                this.setHasFixedSize(true)
                this.layoutManager = LinearLayoutManager(activity?.baseContext)

                this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if (dy > 0)
                            fabItensPedido.hide()
                        else
                            fabItensPedido.show()

                    }
                })
            }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        feedPresenterInput.buscarLojistas()

    /*    Handler().postDelayed({
            ObjectAnimator.ofFloat(
                view.findViewById<ImageView>(R.id.FragmentFeed_imgToolbar),
                "translationY",
                0f
            ).apply {
                duration = 600
                start()
            }

            ObjectAnimator.ofFloat(
                view.findViewById<ImageView>(R.id.FragmentFeed_imgToolbar),
                "translationX",
                -20f
            ).apply {
                duration = 600
                start()
            }
        }, 300)*/
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor =
            ContextCompat.getColor(activity?.baseContext!!, R.color.colorPrimary)
    }

    override fun mostrarLojistas(lojistaAdapter: LojistaAdapter) {
        recyclerViewLojistas.adapter = lojistaAdapter
        lojistaAdapter.startListening()
    }
}