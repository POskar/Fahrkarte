package com.example.fahrkarte.data.models

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.fahrkarte.R

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?, position: Int,
                id: Long
        ) {
            when(position){
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}

    }

    private val statuses: HashMap<String, Int> = hashMapOf(
        "Open" to 0,
        "Waiting" to 0,
        "Closed" to 3
    )

    private val priorities: HashMap<String, Int> = hashMapOf(
        "High Priority" to 0,
        "Medium Priority" to 1,
        "Low Priority" to 2
    )

    fun sortTickets(tickets: ArrayList<Ticket>): ArrayList<Ticket> {
        val comparator = Comparator { t1: Ticket, t2: Ticket ->
            return@Comparator statuses[t1.status]!! - statuses[t2.status]!! + priorities[t1.priority]!! - priorities[t2.priority]!!
        }
        val copy = arrayListOf<Ticket>().apply { addAll(tickets) }
        copy.sortWith(comparator)
        return copy
    }


}