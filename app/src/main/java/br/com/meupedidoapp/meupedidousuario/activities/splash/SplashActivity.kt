package br.com.meupedidoapp.meupedidousuario.activities.splash

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Config
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.meupedidoapp.meupedidousuario.R
import br.com.meupedidoapp.meupedidousuario.activities.principal.PrincipalActivity
import kotlinx.android.synthetic.main.fragment_atributos.*

class SplashActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                Log.v("Msg", "O modo noturno está desativado.")
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                Log.v("Msg", "O modo noturno está ativado.")
            }
        }

        Intent(this, PrincipalActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }
}