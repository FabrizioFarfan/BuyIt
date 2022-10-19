package com.fabrizio.buyit.ui.home

import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import com.fabrizio.buyit.arch.BuyItViewModel
import com.fabrizio.buyit.database.AppDatabase
import com.fabrizio.buyit.ui.MainActivity
import com.google.android.material.color.MaterialColors


abstract class BaseFragment: Fragment() {

    protected val mainActivity :MainActivity
        get() = activity as MainActivity

    protected val appDatabase: AppDatabase
        get() = AppDatabase.getDatabase(requireActivity())

    protected val sharedViewModel: BuyItViewModel by activityViewModels()

    // region Navigation helper methods
    protected fun navigateUp(){
        mainActivity.navController.navigateUp()
    }

    protected fun navigateViaNavGraph(actionId: Int){
        mainActivity.navController.navigate(actionId)
    }

    protected fun navigateViaNavGraph(navDirections: NavDirections){
        mainActivity.navController.navigate(navDirections)
    }
    // endregion Navigation helper methods

    @ColorInt
    protected fun getAttrColor(attrResId: Int): Int {
        return MaterialColors.getColor(requireView(), attrResId)
    }



}
