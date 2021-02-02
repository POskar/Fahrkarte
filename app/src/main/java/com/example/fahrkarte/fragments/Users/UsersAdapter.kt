package com.example.fahrkarte.fragments.Users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fahrkarte.R
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.databinding.ItemTicketBinding
import com.example.fahrkarte.databinding.ItemUserBinding
import java.io.IOException

private var _binding: ItemUserBinding? = null
private val binding get() = _binding!!

open class UsersAdapter(private var fragment: Fragment, private var list: ArrayList<User>): RecyclerView.Adapter<UsersAdapter.MyViewHolder>(){

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        if(holder is MyViewHolder){
            try {
                Glide
                        .with(fragment)
                        .load(model.image)
                        .centerCrop()
                        .placeholder(R.drawable.round_profile_icon)
                        .into(binding.ivUserImage)
            }catch(e: IOException){
                e.printStackTrace()
            }
            binding.tvName.text = model.name
            binding.tvDepartment.text = model.department
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener{
        fun onClick(position: Int, model: Ticket)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    class MyViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}