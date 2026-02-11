package com.transportcompany.app.utils

object Constants {
    const val PREF_NAME = "transport_app_prefs"
    const val KEY_USERNAME = "username"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    const val KEY_BRANCH_ID = "branch_id"
    const val KEY_BRANCH_NAME = "branch_name"
    const val KEY_BRANCH_CODE = "branch_code"
    
    // API endpoints (実際のAPIに置き換えてください)
    const val BASE_URL = "https://api.transportcompany.com/"
    const val API_LOGIN = "auth/login"
    const val API_BRANCHES = "branches"
    const val API_DELIVERIES = "deliveries"
    const val API_UPDATE_STATUS = "deliveries/{id}/status"
}

