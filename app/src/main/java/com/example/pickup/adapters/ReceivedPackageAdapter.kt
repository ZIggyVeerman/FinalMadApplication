package com.example.pickup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup.R
import com.example.pickup.model.PackageItem
import kotlinx.android.synthetic.main.received_package_item.view.*
import java.text.SimpleDateFormat

import java.util.*

class ReceivedPackageAdapter(
    private val packages: List<PackageItem>,
    private val Onclick: ((PackageItem) -> Unit)
) : RecyclerView.Adapter<ReceivedPackageAdapter.ViewHolder>() {

    private lateinit var context: Context
    private val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

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
            itemView.tvPackageName.text =
                itemView.context.getString(R.string.PackageName, packageItem.packageName)
            itemView.tvOwnerPostalCode.text =
                itemView.context.getString(R.string.PostalCodeReceived, packageItem.ownerPostalCode)
            itemView.tvOwnerHomeNumber.text = itemView.context.getString(
                R.string.HomeNumberReceived,
                packageItem.ownerHomeNumber.toString()
            )
            println(packageItem.pickUpTime.toString())

            itemView.tvPickUpTime.text = itemView.context.getString(
                R.string.PickupReceivedTime,
                format.format(packageItem.pickUpTime)
            )
            if (!packageItem.willPickUp) {
                itemView.tfCanMakePickup.text = itemView.context.getString(
                    R.string.willPickUp, "No"
                )
            } else {
                itemView.tfCanMakePickup.text = itemView.context.getString(
                    R.string.willPickUp, "Yes"
                )
            }
        }
    }


}