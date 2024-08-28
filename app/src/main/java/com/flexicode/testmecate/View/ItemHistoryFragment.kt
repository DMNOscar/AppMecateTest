package com.flexicode.testmecate.View

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexicode.testmecate.Entity.UserEntity
import com.flexicode.testmecate.View.Adapter.HistoryAdapter
import com.flexicode.testmecate.View.Adapter.ResultAdapter
import com.flexicode.testmecate.View.ViewModel.MainActivityViewModel
import com.flexicode.testmecate.databinding.FragmentItemHistoryListDialogBinding

class ItemHistoryFragment(val mainViewModel: MainActivityViewModel) : BottomSheetDialogFragment() {

    private var _binding: FragmentItemHistoryListDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentItemHistoryListDialogBinding.inflate(inflater, container, false)


        observerViewModel()
        return binding.root

    }



    private fun observerViewModel(){
        lifecycleScope.launchWhenStarted {
            mainViewModel.state.collect() { state ->

                when (state.action) {
                    "history"->{
                        loadHistory(state.history)
                    }
                }
            }
        }
    }

    private fun loadHistory(history: List<UserEntity>) {

        historyAdapter = HistoryAdapter(history){ selectedItem ->
            mainViewModel.getHistoryDB(selectedItem)
            dismiss()
        }
        binding.rcvHistory.layoutManager = LinearLayoutManager(context)
        binding.rcvHistory.adapter = historyAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ItemHistoryFragment"
    }
}