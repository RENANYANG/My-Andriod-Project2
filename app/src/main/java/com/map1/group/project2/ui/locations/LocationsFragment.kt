package com.map1.group.project2.ui.locations

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.map1.group.project2.Constants.TAG
import com.map1.group.project2.LocationAdapter
import com.map1.group.project2.LocationItem
import com.map1.group.project2.R
import com.map1.group.project2.databinding.FragmentLocationsBinding
import java.lang.Exception

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val locationsViewModel = ViewModelProvider(this).get(LocationsViewModel::class.java)

        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textLocations
//        locationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


        val locationList = this.getLocationList()
        val locationAdapter = locationList?.let { LocationAdapter(it) }
        locationAdapter?.notifyDataSetChanged()
        binding.locationRecycler.adapter = locationAdapter
        binding.locationRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)






        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getLocationList(): ArrayList<LocationItem>? {
        val sharedPref = activity?.getSharedPreferences(getString(R.string.shared_file_saved_locations), Context.MODE_PRIVATE)
        val locationInfoStr = sharedPref?.getString(getString(R.string.shared_key_saved_locations), null)

        try {
            val listType = object : TypeToken<ArrayList<LocationItem>>() {}.type
            return Gson().fromJson(locationInfoStr, listType)
        } catch (e: Exception){
            return ArrayList<LocationItem>()
        }
    }
}