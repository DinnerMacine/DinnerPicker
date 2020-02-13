package dinnerMachine.dinnerPicker

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.Scene
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : Activity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val bottomTap = findViewById<BottomNavigationView>(R.id.tab)
        bottomTap.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tab_map -> { return true }
            R.id.tab_home -> { return true }
            R.id.tab_setting -> { return true }
        }
        return false
    }

}