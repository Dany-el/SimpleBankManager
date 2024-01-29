package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class CalculateExchangeFragment :
    Fragment(R.layout.fragment_calculate_exchange),
    GetIntentExtraOrDefault {
    private val defaultMap = mapOf(
        "EUR" to mapOf(
            "GBP" to 0.5,
            "USD" to 2.0
        ),
        "GBP" to mapOf(
            "EUR" to 2.0,
            "USD" to 4.0
        ),
        "USD" to mapOf(
            "EUR" to 0.5,
            "GBP" to 0.25
        )
    )

    private lateinit var exchangeMap: Map<String, Map<String, Double>>
    private lateinit var selectedConvertTo: String
    private lateinit var selectedConvertFrom: String
    private lateinit var convertToSpinner: Spinner
    private lateinit var convertFromSpinner: Spinner
    private lateinit var calculateExchangeDisplayTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        convertFromSpinner = view.findViewById(R.id.calculateExchangeFromSpinner)
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.exchange_to_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            convertFromSpinner.adapter = adapter
        }

        convertFromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == convertToSpinner.selectedItemPosition) {
                    convertToSpinner.setSelection(moveToNextIndex(p2))
                    showToastMessage("Cannot convert to same currency")
                }
                selectedConvertFrom =
                    p0?.getItemAtPosition(p2) as String
                Log.i("SpinnerFrom", "Selected $selectedConvertFrom")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        convertToSpinner = view.findViewById(R.id.calculateExchangeToSpinner)
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.exchange_to_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            convertToSpinner.adapter = adapter
            convertToSpinner.setSelection(1)
        }

        convertToSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == convertFromSpinner.selectedItemPosition) {
                    convertFromSpinner.setSelection(moveToNextIndex(p2))
                    showToastMessage("Cannot convert to same currency")
                }
                selectedConvertTo =
                    p0?.getItemAtPosition(p2) as String

                Log.i("SpinnerTo", "Selected $selectedConvertTo")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        exchangeMap = getExtraOrDefault("exchangeMap", defaultMap)
        Log.i("Extra", "Get exchangeMap ${exchangeMap.entries.joinToString()}")

        view.findViewById<Button>(R.id.calculateExchangeButton)
            .setOnClickListener {
                val amountText =
                    view.findViewById<EditText>(R.id.calculateExchangeAmountEditText).text
                if (amountText.isNullOrEmpty()) {
                    showToastMessage("Enter amount")
                } else {
                    calculateExchangeDisplayTextView =
                        view.findViewById(R.id.calculateExchangeDisplayTextView)

                    val currencyDifference =
                        exchangeMap[selectedConvertFrom]?.get(selectedConvertTo) ?: .0
                    val amount = amountText.toString().toDouble()

                    val text = String.format(
                        "%s%.2f = %s%.2f",
                        getCurrencySymbol(selectedConvertFrom), amount,
                        getCurrencySymbol(selectedConvertTo), amount * currencyDifference
                    )

                    calculateExchangeDisplayTextView.text = text
                    calculateExchangeDisplayTextView.visibility = View.VISIBLE
                }
            }
    }
}

fun Fragment.showToastMessage(msg: String) {
    Toast.makeText(
        requireActivity(),
        msg,
        Toast.LENGTH_SHORT
    ).show()
}

fun moveToNextIndex(pos: Int): Int {
    // 2 is the last index in array
    if (pos + 1 > 2) return 0
    return pos + 1
}

fun getCurrencySymbol(currency: String) = when (currency) {
    "EUR" -> "€"
    "GBP" -> "£"
    "USD" -> "\$"
    else -> ""
}
