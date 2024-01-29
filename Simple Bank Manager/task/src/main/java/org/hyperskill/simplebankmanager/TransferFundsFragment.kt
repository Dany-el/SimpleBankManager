package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class TransferFundsFragment : Fragment(R.layout.fragment_transfer_funds), GetIntentExtraOrDefault,
    PutExtraToIntent {
    private var amount = .0
    private var accountEditText: EditText? = null
    private var amountEditText: EditText? = null
    private var transferButton: Button? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amount = getExtraOrDefault("balance", 100.0)
        Log.i("Amount", "Get $amount from extras")

        transferButton = view.findViewById(R.id.transferFundsButton)
        transferButton?.setOnClickListener {
            accountEditText = view.findViewById(R.id.transferFundsAccountEditText)
            amountEditText = view.findViewById(R.id.transferFundsAmountEditText)

            val account = accountEditText?.text.toString()
            val transferAmount: Double = amountEditText?.text.toString().toDoubleOrNull() ?: -1.0

            if (isAccountEditTextMatchRegex(accountEditText, account) &&
                isEditTextHasRightValue(
                    amountEditText,
                    transferAmount
                )
            ) {
                if (isTransferAmountInRange(transferAmount, amount)) {
                    amount -= transferAmount
                    putExtraToIntent("balance", amount)

                    Log.i(
                        "Transfer",
                        String.format("Transferred \$%.2f to account $account", transferAmount)
                    )

                    Toast.makeText(
                        requireActivity().applicationContext,
                        String.format("Transferred \$%.2f to account $account", transferAmount),
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().popBackStack()
                } else {
                    Log.i(
                        "Transfer",
                        String.format("Not enough funds to transfer \$%.2f", transferAmount)
                    )

                    Toast.makeText(
                        requireActivity().applicationContext,
                        String.format("Not enough funds to transfer \$%.2f", transferAmount),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        accountEditText = null
        amountEditText = null
        transferButton = null
    }
}

fun isAccountEditTextMatchRegex(accountEditText: EditText?, account: String): Boolean {
    val isAccountMatchRegex =
        Regex("^(sa|ca)\\d{4}\$").matches(account)
    return if (!isAccountMatchRegex) {
        accountEditText?.error = "Invalid account number"
        false
    } else {
        accountEditText?.error = null
        true
    }
}

fun isEditTextHasRightValue(
    amountEditText: EditText?,
    transferAmount: Double
): Boolean {
    return if (
        amountEditText?.text.isNullOrEmpty() ||
        transferAmount <= 0
    ) {
        amountEditText?.error = "Invalid amount"
        false
    } else {
        true
    }
}

fun isTransferAmountInRange(
    transferAmount: Double,
    currentAmount: Double
) = transferAmount in 1.0..currentAmount