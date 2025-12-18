package com.transportcompany.app.utils

object Constants {
    const val PREF_NAME = "transport_app_prefs"
    const val KEY_USERNAME = "username"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    
    // API endpoints (実際のAPIに置き換えてください)
    const val BASE_URL = "https://api.transportcompany.com/"
    const val API_LOGIN = "auth/login"
    const val API_DELIVERIES = "deliveries"
    const val API_UPDATE_STATUS = "deliveries/{id}/status"
}

