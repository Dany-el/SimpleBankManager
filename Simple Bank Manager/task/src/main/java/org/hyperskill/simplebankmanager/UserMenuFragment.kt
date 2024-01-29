package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class UserMenuFragment : Fragment(R.layout.fragment_user_menu) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username") ?: ""
        val welcomeText = getString(R.string.welcome_username, username)
        view.findViewById<TextView>(R.id.userMenuWelcomeTextView).text = welcomeText

        view.findViewById<Button>(R.id.userMenuViewBalanceButton)
            .setOnClickListener {
                findNavController().navigate(R.id.action_userMenuFragment_to_viewBalanceFragment)
            }

        view.findViewById<Button>(R.id.userMenuTransferFundsButton)
            .setOnClickListener {
                findNavController().navigate(R.id.action_userMenuFragment_to_transferFundsFragment)
            }

        view.findViewById<Button>(R.id.userMenuExchangeCalculatorButton)
            .setOnClickListener {
                findNavController().navigate(R.id.action_userMenuFragment_to_calculateExchangeFragment)
            }

        view.findViewById<Button>(R.id.userMenuPayBillsButton)
            .setOnClickListener {
                findNavController().navigate(R.id.action_userMenuFragment_to_payBillsFragment)
            }
    }
}