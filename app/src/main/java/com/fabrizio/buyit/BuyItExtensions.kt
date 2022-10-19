package com.fabrizio.buyit

import android.view.View
import androidx.annotation.ColorInt
import com.airbnb.epoxy.EpoxyController
import com.fabrizio.buyit.ui.epoxy.models.HeaderEpoxyModel
import com.google.android.material.color.MaterialColors

fun EpoxyController.addHeaderModel(headerText: String) {
    HeaderEpoxyModel(headerText).id(headerText).addTo(this)
}

@ColorInt
fun View.getAtrColor(attrResId: Int): Int {
    return MaterialColors.getColor(this, attrResId)
}