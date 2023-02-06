package com.ict.useraplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ict.useraplication.api.ServiceAPI
import com.ict.useraplication.databinding.FragmentCreateUserBinding
import com.ict.useraplication.model.CreateUser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateUserFragment : Fragment() {
    private lateinit var binding: FragmentCreateUserBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btncreateUser.setOnClickListener {

            val name = binding.etName.text.toString()
            val job = binding.etJob.text.toString()


            if (name.isNullOrEmpty() || job.isNullOrEmpty() ) {
                Toast.makeText(requireContext(), "Emri dhe puna jane fushat e detyrueshme", Toast.LENGTH_SHORT).show()
            } else {
                val newUser = CreateUser(name, job)
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://reqres.in/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val retrofitInstance = retrofit.create(ServiceAPI::class.java)
                val createUserCall =retrofitInstance.createUser(newUser)
                createUserCall.enqueue(object : Callback<CreateUser> {
                    override fun onResponse(
                        call: Call<CreateUser>,
                        response: Response<CreateUser>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Useri u regjistrua me sukses", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<CreateUser>, t: Throwable) {
                        Toast.makeText(requireContext(), "Useri nuk u regjistrua me sukses", Toast.LENGTH_SHORT).show()
                    }
                })


            }
        }
}}