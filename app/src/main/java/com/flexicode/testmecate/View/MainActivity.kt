package com.flexicode.testmecate.View

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexicode.testmecate.Models.UserData
import com.flexicode.testmecate.R
import com.flexicode.testmecate.View.Adapter.ResultAdapter
import com.flexicode.testmecate.View.ViewModel.MainActivityViewModel
import com.flexicode.testmecate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainActivityViewModel by viewModels()
    private lateinit var resultAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnSearch.setOnClickListener(this)
        binding.btnShowHistory.setOnClickListener(this)


        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mainViewModel.searchtLastName(binding.edtSearch.text.toString())
                hideKeyboard()
                true
            } else {
                false
            }
        }
        observerViewModel()
        setContentView(binding.root)
    }

    private fun observerViewModel(){
        lifecycleScope.launchWhenStarted {
            mainViewModel.state.collect() { state ->

                when (state.action) {

                    "search"-> {
                        if (state.showAlert) {
                            showErrorDialog(
                                title = state.titleAlert,
                                description = state.descriptionAlert
                            )
                        } else {
                            updateUI(state.response.name ?: "Resultados")
                            loadResultList(data = state.response)
                        }
                    }
                    "history"->{
                        if (supportFragmentManager.findFragmentByTag(ItemHistoryFragment.TAG) == null) {
                            val bottomSheet = ItemHistoryFragment(mainViewModel)
                            bottomSheet.show(supportFragmentManager, ItemHistoryFragment.TAG)
                        }
                    }
                }
            }
        }
    }
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus
        view?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
    private fun updateUI(title: String) {
        binding.txtvNameSearch.text = title
        binding.edtSearch.clearFocus()
        binding.edtSearch.setText("")
    }

    private fun loadResultList(data: UserData){
        resultAdapter = ResultAdapter(data.country)
        binding.rcvResultSearch.layoutManager = LinearLayoutManager(this)
        binding.rcvResultSearch.adapter = resultAdapter
    }

   private fun showErrorDialog(title: String, description: String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(description)
            setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(false)
            create()
            show()
        }
    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.btnSearch -> {
                hideKeyboard()
                mainViewModel.searchtLastName(binding.edtSearch.text.toString())
            }

            R.id.btnShowHistory -> {
                hideKeyboard()
                mainViewModel.showHistory()
            }
        }

    }
}