package com.example.truck

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.truck.dataModel.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private var layoutmanager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    private val viewModel : SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.map_icon).isVisible = true
        menu.findItem(R.id.listview_icon).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menu.findItem(R.id.listview_icon).isVisible = false
//        menu.findItem(R.id.map_icon).isVisible = true
//        super.onCreateOptionsMenu(menu, inflater)
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutmanager = LinearLayoutManager(context)
        var recyclerview : RecyclerView= requireView().findViewById(R.id.recyclerview)
        recyclerview.layoutManager = layoutmanager

        val service = RetrofitClientInstance.retrofitInstance?.create(ApiService::class.java)
        val call = service?.getDetails()
        call?.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {

                //adapter = RecyclerAdapter(response.body()!!.data)
                //recyclerview.adapter = adapter
                viewModel.truckData = response.body()!!.data
                adapter = RecyclerAdapter(viewModel.truckData)
                //recyclerview.adapter = adapter
                recyclerview.adapter = RecyclerAdapter(viewModel.truckData)

            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                Log.d("onFailure",t.message.toString())
            }

        })

    }
}