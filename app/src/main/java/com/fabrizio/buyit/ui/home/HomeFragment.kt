package com.fabrizio.buyit.ui.home

import android.os.Bundle
import android.view.*
import com.airbnb.epoxy.EpoxyTouchHelper
import com.fabrizio.buyit.R
import com.fabrizio.buyit.database.entity.ItemEntity
import com.fabrizio.buyit.databinding.FragmentHomeBinding
import com.fabrizio.buyit.ui.home.bottomsheet.SortOrderBottomSheetDialogFragment


class HomeFragment : BaseFragment(), ItemEntityInterface {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigateViaNavGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }

        val controller = HomeEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.homeViewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            controller.viewState = viewState
            binding.epoxyRecyclerView.postDelayed(
                { binding.epoxyRecyclerView.smoothScrollToPosition(0)},
                 500L)
        }

        // Setup swipe-to-delete
        EpoxyTouchHelper.initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(HomeEpoxyController.ItemEntityEpoxyModel::class.java)
            .andCallbacks(object :
                EpoxyTouchHelper.SwipeCallbacks<HomeEpoxyController.ItemEntityEpoxyModel>() {
                override fun onSwipeCompleted(
                    model: HomeEpoxyController.ItemEntityEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int,
                ) {
                    val itemThatWasRemoved = model?.itemEntity ?: return
                    sharedViewModel.deleteItem(itemThatWasRemoved.itemEntity)
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuItemSort){
            SortOrderBottomSheetDialogFragment().show(childFragmentManager, null)
            true
        }else {
            super.onOptionsItemSelected(item)
        }
    }

        override fun onResume() {
        super.onResume()
        mainActivity.hideKeyboard(requireView())
    }

    override fun onBumpPriority(itemEntity: ItemEntity) {
        val currentPriority = itemEntity.priority
        var newPriority = currentPriority + 1
        if(newPriority > 3){
            newPriority = 1
        }

        val updatedItemEntity = itemEntity.copy(priority = newPriority)
        sharedViewModel.updateItem(updatedItemEntity)
    }

    override fun onItemSelected(itemEntity: ItemEntity) {
        val navDirections = HomeFragmentDirections.actionHomeFragmentToAddItemEntityFragment(itemEntity.id)
        navigateViaNavGraph(navDirections)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}