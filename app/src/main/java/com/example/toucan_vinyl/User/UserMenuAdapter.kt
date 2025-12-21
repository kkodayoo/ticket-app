package com.example.toucan_vinyl.User.Payment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toucan_vinyl.Data.Model.MenuItem
import com.example.toucan_vinyl.R

class UserMenuAdapter(
    private val menuList: List<MenuItem>,
    private val onClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<UserMenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.iconMenu)
        val title: TextView = view.findViewById(R.id.txtMenuTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_profile, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuList[position]
        holder.title.text = item.title
        holder.icon.setImageResource(item.icon)

        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = menuList.size
}
