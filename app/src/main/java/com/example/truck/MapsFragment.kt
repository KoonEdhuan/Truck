package com.example.truck

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.activityViewModels
import com.example.truck.dataModel.Model

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsFragment : Fragment() {

//    private var latitude : ArrayList<Double>? = null
//    private var longitude : ArrayList<Double>? = null

    private val viewModel : SharedViewModel by activityViewModels()

    private lateinit var latitude : List<Double>
    private lateinit var longitude : List<Double>


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

//        viewModel.truckData.forEach{
//            val marker = LatLng(it.lastWaypoint.lat, it.lastWaypoint.lng)
//            val title = it.truckNumber
//            googleMap.addMarker(MarkerOptions().position(marker).title(title))
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
//        }

        viewModel.truckData.forEach{
            // truck is in moving. Green marker
            if (it.lastRunningState.truckRunningState == 1){
                val marker = LatLng(it.lastWaypoint.lat, it.lastWaypoint.lng)
                val title = it.truckNumber
                googleMap.addMarker(MarkerOptions().position(marker).title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                googleMap.moveCamera((CameraUpdateFactory.newLatLng(marker)))
            }
            else if (it.lastRunningState.truckRunningState == 0){
                // truck is stopped and ignition is off. Blue marker
                if (it.lastWaypoint.ignitionOn == false){
                    val marker = LatLng(it.lastWaypoint.lat, it.lastWaypoint.lng)
                    val title = it.truckNumber
                    googleMap.addMarker(MarkerOptions().position(marker).title(title)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                    googleMap.moveCamera((CameraUpdateFactory.newLatLng(marker)))
                }
                // truck is stopped and ignition is on. Yellow marker
                if (it.lastWaypoint.ignitionOn == true){
                    val marker = LatLng(it.lastWaypoint.lat, it.lastWaypoint.lng)
                    val title = it.truckNumber
                    googleMap.addMarker(MarkerOptions().position(marker).title(title)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
                    googleMap.moveCamera((CameraUpdateFactory.newLatLng(marker)))
                }
            }
            else{
                viewModel.truckData.forEach{
                val marker = LatLng(it.lastWaypoint.lat, it.lastWaypoint.lng)
                val title = it.truckNumber
                googleMap.addMarker(MarkerOptions().position(marker).title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.map_icon).isVisible = false
        menu.findItem(R.id.listview_icon).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }
}