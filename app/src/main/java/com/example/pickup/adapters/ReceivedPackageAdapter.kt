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
    private val format = SimpleDateFormat("EEE MMM dd HH:mm:ss zXXX yyyy", Locale.ENGLISH)
    private val format1 = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())


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
//            val date: Date = packageItem.pickUpTime
//            val string: String = packageItem.pickUpTime.toString()
//            println(date)
//            val d = format.parse("Wed Mar 30 00:00:00 GMT+05:30 2016")
//            format1.parse(d.toString())

            itemView.tvPickUpTime.text = itemView.context.getString(
                R.string.PickupReceivedTime,
                packageItem.pickUpTime
            )
            itemView.tfCanMakePickup.text = packageItem.willPickUp.toString()
        }
    }


}