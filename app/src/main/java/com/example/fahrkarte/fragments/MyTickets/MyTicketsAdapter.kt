package com.example.fahrkarte.fragments.MyTickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fahrkarte.R
import com.example.fahrkarte.data.models.SharedViewModel
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.databinding.ItemTicketBinding
import com.example.fahrkarte.utility.Constants
import com.google.firebase.firestore.FirebaseFirestore

private var _binding: ItemTicketBinding? = null
private val binding get() = _binding!!

open class MyTicketsAdapter(private var list: ArrayList<Ticket>): RecyclerView.Adapter<MyTicketsAdapter.MyViewHolder>(){

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        if(holder is MyViewHolder){
            binding.tvTicketId.text = model.id
            binding.tvName.text = model.name
            binding.tvCreatedBy.text = model.createdBy // getUsernameUsingID(model.id) //TODO daj mu czas, żeby coroutines
            parsePriorityColor(model.priority)

            holder.itemView.setOnClickListener{
                if(onClickListener != null){
                    onClickListener!!.onClick(position, model)
                }
            }
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

    private fun parsePriorityColor(priority: String){
        when(priority){
            "High Priority" -> { binding.priorityIndicator.setCardBackgroundColor(binding.priorityIndicator.context.getColor(R.color.red)) }
            "Medium Priority"-> { binding.priorityIndicator.setCardBackgroundColor(binding.priorityIndicator.context.getColor(R.color.yellow)) }
            "Low Priority"-> { binding.priorityIndicator.setCardBackgroundColor(binding.priorityIndicator.context.getColor(R.color.green)) }
        }
    }

    private fun getUsername(user: User): String{
        return user.name
    }

    private fun getUsernameUsingID(user_id: String) {

        FirebaseFirestore.getInstance().collection(Constants.TICKETS)
                .whereEqualTo(Constants.CREATEDBY, user_id)
                .get()
                .addOnSuccessListener {
                    document ->
                    val usersList: ArrayList<User> = ArrayList()
                    for(i in document.documents){
                        var user = i.toObject(User::class.java)!!
                        usersList.add(user)
                    }

                    getUsername(usersList[0])
                }.addOnFailureListener{
                }
    }

    class MyViewHolder(binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root)
}