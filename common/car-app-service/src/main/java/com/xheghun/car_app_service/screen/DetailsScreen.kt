package com.xheghun.car_app_service.screen

import android.graphics.Color
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.ActionStrip
import androidx.car.app.model.CarColor
import androidx.car.app.model.CarIcon
import androidx.car.app.model.MessageTemplate
import androidx.car.app.model.Pane
import androidx.car.app.model.PaneTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import androidx.core.graphics.drawable.IconCompat
import com.example.places.data.PlacesRepository
import com.example.places.data.model.toIntent
import com.xheghun.car_app_service.R

class DetailsScreen(carContext: CarContext, val placeId: Int) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        // in a real app, this should be stored in the data layer
        var isFavourite = false

        val place = PlacesRepository().getPlace(placeId)
            ?: return MessageTemplate.Builder("Place not found")
                .setHeaderAction(Action.BACK)
                .build()

        val navigateAction = Action.Builder()
            .setTitle("Navigate")
            .setIcon(
                CarIcon.Builder(
                    IconCompat.createWithResource(
                        carContext, R.drawable.navigation_24dp
                    )
                ).build()
            )
            .setOnClickListener { carContext.startCarApp(place.toIntent(CarContext.ACTION_NAVIGATE)) }
            .build()

        val actionStrip = ActionStrip.Builder()
            .addAction(
                Action.Builder()
                    .setIcon(
                        CarIcon.Builder(
                            IconCompat.createWithResource(carContext, R.drawable.navigation_24dp)
                        ).setTint(
                            if (isFavourite) CarColor.RED else CarColor.createCustom(
                                Color.LTGRAY,
                                Color.DKGRAY
                            )
                        ).build()
                    ).setOnClickListener {
                        isFavourite = !isFavourite

                        //update the screen state
                        invalidate()
                    }
                    .build()
            ).build()

        return PaneTemplate.Builder(
            Pane.Builder()
                .addAction(navigateAction)
                .addRow(
                    Row.Builder()
                        .setTitle("Coordinates")
                        .addText("${place.latitude}, ${place.longitude}")
                        .build()
                ).addRow(
                    Row.Builder()
                        .setTitle("Description")
                        .addText(place.description)
                        .build()
                ).build()
        )
            .setActionStrip(actionStrip)
            .setTitle(place.name)
            .setHeaderAction(Action.BACK)
            .build()
    }
}