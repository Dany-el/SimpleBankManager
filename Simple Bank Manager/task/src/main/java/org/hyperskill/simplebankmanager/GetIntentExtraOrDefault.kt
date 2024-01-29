package org.hyperskill.simplebankmanager

import androidx.fragment.app.Fragment

interface GetIntentExtraOrDefault {
    fun Fragment.getExtraOrDefault(key: String, default: String): String {
        return requireActivity().intent.extras?.getString(key) ?: default
    }

    fun Fragment.getExtraOrDefault(key: String, default: Double): Double {
        return requireActivity().intent.extras?.getDouble(key) ?: default
    }

    fun Fragment.getExtraOrDefault(
        key: String,
        default: Map<String, Map<String, Double>>
    ): Map<String, Map<String, Double>> {
        val serializableMap =
            requireActivity().intent.extras?.getSerializable(key)
        return if (serializableMap == null) {
            default
        } else {
            serializableMap as Map<String, Map<String, Double>>
        }
    }

    fun Fragment.getExtraOrDefaultMap(
        key: String,
        default: Map<String, Triple<String, String, Double>>
    ): Map<String, Triple<String, String, Double>> {
        val serializableMap =
            requireActivity().intent.extras?.getSerializable(key)
        return if (serializableMap == null) {
            default
        } else {
            serializableMap as Map<String, Triple<String, String, Double>>
        }
    }
}