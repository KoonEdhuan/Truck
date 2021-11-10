package com.example.truck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filter.FilterResults
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.truck.dataModel.Data
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private var data : List<Data>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), Filterable {

    private lateinit var truckNumberData : ArrayList<String>
    lateinit var truckList : ArrayList<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {

        // truck number
        holder.truck_number.setText(data.get(position).truckNumber)

        //state of truck
        // 0 for stopped. 1 for running
        if(data[position].lastRunningState.truckRunningState == 0){
            holder.running_stopped_state.text = "Stopped"
        }
        else{
            holder.running_stopped_state.text = "Running"
        }

        //speed of the truck
        if (data[position].lastWaypoint.speed == 0.0){
            holder.truck_speed.text = null
        }
        else{
            holder.truck_speed.text = "${data[position].lastWaypoint.speed.toString()} k/h"
        }

        val sdf = SimpleDateFormat("dd/MM/yy, hh:mm a")
        val createDate = Date(data[position].lastWaypoint.createTime)
        holder.last_updated.text =sdf.format(createDate)
//        holder.last_updated.text = createDate.toString()

        //CalculateDateDifference(data[position].lastWaypoint.createTime)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        var truck_number : TextView = view.findViewById(R.id.truck_number)
        var last_updated : TextView = view.findViewById(R.id.last_updated)
        var running_stopped_state : TextView = view.findViewById(R.id.running_stopped_state)
        var truck_speed : TextView = view.findViewById(R.id.truck_speed)

    }

    private fun CalculateDateDifference(userDate : Long){

        val sdf = SimpleDateFormat("dd/MM/yy, hh:mm a")
        var currentDT = sdf.format(Date())

        var diff_DT =  currentDT.toInt() - userDate.toInt()
        val final = sdf.format(diff_DT)


    }

    override fun getFilter(): Filter {
        data.forEach {
            truckNumberData.add(it.truckNumber)
        }
        return filter
    }
    private var filter : Filter = object : Filter(){
        override fun performFiltering(charSequence: CharSequence): FilterResults? {
            var filteredTruckData : ArrayList<String>? = null
            if (charSequence.isEmpty()){
                filteredTruckData?.addAll(truckNumberData)
            }else{
                for (element in truckNumberData){
                    if (element.lowercase().contains(charSequence.toString().lowercase())){
                        filteredTruckData?.add(element)
                    }
                }
            }

            var filterResults : FilterResults? = null
            filterResults?.values = filteredTruckData
            return  filterResults
        }

        override fun publishResults(p0: CharSequence, filterResults: FilterResults?) {
            truckList.addAll(filterResults?.values as Collection<String>)
            notifyDataSetChanged()
        }

    }

//    var filter: Filter = object : Filter() {
//        override fun performFiltering(charSequence: CharSequence): FilterResults {
//            return null
//        }
//
//        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {}
//    }





}