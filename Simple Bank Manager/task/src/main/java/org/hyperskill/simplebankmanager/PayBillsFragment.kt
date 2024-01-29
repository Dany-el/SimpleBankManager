package org.hyperskill.simplebankmanager

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class PayBillsFragment :
    Fragment(R.layout.fragment_pay_bills),
    GetIntentExtraOrDefault,
    PutExtraToIntent {

    private val defaultBillInfoMap = mapOf(
        "ELEC" to Triple("Electricity", "ELEC", 45.0),
        "GAS" to Triple("Gas", "GAS", 20.0),
        "WTR" to Triple("Water", "WTR", 25.5)
    )

    private lateinit var billInfoMap: Map<String, Triple<String, String, Double>>
    private var amount: Double = .0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amount = getExtraOrDefault("balance", 100.0)
        billInfoMap = getExtraOrDefaultMap("billInfo", defaultBillInfoMap)

        view.findViewById<Button>(R.id.payBillsShowBillInfoButton)
            .setOnClickListener {
                val billingCode = view.findViewById<EditText>(R.id.payBillsCodeInputEditText)
                val billInfo = billInfoMap[billingCode.text.toString()]
                if (billInfo != null) {
                    val billName = billInfo.first
                    val billCode = billInfo.second
                    val billAmount = billInfo.third
                    val text = """
                        Name: $billName
                        BillCode: $billCode
                        Amount: ${String.format("$%.2f", billAmount)}
                    """.trimIndent()

                    AlertDialog.Builder(requireActivity())
                        .setTitle("Bill info")
                        .setMessage(text)
                        .setPositiveButton("Confirm") { dialog, _ ->
                            if (amount - billAmount >= 0) {
                                amount -= billAmount
                                putExtraToIntent("balance", amount)
                                showToastMessage("Payment for bill $billName, was successful")
                            } else {
                                showAlertDialogWithOkButton("Error", "Not enough funds")
                                dialog.dismiss()
                            }
                        }
                        .setNegativeButton(android.R.string.cancel, null)
                        .show()

                } else {
                    showAlertDialogWithOkButton("Error", "Wrong code")
                }
            }
    }
}

fun Fragment.showAlertDialogWithOkButton(title: String, msg: String) {
    AlertDialog.Builder(requireActivity())
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(android.R.string.ok, null)
        .show()
}