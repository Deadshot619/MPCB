package com.example.mpcb.reports.hazardious_waste_management

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.R
import kotlinx.android.synthetic.main.item_hazardious_reports.view.*

class CustomAdapter(val reportItems:ArrayList<String>,val context: Context):
    RecyclerView.Adapter<ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvReports?.text = reportItems.get(position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hazardious_reports,
                parent,
                false
            ))
    }

    override fun getItemCount(): Int {
        return reportItems.size

    }


}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tvReports = view.report_text
}