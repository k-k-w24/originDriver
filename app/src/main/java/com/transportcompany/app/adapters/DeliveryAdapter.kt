package com.transportcompany.app.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.transportcompany.app.R
import com.transportcompany.app.models.Delivery
import com.transportcompany.app.models.DeliveryStatus
import java.text.SimpleDateFormat
import java.util.Locale

class DeliveryAdapter(
    private val onItemClick: (Delivery) -> Unit
) : ListAdapter<Delivery, DeliveryAdapter.DeliveryViewHolder>(DeliveryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_delivery, parent, false)
        return DeliveryViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DeliveryViewHolder(
        itemView: View,
        private val onItemClick: (Delivery) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val packageNumberText: TextView = itemView.findViewById(R.id.packageNumberText)
        private val recipientNameText: TextView = itemView.findViewById(R.id.recipientNameText)
        private val deliveryAddressText: TextView = itemView.findViewById(R.id.deliveryAddressText)
        private val statusText: TextView = itemView.findViewById(R.id.statusText)
        private val deliveryDateText: TextView = itemView.findViewById(R.id.deliveryDateText)

        fun bind(delivery: Delivery) {
            packageNumberText.text = delivery.packageNumber
            recipientNameText.text = delivery.recipientName
            deliveryAddressText.text = delivery.deliveryAddress
            statusText.text = getStatusText(delivery.status)
            statusText.setTextColor(getStatusColor(delivery.status))

            val dateFormat = SimpleDateFormat("MM/dd HH:mm", Locale.JAPANESE)
            deliveryDateText.text = delivery.deliveryDate?.let { dateFormat.format(it) } ?: "未設定"

            itemView.setOnClickListener {
                onItemClick(delivery)
            }
        }

        private fun getStatusText(status: DeliveryStatus): String {
            return when (status) {
                DeliveryStatus.PENDING -> "未配送"
                DeliveryStatus.IN_TRANSIT -> "配送中"
                DeliveryStatus.DELIVERED -> "配送完了"
                DeliveryStatus.FAILED -> "配送失敗"
            }
        }

        private fun getStatusColor(status: DeliveryStatus): Int {
            return when (status) {
                DeliveryStatus.PENDING -> Color.parseColor("#FF9800")
                DeliveryStatus.IN_TRANSIT -> Color.parseColor("#2196F3")
                DeliveryStatus.DELIVERED -> Color.parseColor("#4CAF50")
                DeliveryStatus.FAILED -> Color.parseColor("#F44336")
            }
        }
    }

    class DeliveryDiffCallback : DiffUtil.ItemCallback<Delivery>() {
        override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem == newItem
        }
    }
}

