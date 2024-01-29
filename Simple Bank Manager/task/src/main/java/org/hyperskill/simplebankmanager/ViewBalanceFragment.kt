package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class ViewBalanceFragment : Fragment(R.layout.fragment_view_balance), GetIntentExtraOrDefault {
    private var amount = .0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amount = getExtraOrDefault("balance", 100.0)
        val text = getString(R.string.amount, amount)
        Log.i("Amount", "Current balance: $amount")
        view.findViewById<TextView>(R.id.viewBalanceAmountTextView).text = text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().intent.putExtra("balance", amount)
    }
}

