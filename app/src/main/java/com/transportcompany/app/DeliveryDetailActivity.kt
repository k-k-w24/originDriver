package com.transportcompany.app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.transportcompany.app.databinding.ActivityDeliveryDetailBinding
import com.transportcompany.app.models.Delivery
import com.transportcompany.app.models.DeliveryStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DeliveryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryDetailBinding
    private var currentDelivery: Delivery? = null

    companion object {
        private const val TAG = "DeliveryDetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            Log.d(TAG, "onCreate started")
            binding = ActivityDeliveryDetailBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Log.d(TAG, "Layout inflated successfully")

            try {
                setSupportActionBar(binding.toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = getString(R.string.delivery_detail_title)
                Log.d(TAG, "Toolbar set successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error setting toolbar", e)
            }

            val deliveryId = intent.getStringExtra("delivery_id")
            Log.d(TAG, "Delivery ID: $deliveryId")
            if (deliveryId != null) {
                try {
                    loadDeliveryDetails(deliveryId)
                } catch (e: Exception) {
                    Log.e(TAG, "Error loading delivery details", e)
                    Toast.makeText(this, "配送情報の読み込みに失敗しました", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.w(TAG, "No delivery ID provided")
                Toast.makeText(this, "配送情報が見つかりません", Toast.LENGTH_SHORT).show()
            }

            try {
                binding.updateStatusButton.setOnClickListener {
                    showStatusUpdateDialog()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error setting up update button", e)
            }

            Log.d(TAG, "onCreate completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            Log.e(TAG, "Error message: ${e.message}")
            Log.e(TAG, "Error stack trace: ${e.stackTraceToString()}")
            e.printStackTrace()
            try {
                Toast.makeText(this, "エラーが発生しました: ${e.message}", Toast.LENGTH_LONG).show()
            } catch (toastError: Exception) {
                Log.e(TAG, "Could not show toast", toastError)
            }
            finish()
        }
    }

    private fun loadDeliveryDetails(deliveryId: String) {
        // サンプルデータ（実際のアプリではAPIから取得）
        currentDelivery = getSampleDelivery(deliveryId)
        currentDelivery?.let { delivery ->
            displayDeliveryInfo(delivery)
        }
    }

    private fun getSampleDelivery(id: String): Delivery {
        // 簡易的なサンプルデータ
        return Delivery(
            id = id,
            packageNumber = "PKG-2025-00$id",
            recipientName = "山田太郎",
            deliveryAddress = "東京都渋谷区1-2-3 マンション101",
            status = DeliveryStatus.PENDING,
            deliveryDate = Date(),
            driverName = "佐藤一郎",
            phoneNumber = "090-1234-5678",
            notes = "不在の場合は再配達をお願いします"
        )
    }

    private fun displayDeliveryInfo(delivery: Delivery) {
        try {
            Log.d(TAG, "Displaying delivery info for: ${delivery.packageNumber}")
            binding.packageNumberText.text = delivery.packageNumber
            binding.recipientNameText.text = delivery.recipientName
            binding.deliveryAddressText.text = delivery.deliveryAddress
            binding.driverNameText.text = delivery.driverName ?: "未割り当て"
            binding.phoneNumberText.text = delivery.phoneNumber ?: "未登録"
            binding.statusText.text = getStatusText(delivery.status)
            binding.statusText.setTextColor(getStatusColor(delivery.status))

            val dateFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.JAPANESE)
            binding.deliveryDateText.text = delivery.deliveryDate?.let { dateFormat.format(it) } ?: "未設定"

            delivery.notes?.let {
                binding.notesText.text = it
            } ?: run {
                binding.notesText.text = "特記事項なし"
            }
            Log.d(TAG, "Delivery info displayed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error displaying delivery info", e)
            throw e
        }
    }

    private fun getStatusText(status: DeliveryStatus): String {
        return when (status) {
            DeliveryStatus.PENDING -> getString(R.string.status_pending)
            DeliveryStatus.IN_TRANSIT -> getString(R.string.status_in_transit)
            DeliveryStatus.DELIVERED -> getString(R.string.status_delivered)
            DeliveryStatus.FAILED -> getString(R.string.status_failed)
        }
    }

    private fun getStatusColor(status: DeliveryStatus): Int {
        return when (status) {
            DeliveryStatus.PENDING -> getColor(R.color.status_pending)
            DeliveryStatus.IN_TRANSIT -> getColor(R.color.status_in_transit)
            DeliveryStatus.DELIVERED -> getColor(R.color.status_delivered)
            DeliveryStatus.FAILED -> getColor(R.color.status_failed)
        }
    }

    private fun showStatusUpdateDialog() {
        val statuses = arrayOf(
            getString(R.string.status_pending),
            getString(R.string.status_in_transit),
            getString(R.string.status_delivered),
            getString(R.string.status_failed)
        )

        AlertDialog.Builder(this)
            .setTitle("配送状況を更新")
            .setItems(statuses) { _, which ->
                val newStatus = when (which) {
                    0 -> DeliveryStatus.PENDING
                    1 -> DeliveryStatus.IN_TRANSIT
                    2 -> DeliveryStatus.DELIVERED
                    3 -> DeliveryStatus.FAILED
                    else -> DeliveryStatus.PENDING
                }
                updateDeliveryStatus(newStatus)
            }
            .show()
    }

    private fun updateDeliveryStatus(newStatus: DeliveryStatus) {
        currentDelivery?.let { delivery ->
            try {
                Log.d(TAG, "Updating delivery status to: $newStatus")
                val updatedDelivery = delivery.copy(status = newStatus)
                currentDelivery = updatedDelivery
                displayDeliveryInfo(updatedDelivery)
                
                // 実際のアプリではAPI呼び出し
                Toast.makeText(this, "配送状況を更新しました", Toast.LENGTH_SHORT).show()
                
                // 更新後に確認ダイアログを表示
                showBackToListDialog()
            } catch (e: Exception) {
                Log.e(TAG, "Error updating delivery status", e)
                Toast.makeText(this, "更新に失敗しました", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun showBackToListDialog() {
        AlertDialog.Builder(this)
            .setTitle("配送状況を更新しました")
            .setMessage("一覧画面に戻りますか？")
            .setPositiveButton("戻る") { _, _ ->
                finish()
            }
            .setNegativeButton("このまま") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        try {
            finish()
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error in onSupportNavigateUp", e)
            return false
        }
    }
}

