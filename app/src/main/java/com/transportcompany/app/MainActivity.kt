package com.transportcompany.app

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.transportcompany.app.ui.screens.BarcodeScanScreen
import com.transportcompany.app.ui.screens.BranchSelectionScreen
import com.transportcompany.app.ui.screens.DeliveryDetailScreen
import com.transportcompany.app.ui.screens.DeliveryListScreen
import com.transportcompany.app.ui.screens.LoginScreen
import com.transportcompany.app.ui.screens.PaymentScreen
import com.transportcompany.app.ui.theme.TransportCompanyAppTheme
import com.transportcompany.app.utils.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)
        
        setContent {
            TransportCompanyAppTheme {
                AppNavigation(sharedPreferences = sharedPreferences)
            }
        }
    }
}

@Composable
fun AppNavigation(sharedPreferences: SharedPreferences) {
    val navController = rememberNavController()
    
    // 配送データを共有状態として管理
    var deliveries by remember { mutableStateOf(generateSampleDeliveries()) }
    
    // 初回起動時のスタート画面を決定
    val isLoggedIn = remember {
        sharedPreferences.getBoolean(Constants.KEY_IS_LOGGED_IN, false)
    }
    val branchId = remember {
        sharedPreferences.getLong(Constants.KEY_BRANCH_ID, -1)
    }
    
    val startDestination = remember(isLoggedIn, branchId) {
        when {
            !isLoggedIn -> "login"
            branchId == -1L -> "branch_selection"
            else -> "delivery_list"
        }
    }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable("login") {
                LoginScreen(
                    navController = navController,
                    sharedPreferences = sharedPreferences
                )
            }
            composable("branch_selection") {
                BranchSelectionScreen(
                    navController = navController,
                    sharedPreferences = sharedPreferences
                )
            }
            composable("delivery_list") {
                DeliveryListScreen(
                    navController = navController,
                    sharedPreferences = sharedPreferences,
                    deliveries = deliveries,
                    onDeliveriesUpdate = { deliveries = it }
                )
            }
            composable("delivery_detail/{deliveryId}") { backStackEntry ->
                val deliveryId = backStackEntry.arguments?.getString("deliveryId") ?: ""
                DeliveryDetailScreen(
                    navController = navController,
                    deliveryId = deliveryId,
                    sharedPreferences = sharedPreferences,
                    deliveries = deliveries,
                    onDeliveryUpdate = { updatedDelivery ->
                        deliveries = deliveries.map { 
                            if (it.id == updatedDelivery.id) updatedDelivery else it 
                        }
                    }
                )
            }
            composable("payment/{amount}/{paymentId}") { backStackEntry ->
                val amountRaw = backStackEntry.arguments?.getString("amount") ?: "0"
                val amount = try {
                    val amountInt = amountRaw.toInt()
                    "${amountInt.toString().reversed().chunked(3).joinToString(",").reversed()}円"
                } catch (e: Exception) {
                    "0円"
                }
                val paymentId = backStackEntry.arguments?.getString("paymentId") ?: "PAY-001"
                PaymentScreen(
                    navController = navController,
                    amount = amount,
                    paymentId = paymentId
                )
            }
            composable("barcode_scan") {
                BarcodeScanScreen(
                    navController = navController
                )
            }
        }
    }
}

// サンプルデータ生成関数をMainActivityに移動
private fun generateSampleDeliveries(): List<com.transportcompany.app.models.Delivery> {
    return listOf(
        com.transportcompany.app.models.Delivery(
            id = "1",
            packageNumber = "PKG-2025-001",
            recipientName = "山田太郎",
            deliveryAddress = "東京都渋谷区1-2-3",
            status = com.transportcompany.app.models.DeliveryStatus.PENDING,
            deliveryDate = java.util.Date(),
            driverName = "佐藤一郎",
            phoneNumber = "090-1234-5678"
        ),
        com.transportcompany.app.models.Delivery(
            id = "2",
            packageNumber = "PKG-2025-002",
            recipientName = "鈴木花子",
            deliveryAddress = "東京都新宿区4-5-6",
            status = com.transportcompany.app.models.DeliveryStatus.IN_TRANSIT,
            deliveryDate = java.util.Date(),
            driverName = "田中次郎",
            phoneNumber = "090-2345-6789"
        ),
        com.transportcompany.app.models.Delivery(
            id = "3",
            packageNumber = "PKG-2025-003",
            recipientName = "高橋三郎",
            deliveryAddress = "東京都港区7-8-9",
            status = com.transportcompany.app.models.DeliveryStatus.DELIVERED,
            deliveryDate = java.util.Date(),
            driverName = "佐藤一郎",
            phoneNumber = "090-3456-7890"
        ),
        com.transportcompany.app.models.Delivery(
            id = "4",
            packageNumber = "PKG-2025-004",
            recipientName = "伊藤四郎",
            deliveryAddress = "東京都品川区10-11-12",
            status = com.transportcompany.app.models.DeliveryStatus.FAILED,
            deliveryDate = java.util.Date(),
            driverName = "田中次郎",
            phoneNumber = "090-4567-8901"
        )
    )
}
