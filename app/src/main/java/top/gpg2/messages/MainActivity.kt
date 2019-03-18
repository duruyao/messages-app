package top.gpg2.messages

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(bottomAppBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_bottom_action, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_new_conversation -> toast("New Message Activity")
            R.id.action_settings -> toast("Settings Activity")
            android.R.id.home -> {
                val navigationSheetFragment = NavigationSheetFragment()
                navigationSheetFragment.show(supportFragmentManager, navigationSheetFragment.tag)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun toast(text: CharSequence) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.show()
    }
}
