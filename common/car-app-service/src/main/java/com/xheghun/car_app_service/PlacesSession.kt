package com.xheghun.car_app_service

import android.content.Intent
import androidx.car.app.Screen
import androidx.car.app.Session
import com.xheghun.car_app_service.screen.MainScreen

class PlacesSession : Session() {
    override fun onCreateScreen(intent: Intent): Screen {
        return MainScreen(carContext)
    }
}