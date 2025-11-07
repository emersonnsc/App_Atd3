package br.unimes.appatd3

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = Firebase.firestore
        binding.btnCadastrar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val preco = binding.edtPreco.text.toString().toDouble()
            val quantidade = binding.edtQuantidade.text.toString().toInt()
            val produto = hashMapOf(
                "nome" to nome,
                "preco" to preco,
                "quantidade" to quantidade
            )
            db.collection("produtos")
                .add(produto)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this, "Falha!${task.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

        }
    }
}