package dinnerMachine.dinnerPicker

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import dinnerMachine.dinnerPicker.Home
import dinnerMachine.dinnerPicker.Map
import dinnerMachine.dinnerPicker.Setting
import java.io.IOException
import java.security.AccessController.getContext
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() /*, BottomNavigationView.OnNavigationItemSelectedListener  */{
    lateinit var ft: FragmentTransaction
    val multiplePermissionsCode = 100
    val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE
    )
    var isPermission = arrayOf(false,false,false,false)
    var isPermissions = false
    lateinit var locaInfo : LocationInfo

    fun checkPermissions(){
        var rejectedPermissionList = ArrayList<String>()

        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED)
                rejectedPermissionList.add(permission)
        }

        if(rejectedPermissionList.isNotEmpty()){
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this,rejectedPermissionList.toArray(array),multiplePermissionsCode)
        }
        else{
            isPermissions = true
        }
    }

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
        upperView.setOnClickListener(View.OnClickListener(){

           fun onClick(view:View){
               if(!isPermissions){
                   checkPermissions()
                   return
               }
               locaInfo = LocationInfo(this)
               if(locaInfo.isGetLocation){
                   var latitude = locaInfo.getLat()
                   var longitude = locaInfo.getLon()

                   var gCoder = Geocoder(this, Locale.getDefault())
                   var addr: List<Address>? = null
                   try{
                       addr = gCoder.getFromLocation(latitude,longitude,1)
                       var a = addr.get(0)



                       for(i in 0  .. a.getMaxAddressLineIndex()){
                           Log.v("알림","AddressLine("+i+")"+a.getAddressLine(i)+"\n")
                       }
                   }catch(e: IOException){
                       e.printStackTrace()
                   }
                   if(addr != null){
                       if(addr.size==0){
                           Toast.makeText(this,"주소정보 없음",Toast.LENGTH_LONG).show()
                       }
                   }
               } else{
                   locaInfo.showSettingsAlert()
               }
           }
        })

        checkPermissions()
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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        isPermissions = true
        when(requestCode){
            multiplePermissionsCode ->{
                if(grantResults.isNotEmpty()){
                    for((i,permission) in permissions.withIndex()){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Log.i("실패!","사용자가 $permission 의 권한을 거절했습니다!")
                        }
                        else{
                            isPermission[i] = true
                        }
                        isPermissions = isPermission[i] || isPermissions
                    }
                }
            }
        }
    }
}