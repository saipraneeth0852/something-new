package com.statushub.india.util

import com.statushub.india.BuildConfig

/**
 * Central configuration object that reads values from BuildConfig
 * (populated via local.properties at build time).
 *
 * IMPORTANT: Never hardcode secrets here. All values must come from
 * local.properties (development) or CI/CD secrets (production).
 */
object AppConfig {
    const val PEXELS_API_KEY = BuildConfig.PEXELS_API_KEY
    const val ADMOB_APP_ID = BuildConfig.ADMOB_APP_ID
    const val BANNER_AD_UNIT_ID = BuildConfig.BANNER_AD_UNIT_ID
    const val INTERSTITIAL_AD_UNIT_ID = BuildConfig.INTERSTITIAL_AD_UNIT_ID
}
