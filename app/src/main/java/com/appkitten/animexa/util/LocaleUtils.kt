package com.appkitten.animexa.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import java.util.*


class LocaleUtils {
    companion object {
        fun setLocale(context: Context, languageCode: String, bool:Boolean) {
            val selectedLocale = Locale(languageCode)
            if (bool){
                Locale.setDefault(selectedLocale)
            }
            // Update the app's configuration to use the selected locale.
            // Update the app's configuration to use the selected locale.
            val resources: Resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(selectedLocale)
            resources.updateConfiguration(configuration, resources.displayMetrics)

        }
        fun getCurrentLocale(context: Context): Locale {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.resources.configuration.locales[0]
            } else {
                context.resources.configuration.locale
            }
        }
    }
}
