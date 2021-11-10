package com.example.truck

import com.example.truck.dataModel.Model
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/tt/mobile/logistics/searchTrucks?auth-company=PCH&companyId=33&deactivated=false&key=g2qb5jvucg7j8skpu5q7ria0mu&q-expand=true&q-include=lastRunningState,lastWaypoint")
    fun getDetails() : Call<Model>
}