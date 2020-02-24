package dinnerMachine.dinnerPicker

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat



class LocationInfo() : Service(), LocationListener, Parcelable {
    lateinit var mContext: Context
    var isGPSEnabled = false
    var isNetworkEnabled = false
    var isGetLocation = false
    private lateinit var location: Location
    var latitude = 37.563826
    var longitude = 127.041951

    val MIN_DISTANCE_CHANGE_FOR_UPDATES : Float = 10f
    val MIN_TIME_BW_UPDATES: Long = 1000 * 60 * 1

    var locationManager: LocationManager? = null

    constructor(parcel: Parcel) : this() {
        isGPSEnabled = parcel.readByte() != 0.toByte()
        isNetworkEnabled = parcel.readByte() != 0.toByte()
        isGetLocation = parcel.readByte() != 0.toByte()
        location = parcel.readParcelable(Location::class.java.classLoader)!!
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
    }

    constructor(context: Context) : this() {
        this.mContext = context
        getLocation()

    }

    @TargetApi(23)
    fun getLocation(): Location? {
        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(
                mContext, Manifest.permission.ACCESS_FINE_LOCATION
            )
                    != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                mContext, Manifest.permission.ACCESS_COARSE_LOCATION
            )
                    != PackageManager.PERMISSION_GRANTED)
        ) {
            return null
        }
        try {
            locationManager = mContext
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            // GPS 정보 가져오기
            isGPSEnabled = locationManager!!
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
            // 현재 네트워크 상태 값 알아오기
            isNetworkEnabled = locationManager!!
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetworkEnabled) { // GPS 와 네트워크사용이 가능하지 않을때 소스 구현
            } else {
                isGetLocation = true
                // 네트워크 정보로 부터 위치값 가져오기
                if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this
                    )

                    if (locationManager != null) {
                        location = locationManager!!
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) { // 위도 경도 저장
                            latitude = location.latitude
                            longitude = location.longitude
                            Log.v("알림", "위도 : " + latitude.toString() + "경도 " + longitude)
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this
                        )
                        if (locationManager != null) {
                            location = locationManager!!
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (location != null) {
                                latitude = location.latitude
                                longitude = location.longitude
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return location
    }

    fun stopUsingGPS(){
        if(locationManager != null) {
            locationManager!!.removeUpdates(this)
        }
    }

    fun getLat(): Double {
        if(location!=null){
            latitude = location.latitude
        }
        return latitude
    }

    fun getLon():Double{
        if(location != null){
            longitude = location.longitude
        }
        return longitude
    }

    fun IsGetLocation(): Boolean {
        return this.isGetLocation
    }

    fun showSettingsAlert(){
        makeDialog()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location?) {
       //
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //
    }

    override fun onProviderEnabled(provider: String?) {
        //.
    }
    override fun onProviderDisabled(provider: String?) {
        //
    }


    fun makeDialog(){
        var alt_bld = AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle)
        alt_bld.setMessage("사용자의 위치정보를 얻어오기 위해 GPS사용이 필요합니다.\n 설정창으로 가시겠습니까?").setCancelable(false)
            .setPositiveButton("네", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                    var intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    mContext.startActivity(intent)
            }).setNegativeButton("아니요", DialogInterface.OnClickListener(){ dialog: DialogInterface, id: Int ->
                dialog.cancel()
            })
        var alert = alt_bld.create()
        alert.setIcon(R.drawable.ic_bob_mini)
        alert.setTitle("'저녁 뭐먹지?'가 GPS 사용 허가를 바랍니다!")
        //alert.getWindow().setBackgroundDrawable(ColorDrawable(Color.argb(255,62,79,92)))
        alert.show()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isGPSEnabled) 1 else 0)
        parcel.writeByte(if (isNetworkEnabled) 1 else 0)
        parcel.writeByte(if (isGetLocation) 1 else 0)
        parcel.writeParcelable(location, flags)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationInfo> {
        override fun createFromParcel(parcel: Parcel): LocationInfo {
            return LocationInfo(parcel)
        }

        override fun newArray(size: Int): Array<LocationInfo?> {
            return arrayOfNulls(size)
        }
    }
}