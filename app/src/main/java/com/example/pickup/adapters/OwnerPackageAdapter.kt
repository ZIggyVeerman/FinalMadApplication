package com.example.pickup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup.R
import com.example.pickup.model.PackageOverviewResponse
import kotlinx.android.synthetic.main.owner_package_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OwnerPackageAdapter(
    private val overview: ArrayList<PackageOverviewResponse>,
    private val yesClick: ((PackageOverviewResponse) -> Unit),
    private val noClick: ((PackageOverviewResponse) -> Unit)
) : RecyclerView.Adapter<OwnerPackageAdapter.ViewHolder>() {

    private lateinit var context: Context
    private val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.owner_package_item, parent, false)
        )
    }

    override fun getItemCount(): Int = overview.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(overview[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.btYes.setOnClickListener {
                it.setOnClickListener {
                    yesClick(overview[adapterPosition])
                }
            }

            itemView.btNo.setOnClickListener {
                it.setOnClickListener {
                    noClick(overview[adapterPosition])
                }
            }
        }


        fun bind(packageItem: PackageOverviewResponse) {
            itemView.tvPickupPostal.text =
                context.getString(R.string.PostalCodeOwner, packageItem.postalCode)
            itemView.tvPickupHomeNumber.text =
                context.getString(R.string.HomeNumberOwner, packageItem.homeNumber.toString())
            packageItem.packages.forEach { element ->
                itemView.tvPackageName.text =
                    context.getString(R.string.PackageName, element.packageName)
                itemView.tvPickUpTime.text =
                    context.getString(
                        R.string.PickupReceivedTime,
                        format.format(element.pickUpTime)
                    )
            }
        }
    }

}