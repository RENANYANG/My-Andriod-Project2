package com.map1.group.project2.ui.maps

import android.app.AlertDialog
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.map1.group.project2.Constants.DEFAULT_ZOOM
import com.map1.group.project2.Constants.LONDON_LAT
import com.map1.group.project2.Constants.LONDON_LONG
import com.map1.group.project2.Constants.TAG
import com.map1.group.project2.R

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private lateinit var button: Button
    private var prevMarker: Marker? = null

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

        button = view.findViewById(R.id.save_location)
        button.setOnClickListener{
            this.showInputDialog()
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun onMapClicked() {
        Log.d(TAG, "onMapClicked")

        mMap.setOnMapClickListener {
            Log.d(TAG, "onMapClickListener")

            val location = LatLng(it.latitude, it.longitude)
            prevMarker?.remove()
            prevMarker = mMap.addMarker(MarkerOptions().position(location)
                .title("Point")
                .snippet(String.format("Lat/Lng: (%.4f, %.4f)", it.latitude, it.longitude))
            )
        }
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(requireContext())

        val strLat:String = String.format("%.4f", prevMarker?.position?.latitude)
        val strLng:String = String.format("%.4f", prevMarker?.position?.longitude)

        builder.setTitle("* Lat: $strLat\n* Lng: $strLng")

        val view = layoutInflater.inflate(R.layout.dialog_save_location, null)
        val editText = view.findViewById<EditText>(R.id.edit_text_input)

        builder.setView(view)

        builder.setPositiveButton("OK") { dialog, which ->
            val inputLocation = editText.text.toString()
            if (inputLocation.isEmpty()) {
                Toast.makeText(requireContext(), "Location name is null", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }
}