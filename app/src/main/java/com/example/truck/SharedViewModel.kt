package com.example.truck

import androidx.lifecycle.ViewModel
import com.example.truck.dataModel.Data

class SharedViewModel : ViewModel() {

    lateinit var truckData : List<Data>
    lateinit var truckNumbers : ArrayList<String>

    fun SearchData(): ArrayList<String> {
        truckData.forEach{
            truckNumbers.add(it.truckNumber)
        }
        return truckNumbers
    }


}