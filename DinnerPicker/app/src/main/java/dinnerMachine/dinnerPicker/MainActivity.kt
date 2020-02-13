package dinnerMachine.dinnerPicker

import android.app.ActionBar
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : Activity() /*, BottomNavigationView.OnNavigationItemSelectedListener  */{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("@@@", " MAIN")
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.tab)
        val upperView = findViewById<FrameLayout>(R.id.tab_frame)

        Log.d("@@@ TAB: ", "BEFORE TAB")

        bottomNavigation.setOnNavigationItemSelectedListener {
            Log.d("@@@ TAB: ", "HI")
            var selected: Fragment
            when (it.itemId) {
                R.id.tab_map -> {
                    selected = Map()
                    Log.d("@@@ TAB: ", "SELECTED MAP")
                    true }
                R.id.tab_setting -> {
                    selected = Setting()
                    true }
                else -> {
                    selected = Home()
                    true
                }
            }

            var setMe : View = View.inflate(this, R.layout.layout_tab_map, upperView)
            upperView.startViewTransition(setMe)
            true
        }

    }


}