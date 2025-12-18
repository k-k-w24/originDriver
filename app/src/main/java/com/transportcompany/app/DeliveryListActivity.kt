package com.transportcompany.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.transportcompany.app.adapters.DeliveryAdapter
import com.transportcompany.app.databinding.ActivityDeliveryListBinding
import com.transportcompany.app.models.Delivery
import com.transportcompany.app.models.DeliveryStatus
import java.util.Date

class DeliveryListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryListBinding
    private lateinit var deliveryAdapter: DeliveryAdapter
    private var allDeliveries: List<Delivery> = emptyList()

    companion object {
        private const val TAG = "DeliveryListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            Log.d(TAG, "onCreate started")
            binding = ActivityDeliveryListBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Log.d(TAG, "Layout inflated successfully")

            try {
                setSupportActionBar(binding.toolbar)
                supportActionBar?.title = getString(R.string.delivery_list_title)
                Log.d(TAG, "Toolbar set successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error setting toolbar", e)
            }

            try {
                setupRecyclerView()
                Log.d(TAG, "RecyclerView setup completed")
            } catch (e: Exception) {
                Log.e(TAG, "Error setting up RecyclerView", e)
                throw e
            }

            try {
                loadDeliveries()
                Log.d(TAG, "Deliveries loaded successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading deliveries", e)
                throw e
            }

            try {
                setupRefreshButton()
                Log.d(TAG, "Refresh button setup completed")
            } catch (e: Exception) {
                Log.e(TAG, "Error setting up refresh button", e)
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

    private fun setupRecyclerView() {
        try {
            Log.d(TAG, "Setting up RecyclerView")
            deliveryAdapter = DeliveryAdapter { delivery ->
                // 配送詳細画面へ遷移
                try {
                    Log.d(TAG, "Item clicked: ${delivery.packageNumber}")
                    val intent = Intent(this, DeliveryDetailActivity::class.java)
                    intent.putExtra("delivery_id", delivery.id)
                    startActivity(intent)
                    Log.d(TAG, "Navigation to detail started")
                } catch (e: Exception) {
                    Log.e(TAG, "Error navigating to detail", e)
                    Log.e(TAG, "Error message: ${e.message}")
                    Log.e(TAG, "Error stack trace: ${e.stackTraceToString()}")
                    Toast.makeText(this, "画面遷移エラー: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            binding.deliveryRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@DeliveryListActivity)
                adapter = deliveryAdapter
            }
            Log.d(TAG, "RecyclerView setup completed")
        } catch (e: Exception) {
            Log.e(TAG, "Error in setupRecyclerView", e)
            throw e
        }
    }

    private fun loadDeliveries() {
        try {
            Log.d(TAG, "Loading deliveries")
            // サンプルデータ（実際のアプリではAPIから取得）
            allDeliveries = generateSampleDeliveries()
            Log.d(TAG, "Generated ${allDeliveries.size} deliveries")
            if (::deliveryAdapter.isInitialized) {
                deliveryAdapter.submitList(allDeliveries)
                Log.d(TAG, "Deliveries submitted to adapter")
            } else {
                Log.e(TAG, "DeliveryAdapter not initialized")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in loadDeliveries", e)
            throw e
        }
    }

    private fun generateSampleDeliveries(): List<Delivery> {
        return listOf(
            Delivery(
                id = "1",
                packageNumber = "PKG-2025-001",
                recipientName = "山田太郎",
                deliveryAddress = "東京都渋谷区1-2-3",
                status = DeliveryStatus.PENDING,
                deliveryDate = Date(),
                driverName = "佐藤一郎",
                phoneNumber = "090-1234-5678"
            ),
            Delivery(
                id = "2",
                packageNumber = "PKG-2025-002",
                recipientName = "鈴木花子",
                deliveryAddress = "東京都新宿区4-5-6",
                status = DeliveryStatus.IN_TRANSIT,
                deliveryDate = Date(),
                driverName = "田中次郎",
                phoneNumber = "090-2345-6789"
            ),
            Delivery(
                id = "3",
                packageNumber = "PKG-2025-003",
                recipientName = "高橋三郎",
                deliveryAddress = "東京都港区7-8-9",
                status = DeliveryStatus.DELIVERED,
                deliveryDate = Date(),
                driverName = "佐藤一郎",
                phoneNumber = "090-3456-7890"
            ),
            Delivery(
                id = "4",
                packageNumber = "PKG-2025-004",
                recipientName = "伊藤四郎",
                deliveryAddress = "東京都品川区10-11-12",
                status = DeliveryStatus.FAILED,
                deliveryDate = Date(),
                driverName = "田中次郎",
                phoneNumber = "090-4567-8901"
            )
        )
    }

    private fun setupRefreshButton() {
        binding.refreshButton.setOnClickListener {
            loadDeliveries()
            Toast.makeText(this, "配送一覧を更新しました", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        try {
            menuInflater.inflate(R.menu.delivery_list_menu, menu)
            
            val searchItem = menu.findItem(R.id.action_search)
            if (searchItem != null) {
                val searchView = searchItem.actionView as? SearchView
                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        filterDeliveries(newText ?: "")
                        return true
                    }
                })
            }
            
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreateOptionsMenu", e)
            return false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                // フィルター機能（実装は省略）
                Toast.makeText(this, "フィルター機能", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun filterDeliveries(query: String) {
        try {
            Log.d(TAG, "Filtering deliveries with query: $query")
            val filtered = if (query.isEmpty()) {
                allDeliveries
            } else {
                allDeliveries.filter {
                    it.packageNumber.contains(query, ignoreCase = true) ||
                    it.recipientName.contains(query, ignoreCase = true) ||
                    it.deliveryAddress.contains(query, ignoreCase = true)
                }
            }
            if (::deliveryAdapter.isInitialized) {
                deliveryAdapter.submitList(filtered)
                Log.d(TAG, "Filtered list submitted: ${filtered.size} items")
            } else {
                Log.e(TAG, "DeliveryAdapter not initialized for filtering")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error filtering deliveries", e)
        }
    }
}

