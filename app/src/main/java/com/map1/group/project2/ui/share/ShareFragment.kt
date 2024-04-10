package com.map1.group.project2.ui.share

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.map1.group.project2.Constants.TAG
import com.map1.group.project2.LocationItem
import com.map1.group.project2.R
import com.map1.group.project2.databinding.FragmentShareBinding
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShareFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShareFragment : Fragment() {
    private lateinit var binding: FragmentShareBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShareBinding.inflate(inflater, container, false)

        val locationList = this.getLocationList()
        val items = ArrayList<String>()

        locationList?.forEach {
            items.add("[${it.lat}/${it.lng}] ${it.name} ")
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLocation.adapter = adapter
        binding.spinnerLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLocation = getLocationByIndex(position)
                Log.d(TAG, selectedLocation.toString())

                binding.locationId.text = selectedLocation?.id
                binding.locationName.text = selectedLocation?.name
                binding.locationLat.text = selectedLocation?.lat
                binding.locationLng.text = selectedLocation?.lng
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected")
            }
        }

        //return inflater.inflate(R.layout.fragment_share, container, false)
        return binding.root
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

    private fun getLocationByIndex(index: Int): LocationItem? {
        return this.getLocationList()?.get(index)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShareFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShareFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}