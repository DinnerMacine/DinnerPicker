package dinnerMachine.dinnerPicker

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.fondesa.kpermissions.extension.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import dinnerMachine.dinnerPicker.Map as Map
import dinnerMachine.dinnerPicker.Home as Home
import dinnerMachine.dinnerPicker.Setting as Setting


class MainActivity : AppCompatActivity() /*, BottomNavigationView.OnNavigationItemSelectedListener  */{
    lateinit var ft: FragmentTransaction
    fun changeFragment(f:Fragment, cleanStack:Boolean=false){
        //         val ft = View = View.inflate(this, )
        ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.tab_frame,f)
        ft.addToBackStack(null)
        ft.commit()

        true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.tab)
        val upperView = findViewById<FrameLayout>(R.id.tab_frame)
        upperView.startViewTransition(View.inflate(this, R.layout.layout_tab_home, upperView))

        bottomNavigation.setOnNavigationItemSelectedListener {
            upperView.removeAllViews()

            var selected: Fragment

            when (it.itemId) {
                R.id.tab_map -> {
                    selected = Map()
                    true }
                R.id.tab_setting -> {
                    selected = Setting()
                    true }
                else -> {
                    selected = Home()
                    true
                }
            }
            if(selected != null){
                changeFragment(selected)
            }

            true
            /*
            var setMe : View = View.inflate(this, selected, upperView)
            upperView.startViewTransition(setMe)
            true

*/
        }


    }


}