package org.unitmesh.llmpoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cc.unitmesh.rag.document.Document
import cc.unitmesh.rag.store.InMemoryEmbeddingStore
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.unitmesh.llmpoc.databinding.ActivityMainBinding
import org.unitmesh.llmpoc.embedding.STSemantic

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val stSemantic = STSemantic.create(this)

        val embeddingStore = InMemoryEmbeddingStore<Document>()

        listOf("That is a happy dog", "That is a very happy person", "Today is a sunny day")
            .map { Document.from(it) }
            .forEach { embeddingStore.add(stSemantic.embed(it.text), it) }

        embeddingStore.findRelevant(stSemantic.embed("That is a happy person"), 3)
            .forEach { println(it) }
    }
}