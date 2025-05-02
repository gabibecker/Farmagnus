package com.example.farmagnus

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.farmagnus.databinding.ActivityCadastroBinding
import com.example.farmagnus.databinding.ActivityMainBinding

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCadastroBinding = ActivityCadastroBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnDados.setOnClickListener{
            binding.linearLayoutDados.visibility = VISIBLE
            binding.linearLayoutendereco.visibility = View.GONE
            binding.botaoEntrar.text = "Continuar"
        }

        binding.btnEndereco.setOnClickListener {
            binding.linearLayoutDados.visibility = View.GONE
            binding.linearLayoutendereco.visibility = VISIBLE
            binding.botaoEntrar.text = "Entrar"
        }

    }
}