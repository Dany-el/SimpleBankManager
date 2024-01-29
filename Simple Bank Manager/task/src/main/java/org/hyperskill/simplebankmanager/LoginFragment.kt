package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.fragment_login), GetIntentExtraOrDefault {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginUsername: EditText = view.findViewById(R.id.loginUsername)
        val loginPassword: EditText = view.findViewById(R.id.loginPassword)

        val loginButton: Button = view.findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            if (
                getExtraOrDefault("username", "Lara") == loginUsername.text.toString() &&
                getExtraOrDefault("password", "1234") == loginPassword.text.toString()
            ) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "logged in",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(
                    R.id.action_loginFragment_to_userMenuFragment,
                    bundleOf("username" to loginUsername.text.toString())
                )
            } else {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "invalid credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}