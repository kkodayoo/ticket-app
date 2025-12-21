package com.example.toucan_vinyl.User.Payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toucan_vinyl.Data.entity.PaymentEntity
import com.example.toucan_vinyl.databinding.ItemPaymentBinding

class PaymentAdapter(
    private var items: List<PaymentEntity>,
    private val onDelete: (PaymentEntity) -> Unit
) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    inner class PaymentViewHolder(val binding: ItemPaymentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding = ItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val item = items[position]

        val maskedNumber = "**** **** **** " + item.cardNumber.takeLast(4)

        holder.binding.tvTitle.text = item.cardHolder
        holder.binding.tvContent.text = maskedNumber

        holder.binding.btnDelete.setOnClickListener {
            onDelete(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newList: List<PaymentEntity>) {
        items = newList
        notifyDataSetChanged()
    }
}
