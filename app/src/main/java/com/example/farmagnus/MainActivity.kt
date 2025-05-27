package com.example.farmagnus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.farmagnus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.botaoCadastro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
        binding.botaoEntrar.setOnClickListener {
            startActivity(Intent(this, MenuInicialActivity::class.java))
            //login()
            finishAffinity()
            //adicionar preference islogged
        }
    }

    private fun login() {
        try {
            if (binding.editTextCPF.text.toString()
                    .isNotEmpty() && binding.editTextSenha.text.toString().isNotEmpty()
            ) {
                val user: Boolean = true // temporário
                if (user) {
                    startActivity(Intent(this, MenuInicialActivity::class.java))
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Dados incorretos")
                        .setMessage("Login ou senha incorretos, tente novamente!")
                        .setNegativeButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Atenção")
                    .setMessage("Por favor, preencha CPF e senha antes de continuar.")
                    .setNegativeButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        } catch (e: Exception) {
            AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Houve um problema ao tentar fazer o login, tente novamente mais tarde!")
                .setNegativeButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
