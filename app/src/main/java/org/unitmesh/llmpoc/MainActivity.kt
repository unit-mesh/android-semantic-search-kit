package org.unitmesh.llmpoc

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.unitmesh.llmpoc.databinding.ActivityMainBinding
import org.unitmesh.llmpoc.embedding.STSemantic
import java.io.IOException
import java.io.InputStream


//import org.unitmesh.llmpoc.embedding.STSemantic
private const val TAG = "MainActivity"

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
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        loadBinAsset("xml/backup_rules.xml")

//        val stSemantic = STSemantic.create(this)
//        val output = stSemantic.embed("demo")
//        println(output)
    }

    fun loadBinAsset(name: String): ByteArray? {
        var stream: InputStream? = null
        try {



            // read all
            val size = stream!!.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            return buffer
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                stream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }
}