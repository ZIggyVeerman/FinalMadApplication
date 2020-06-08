package com.example.pickup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup.R
import com.example.pickup.model.PackageItem
import com.example.pickup.model.PackageOverviewResponse
import kotlinx.android.synthetic.main.owner_package_item.view.*

class OwnerPackageAdapter(
    private val overview: ArrayList<PackageOverviewResponse>
) : RecyclerView.Adapter<OwnerPackageAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OwnerPackageAdapter.ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.owner_package_item, parent, false)
        )
    }

    override fun getItemCount(): Int = overview.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(overview[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(packageItem: PackageOverviewResponse) {
            itemView.tvPickupPostal.text = packageItem.postalCode
            itemView.tvPickupHomeNumber.text = packageItem.homeNumber.toString()
            packageItem.packages.forEach { element ->
                itemView.tvPackageName.text = element.packageName
                itemView.tvPickUpTime.text = element.pickUpTime.toString()
            }
        }
    }

}