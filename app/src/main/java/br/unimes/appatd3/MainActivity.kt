package br.unimes.appatd3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unimes.appatd3.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        binding.btnLogar.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim() // Adicionado .trim()
            val senha = binding.edtSenha.text.toString().trim() // Adicionado .trim()

            if (email.isBlank() || senha.isBlank()) {
                Toast.makeText(this, "Por favor, preencha e-mail e senha.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, CadastroActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        Toast.makeText(
                            this, "Falha na autenticação: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        binding.btnCadastro.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim() // Adicionado .trim()
            val senha = binding.edtSenha.text.toString().trim() // Adicionado .trim()

            if (email.isBlank() || senha.isBlank()) {
                Toast.makeText(this, "Preencha e-mail e senha para cadastrar.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT)
                            .show()

                        binding.edtEmail.text?.clear()
                        binding.edtSenha.text?.clear()
                    } else {
                        Toast.makeText(
                            this, "Falha ao cadastrar: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}
