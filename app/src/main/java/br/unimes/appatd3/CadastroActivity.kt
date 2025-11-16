package br.unimes.appatd3

import android.content.Intent // Import necessário para a lista
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unimes.appatd3.databinding.ActivityCadastroBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Firebase.firestore

        binding.btnCadastrar.setOnClickListener {

            val nome = binding.edtNome.text.toString()
            val plataforma = binding.edtPlataforma.text.toString()
            val estadoJogo = binding.edtEstadoJogo.text.toString()

            if (nome.isBlank() || plataforma.isBlank() || estadoJogo.isBlank()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val jogo = hashMapOf(
                "nome" to nome,
                "plataforma" to plataforma,
                "estado" to estadoJogo
            )

            db.collection("jogos")
                .add(jogo)
                .addOnSuccessListener {
                    Toast.makeText(this, "Jogo cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    binding.edtNome.text?.clear()
                    binding.edtPlataforma.text?.clear()
                    binding.edtEstadoJogo.text?.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Falha ao cadastrar: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

        binding.fabLista.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }
}
