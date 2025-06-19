package com.example.farmagnus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.farmagnus.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.LoginRequest
import retrofit2.Response

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
            //login()
            startActivity(Intent(this, MenuInicialActivity::class.java))
        }
    }

//    private fun login() {
//        val cpf = binding.editTextCPF.text.toString()
//        val senha = binding.editTextSenha.text.toString()
//
//        if (cpf.isEmpty() || senha.isEmpty()) {
//            AlertDialog.Builder(this)
//                .setTitle("Atenção")
//                .setMessage("Por favor, preencha CPF e senha antes de continuar.")
//                .setNegativeButton("OK") { d, _ -> d.dismiss() }
//                .show()
//            return
//        }
//
//        lifecycleScope.launch {
//            try {
//                val credentials = LoginRequest(cpf, senha)
//
//
//
//                if (response.isSuccessful && response.body() == true) {
//                    startActivity(Intent(this@MainActivity, MenuInicialActivity::class.java))
//                    finishAffinity()
//                } else {
//                    withContext(Dispatchers.Main) {
//                        AlertDialog.Builder(this@MainActivity)
//                            .setTitle("Dados incorretos")
//                            .setMessage("Login ou senha incorretos, tente novamente!")
//                            .setNegativeButton("OK") { d, _ -> d.dismiss() }
//                            .show()
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("MainActivity", "Erro ao fazer login", e)
//                withContext(Dispatchers.Main) {
//                    AlertDialog.Builder(this@MainActivity)
//                        .setTitle("Erro de conexão")
//                        .setMessage("Houve um problema ao tentar fazer o login. Verifique sua conexão e tente novamente.")
//                        .setNegativeButton("OK") { d, _ -> d.dismiss() }
//                        .show()
//                }
//            }
//        }
//    }
}
