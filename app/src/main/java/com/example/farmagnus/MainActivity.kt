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

        // Botão para cadastro
        binding.botaoCadastro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        // Botão para login
        binding.botaoEntrar.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val cpf = binding.editTextCPF.text.toString()
        val senha = binding.editTextSenha.text.toString()

        if (cpf.isEmpty() || senha.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Por favor, preencha CPF e senha antes de continuar.")
                .setNegativeButton("OK") { d, _ -> d.dismiss() }
                .show()
            return
        }

        // Verificar as credenciais no SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val storedCpf = sharedPreferences.getString("cpf", "")
        val storedSenha = sharedPreferences.getString("senha", "")

        // Validando as credenciais
        if (cpf == storedCpf && senha == storedSenha) {
            // Login bem-sucedido, redireciona para o Menu Inicial
            startActivity(Intent(this, MenuInicialActivity::class.java))
            finishAffinity()  // Fecha todas as atividades anteriores
        } else {
            // Caso de erro no login, exibe um alerta
            AlertDialog.Builder(this)
                .setTitle("Dados incorretos")
                .setMessage("Login ou senha incorretos, tente novamente!")
                .setNegativeButton("OK") { d, _ -> d.dismiss() }
                .show()
        }
    }
}