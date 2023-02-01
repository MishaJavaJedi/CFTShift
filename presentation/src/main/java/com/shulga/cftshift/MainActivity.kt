package com.shulga.cftshift

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.text.Html.fromHtml
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.shulga.cftshift.adapter.OnInteractionListener
import com.shulga.cftshift.adapter.SearchHistoryAdapter
import com.shulga.cftshift.databinding.ActivityMainBinding
import com.shulga.domain.models.BinModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = SearchHistoryAdapter(object : OnInteractionListener {
            override fun onDelete(cardId: Int) {
                viewModel.deleteCardInfoFromHistory(cardId)
                viewModel.getAllSearchHistory()
            }
        })

        val rv = binding.rv
        rv.adapter = adapter

        lifecycle.coroutineScope.launch {
            viewModel.getAllSearchHistory().collect() {
                adapter.submitList(it)
            }
        }

        binding.checkButton.setOnClickListener {

            if (isOnline(this)) {
                val cardNumber = binding.inputField.text.toString()
                if (cardNumber.isNullOrBlank()) {
                    showNullToast()
                    return@setOnClickListener
                } else
                    viewModel.getCardInfo(binding.inputField.text.toString())
                viewModel.getCardInfo(cardNumber)
                saveCardNumberToSearchHistory(cardNumber)
            } else {
                Toast.makeText(this, R.string.network_exception, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.exceptionMessageResource.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel._dataFlow
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                if (state != null) {
                    renderUi(binding, state)
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun saveCardNumberToSearchHistory(cardNumber: String) {
        viewModel.saveCardNumberToSearchHistory(cardNumber)
    }

    private fun showNullToast() {
        Toast.makeText(this, R.string.null_toast_message, Toast.LENGTH_SHORT).show()
    }

    private fun renderUi(binding: ActivityMainBinding, state: BinModel) {
        with(binding) {
            schemeData.text = state?.scheme
            typeData.text = state?.type
            bankNameData.text = state?.bank?.name
            if (state?.bank?.url == null) {
                bankUrlData.text = ""
            }
            if (state?.bank?.url != null) {
                bankUrlData.text = fromHtml(state?.bank?.url)
            }
            bankNumberData.text = state?.bank?.phone
            brandData.text = state?.brand
            if (state?.prepaid == true) {
                prepaidData.text = "Yes"
            }
            if (state?.prepaid == false) {
                prepaidData.text = "No"
            }
            if (state?.number?.lenght == null) {
                cardDataLength.text = ""
            }
            if (state?.number?.lenght != null) {
                cardDataLength.text = state!!.number!!.lenght!!.toString()
            }

            if (state?.number?.luhn == true) {
                luhn.text = "Yes"
            }
            if (state?.number?.luhn == false) {
                luhn.text = "No"
            }
            countryData.text = state?.country?.name
            emojiData.text = state?.country?.emoji

            bankNumberData.setOnClickListener {

                state.bank?.phone?.let { performPhoneCall(it) }
            }
        }
    }

    private fun performPhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, R.string.not_successful, Toast.LENGTH_SHORT).show()
        }
    }
}

private fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return true
        }
    }
    return false
}




