package com.example.android.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.databinding.RecyclerviewRowBinding
import com.example.android.navigation.db.UserEntity

class RecyclerViewAdapter(private val listener: RowClickListener): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    var items  = ArrayList<UserEntity>()

    fun setListData(data: ArrayList<UserEntity>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerviewRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])
        }
        holder.bind(items[position])

    }



    class MyViewHolder(
        val binding: RecyclerviewRowBinding,
        private val listener: RowClickListener
    ): RecyclerView.ViewHolder(binding.root) {

        private val tvName = binding.tvName
        private val tvEmail = binding.tvEmail
        private val tvPhone = binding.tvPhone
        private val deleteUserID = binding.deleteUserID

        fun bind(data: UserEntity) {
            tvName.text = data.name

            tvEmail.text = data.email


            tvPhone.text = data.phone

            deleteUserID.setOnClickListener {
                listener.onDeleteUserClickListener(data)
            }
        }
    }

    interface RowClickListener{


        fun onDeleteUserClickListener(user: UserEntity)
        fun onItemClickListener(user: UserEntity)
    }
}