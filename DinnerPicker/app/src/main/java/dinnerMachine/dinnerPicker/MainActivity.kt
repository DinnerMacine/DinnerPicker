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
        upperView.startViewTransition(View.inflate(this, R.layout.layout_tab_home, upperView))

        Log.d("@@@ TAB: ", "BEFORE TAB")

        bottomNavigation.setOnNavigationItemSelectedListener {
            upperView.removeAllViews()

            var selected: Int

            when (it.itemId) {
                R.id.tab_map -> {
                    selected = R.layout.layout_tab_map
                    Log.d("@@@ TAB: ", "SELECTED MAP")
                    true }
                R.id.tab_setting -> {
                    selected = R.layout.layout_tab_setting
                    true }
                else -> {
                    selected = R.layout.layout_tab_home
                    true
                }
            }

            var setMe : View = View.inflate(this, selected, upperView)
            upperView.startViewTransition(setMe)
            true
        }

    }


}