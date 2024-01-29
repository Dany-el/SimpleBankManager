package org.hyperskill.simplebankmanager

import androidx.fragment.app.Fragment

interface PutExtraToIntent {
    fun Fragment.putExtraToIntent(name: String, value: Double) {
        requireActivity().intent.putExtra(name, value)
    }
}