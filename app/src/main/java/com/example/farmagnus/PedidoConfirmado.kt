package com.example.farmagnus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.farmagnus.databinding.ActivityPedidoConfirmadoBinding

class PedidoConfirmado : AppCompatActivity() {

    private lateinit var binding: ActivityPedidoConfirmadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPedidoConfirmadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.botaoVoltar.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, MenuInicialActivity::class.java))
        }
    }
}
