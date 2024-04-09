package com.map1.group.project2.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.map1.group.project2.R

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private var prevMarker: Marker? = null

    companion object {
        const val DEFAULT_ZOOM: Float = 12f
        const val LONDON_LAT: Double = 42.9849
        const val LONDON_LONG: Double = 81.24176 * -1
    }

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

        mMap = googleMap
        val london = LatLng(LONDON_LAT, LONDON_LONG)
        prevMarker = mMap.addMarker(MarkerOptions().position(london).title("Marker in London"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, DEFAULT_ZOOM))

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isScrollGesturesEnabled = true
            isMapToolbarEnabled = true
        }

        onMapClicked()
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

    private fun onMapClicked() {
        Log.d("y_song", "onMapClicked")

        mMap.setOnMapClickListener {
            Log.d("y_song", "onMapClickListener")

            val location = LatLng(it.latitude, it.longitude)

            prevMarker?.remove()
            prevMarker = mMap.addMarker(MarkerOptions().position(location)
                .title("Point")
                .snippet(location.toString())
            )
        }

        mMap.setOnMarkerClickListener { marker ->
            Log.d("y_song", "debug-----------1")
            Log.d("y_song", marker.position.toString())

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Edit Marker Title")

            Log.d("y_song", "debug-----------2")
            val input = EditText(requireContext())
            input.setText(marker.title)
            builder.setView(input)


            Log.d("y_song", "debug-----------3")
            builder.setPositiveButton("OK") { dialog, which ->
                val newTitle = input.text.toString()
                marker.title = newTitle
                marker.showInfoWindow()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            Log.d("y_song", "debug-----------4")

            true
        }
    }
}