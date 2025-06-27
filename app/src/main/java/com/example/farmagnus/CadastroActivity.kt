package com.example.farmagnus

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.farmagnus.databinding.ActivityCadastroBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.User

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.campoCPF.addCpfMask()
        binding.campoTelefone.addPhoneMask()
        binding.botaoCriarConta.setOnClickListener {
            cadastrar()
        }
    }

    fun TextInputEditText.addCpfMask() {
        this.addTextChangedListener(object : TextWatcher {
            var isUpdating = false
            val cpfMask = "###.###.###-##"
            val onlyDigits = Regex("\\D")

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }

                val str = s.toString().replace(onlyDigits, "")
                var formatted = ""
                var i = 0

                for (m in cpfMask) {
                    if (m != '#') {
                        formatted += m
                    } else {
                        if (i < str.length) {
                            formatted += str[i]
                            i++
                        } else {
                            break
                        }
                    }
                }

                isUpdating = true
                this@addCpfMask.setText(formatted)
                this@addCpfMask.setSelection(formatted.length.coerceAtMost(formatted.length))
            }
        })
    }

    fun EditText.addPhoneMask() {
        this.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private var cursorPosition = 0
            private var beforeTextLength = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (!isUpdating) {
                    beforeTextLength = s?.length ?: 0
                    cursorPosition = this@addPhoneMask.selectionStart
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return

                isUpdating = true

                val digits = s.toString().filter { it.isDigit() }

                val formatted = buildString {
                    when {
                        digits.isEmpty() -> {}
                        digits.length <= 2 -> append("(${digits}")
                        digits.length <= 7 -> append(
                            "(${
                                digits.substring(
                                    0,
                                    2
                                )
                            }) ${digits.substring(2)}"
                        )

                        digits.length <= 11 -> append(
                            "(${
                                digits.substring(
                                    0,
                                    2
                                )
                            }) ${digits.substring(2, 7)}-${digits.substring(7)}"
                        )

                        else -> append(
                            "(${digits.substring(0, 2)}) ${
                                digits.substring(
                                    2,
                                    7
                                )
                            }-${digits.substring(7, 11)}"
                        )
                    }
                }

                this@addPhoneMask.setText(formatted)

                val newLength = formatted.length
                var newCursorPos = cursorPosition

                if (newLength > beforeTextLength) {
                    newCursorPos += (newLength - beforeTextLength)
                } else if (newLength < beforeTextLength) {
                    newCursorPos = (newCursorPos - (beforeTextLength - newLength)).coerceAtLeast(0)
                }
                if (newCursorPos > newLength) newCursorPos = newLength

                this@addPhoneMask.setSelection(newCursorPos)

                isUpdating = false
            }
        })
    }

    private fun cadastrar() {
        if (camposPreenchidos()) {
            val user = User(
                nome = binding.campoNome.text.toString(),
                cpf = binding.campoCPF.text.toString(),
                email = binding.campoEmail.text.toString(),
                telefone = binding.campoTelefone.text.toString(),
                senha = binding.campoSenha.text.toString()
            )

            // Salvar apenas os dados necessários no SharedPreferences
            salvarNoSharedPreferences(user)

            lifecycleScope.launch {
                try {
                    withContext(Main) {
                        AlertDialog.Builder(this@CadastroActivity)
                            .setTitle("Criado com sucesso!")
                            .setMessage("Sua conta foi criada com sucesso!")
                            .setPositiveButton("OK") { dialog, _ ->
                                startActivity(
                                    Intent(
                                        this@CadastroActivity,
                                        MainActivity::class.java
                                    )
                                )
                                finishAffinity()
                                dialog.dismiss()
                            }
                            .setCancelable(false)
                            .show()
                    }
                } catch (e: Exception) {
                    Log.e("CadastroActivity", "Erro ao salvar o usuário", e)
                    withContext(Main) {
                        AlertDialog.Builder(this@CadastroActivity)
                            .setTitle("Atenção!")
                            .setMessage("Ocorreu um erro ao salvar seu cadastro! Tente novamente mais tarde.")
                            .setNegativeButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        } else {
            AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Para a criação da sua conta é necessário o preenchimento de todos os campos.")
                .setNegativeButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun camposPreenchidos(): Boolean {
        val editTexts = listOf(
            binding.campoNome.text.toString(),
            binding.campoCPF.text.toString(),
            binding.campoEmail.text.toString(),
            binding.campoTelefone.text.toString(),
            binding.campoSenha.text.toString(),
            binding.campoRepetirSenha.text.toString()
        )
        val senhas: Boolean =
            binding.campoSenha.text.toString() == binding.campoRepetirSenha.text.toString()

        return editTexts.all { it.isNotEmpty() && it.isNotBlank() && senhas }
    }

    // Função para salvar os dados no SharedPreferences
    private fun salvarNoSharedPreferences(user: User) {
        // Acessa o SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Salvando as informações do usuário no SharedPreferences
        editor.putString("nome", user.nome)
        editor.putString("cpf", user.cpf)
        editor.putString("email", user.email)
        editor.putString("senha", user.senha)

        // Aplicando as mudanças (assíncrono)
        editor.apply()

        // Após salvar, vamos recuperar e logar os dados para garantir que salvar foi bem-sucedido
        logSharedPreferences()
    }

    // Função para recuperar e logar os dados do SharedPreferences
    private fun logSharedPreferences() {
        // Acessa o SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Recuperando os dados armazenados
        val nome = sharedPreferences.getString("nome", "Valor não encontrado")
        val cpf = sharedPreferences.getString("cpf", "Valor não encontrado")
        val email = sharedPreferences.getString("email", "Valor não encontrado")
        val senha = sharedPreferences.getString("senha", "Valor não encontrado")

        // Logando os dados para verificar se foram salvos corretamente
        Log.d("SharedPreferences", "Nome: $nome")
        Log.d("SharedPreferences", "CPF: $cpf")
        Log.d("SharedPreferences", "Email: $email")
        Log.d("SharedPreferences", "Senha: $senha")

        // Você também pode logar todas as entradas com o método 'all'
        val allEntries = sharedPreferences.all
        for ((key, value) in allEntries) {
            Log.d("SharedPreferences", "Chave: $key, Valor: $value")
        }
    }
}

