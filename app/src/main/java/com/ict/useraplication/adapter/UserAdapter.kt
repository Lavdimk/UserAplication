package com.ict.useraplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.ict.useraplication.R
import com.ict.useraplication.api.ServiceAPI
import com.ict.useraplication.model.User
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class   UserAdapter( var listOfUsers:List<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvId:TextView=itemView.findViewById(R.id.tvId)
        val tvEmail:TextView=itemView.findViewById(R.id.tvEmail)
        val tvFirstName:TextView=itemView.findViewById(R.id.tvFirstName)
        val tvLastname:TextView=itemView.findViewById(R.id.tvLastName)
        val image:ImageView=itemView.findViewById(R.id.imageView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.user_details,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listOfUsers[position]
        holder.tvId.text = user.id.toString()
        holder.tvEmail.text = user.email
        holder.tvFirstName.text = user.first_name
        holder.tvLastname.text = user.last_name
        Picasso.get().load(user.avatar).into(holder.image)

        holder.itemView.setOnClickListener {
            val userId = user.id
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val serviceAPI = retrofit.create(ServiceAPI::class.java)

            if (userId != null) {
                serviceAPI.deleteUser(userId).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            Toast.makeText(holder.itemView.context, "Useri u fshi me sukses", Toast.LENGTH_SHORT).show()
                            removeUser(user)
                        }
                    }

                    private fun removeUser(user: User) {
                        listOfUsers = listOfUsers.filter { it != user }
                        notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(holder.itemView.context, "Useri nuk u fshi me sukses", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }

    }
    override fun getItemCount(): Int {
       return listOfUsers.size
    }

}