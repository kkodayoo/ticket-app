package com.example.toucan_vinyl.User.Payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toucan_vinyl.Data.entity.PaymentEntity
import com.example.toucan_vinyl.databinding.ItemPaymentRowBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentListAdapter(
    private var data: List<PaymentEntity>
) : RecyclerView.Adapter<PaymentListAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ItemPaymentRowBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPaymentRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.apply {
            tvCardHolder.text = item.cardHolder
            tvCardNumber.text = maskCardNumber(item.cardNumber)
            tvCreatedAt.text = formatDate(item.createdAt)
        }
    }

    override fun getItemCount(): Int = data.size

    fun updateData(newData: List<PaymentEntity>) {
        data = newData
        notifyDataSetChanged()
    }

    private fun maskCardNumber(number: String): String {
        return if (number.length >= 4) {
            "**** **** **** " + number.takeLast(4)
        } else {
            "****"
        }
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}
