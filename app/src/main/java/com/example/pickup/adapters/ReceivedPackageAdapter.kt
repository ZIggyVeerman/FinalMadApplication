package com.example.pickup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup.R
import com.example.pickup.model.PackageItem
import kotlinx.android.synthetic.main.received_package_item.view.*

class ReceivedPackageAdapter(
    private val packages: List<PackageItem>,
    private val Onclick: ((PackageItem) -> Unit)
) : RecyclerView.Adapter<ReceivedPackageAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.received_package_item, parent, false)
        )
    }

    override fun getItemCount(): Int = packages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(packages[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { Onclick(packages[adapterPosition]) }
        }

        fun bind(packageItem: PackageItem) {
            itemView.tvPackageName.text = packageItem.packageName
            itemView.tvOwnerPostalCode.text = packageItem.ownerPostalCode
            itemView.tvOwnerHomeNumber.text = packageItem.ownerHomeNumber.toString()
            itemView.tvPickUpTime.text = packageItem.pickUpTime.toString()
            itemView.tvWillPickUp.text = packageItem.willPickUp.toString()
        }
    }


}