package com.ict.useraplication.ui.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ict.useraplication.R
import com.ict.useraplication.adapter.UserAdapter
import com.ict.useraplication.api.ServiceAPI
import com.ict.useraplication.databinding.FragmentHomeBinding
import com.ict.useraplication.model.Data
import com.ict.useraplication.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment() : Fragment() {
    private lateinit var binding: FragmentHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       makeAPICalls()


    }


    private fun makeAPICalls() {
        val retrofitInstance = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://reqres.in/api/")
            .build()
            .create(ServiceAPI::class.java)
        val getAllUser=retrofitInstance.getAllUsers()
        getAllUser.enqueue(object : Callback<Data?> {
            override fun onResponse(call: Call<Data?>, response: Response<Data?>) {
              val userslist= response.body()?.data
                val adapter= userslist?.let { UserAdapter(it) }
                binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                binding.rvUser.adapter=adapter

                binding.rvUser.setOnClickListener {

                }
            }
            override fun onFailure(call: Call<Data?>, t: Throwable) {
                Log.d(TAG, "onFailure ")

            }
        })

    }
}