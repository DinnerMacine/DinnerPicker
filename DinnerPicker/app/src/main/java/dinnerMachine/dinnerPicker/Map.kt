package dinnerMachine.dinnerPicker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Map : Fragment(), OnMapReadyCallback {
    lateinit var mapView: MapView
  override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.layout_tab_map, container, false)
        mapView = view.findViewById(R.id.map)
        mapView.getMapAsync(this)

        Log.v("onCreateView", "CreateView")
        return view
    }

    override fun onStart() {
        Log.v("onStart", "Start")
        mapView.onStart()
        super.onStart()
    }

    override fun onStop() {
        Log.v("onStop", "Stop")
        mapView.onStop()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.v("onSaveInstanceState", "SaveInstanceState")
        mapView.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        Log.v("onResume", "Resume")
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        Log.v("onPause", "Pause")
        mapView.onPause()
        super.onPause()
        }

    override fun onLowMemory() {
        Log.v("onLowMemory", "LowMemory")
        mapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onDestroy() {
        Log.v("onDestroy", "Destroy")
        mapView.onLowMemory()
        super.onDestroy()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.v("onActivityCreated", "ActivityCreated")
//액티비티가 처음 생성될 때 실행되는 함수
        if (mapView != null) {
            mapView.onCreate(savedInstanceState)
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        Log.v("됐나?", "됐다면 출력해 주세요!")
        val seoul = LatLng(37.563826, 127.041951)
        val marker = MarkerOptions()
            .position(seoul)
            .title("잉쟁이의 집")
            .snippet("열려라 잉! 하고 소리치면 열립니다... 아마...?")
        p0!!.addMarker(marker)
        p0!!.moveCamera(CameraUpdateFactory.newLatLng(seoul))
        p0!!.animateCamera(CameraUpdateFactory.zoomTo(12f))
        Log.v("마커 추가 햇는데 됬나..?", "됐나요!?!?!")
    }
}


