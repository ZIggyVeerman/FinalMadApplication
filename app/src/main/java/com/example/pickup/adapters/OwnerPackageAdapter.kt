package com.example.pickup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup.R
import com.example.pickup.model.UserWithPackageItem
import kotlinx.android.synthetic.main.owner_package_item.view.*

class OwnerPackageAdapter(
    private val packages: ArrayList<UserWithPackageItem>
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

    override fun getItemCount(): Int = packages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(packages[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(packageItem: UserWithPackageItem) {
            itemView.tvPackageName.text = packageItem.postalCode

            //TODO BIND DINGEN

        }
    }

}