package com.map1.group.project2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter(private val itemList: ArrayList<LocationItem>) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.tv_name.text = itemList[position].name
        holder.tv_lat.text = itemList[position].lat
        holder.tv_lng.text = itemList[position].lng
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = itemView.findViewById<TextView>(R.id.location_name)
        val tv_lat = itemView.findViewById<TextView>(R.id.location_lat)
        val tv_lng = itemView.findViewById<TextView>(R.id.location_lng)
    }
}