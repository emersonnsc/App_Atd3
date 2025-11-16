package br.unimes.appatd3 // Verifique se o pacote está correto

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.unimes.appatd3.databinding.ActivityListBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ListActivity : AppCompatActivity() {


    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabVoltar.setOnClickListener {
            finish()
        }

        carregarJogos()
    }

    private fun carregarJogos() {
        val db = Firebase.firestore
        binding.tvDadosLista.text = "Carregando jogos..." // Mensagem inicial

        db.collection("jogos")
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    binding.tvDadosLista.text = "Nenhum jogo cadastrado."
                    return@addOnSuccessListener
                }

                val jogosStr = StringBuilder()
                for (document in result) {

                    val nome = document.getString("nome") ?: "N/A"
                    val plataforma = document.getString("plataforma") ?: "N/A"
                    val estado = document.getString("estado") ?: "N/A"

                    jogosStr.append("Jogo: $nome\n")
                    jogosStr.append("Plataforma: $plataforma\n")
                    jogosStr.append("Estado: $estado\n\n")
                }

                binding.tvDadosLista.text = jogosStr.toString()

            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao buscar dados: ${exception.message}", Toast.LENGTH_LONG).show()
                binding.tvDadosLista.text = "Falha ao carregar os dados."
            }
    }
}
