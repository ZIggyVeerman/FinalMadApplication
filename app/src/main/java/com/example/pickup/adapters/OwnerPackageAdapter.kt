package com.example.pickup.adapters

import android.content.Context
import android.view.*
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup.R
import com.example.pickup.model.PackageOverviewResponse
import kotlinx.android.synthetic.main.owner_package_item.view.*
import java.text.SimpleDateFormat
import java.util.*


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
            itemView.btYes.setOnTouchListener(View.OnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> yesClick(overview[adapterPosition])
                }

                return@OnTouchListener v?.onTouchEvent(event) ?: true
            })

            itemView.btNo.setOnTouchListener(View.OnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> noClick(overview[adapterPosition])
                }

                return@OnTouchListener v?.onTouchEvent(event) ?: true
            })
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
                if (element.willPickUp) {
                    itemView.btNo.visibility = View.GONE
                    itemView.btYes.visibility = View.GONE
                    itemView.tfCanMakePickup.text = context.getString(R.string.ForgetPickup)
                } else {
                    itemView.btNo.visibility = View.VISIBLE
                    itemView.btYes.visibility = View.VISIBLE
                }
            }
        }
    }

}