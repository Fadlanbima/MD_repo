package com.bangkit.getguide

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var rvListHome: RecyclerView
    private val list = ArrayList<ListHome>()
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rvListHome = findViewById(R.id.rv_listhome)
        rvListHome.setHasFixedSize(true)

        list.addAll(ListWisataHome.listData)
        showRecycleView()

        auth = FirebaseAuth.getInstance()

    }

    private fun showRecycleView() {
        rvListHome.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val listHomeAdapter = ListHomeAdapter(list)
        rvListHome.adapter = listHomeAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /*
            Gunakan method ini ketika search selesai atau OK
             */
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@HomeActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                startActivity(intent)
            }
            R.id.menu2 -> {
                showToast(resources.getString(R.string.menu))
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, ListActivity::class.java))
                }, 2000)
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}